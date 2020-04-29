import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class Library {

    public enum ReadingMaterialType {BOOK, TEXTBOOK, MAGAZINE}

    // The catalogue should contain reading material type and each subtype should
    // be shown by genre for books,  category for magazines and subject for textbooks.
    // Then all of the subtypes should be ordered by different criteria.
    private HashMap<ReadingMaterialType, HashMap<SubTypes, TreeSet<ReadingMaterial>>> catalogue;
    private HashMap<ReadingMaterial, ReadingLog> rentLog;
    private Logger logger;

    public Library() {
        this.catalogue = new HashMap<>();
        this.rentLog = new HashMap<>();
        this.logger = new Logger();
        logger.setDaemon(true);
        logger.start();
        catalogue.put(ReadingMaterialType.BOOK, new HashMap<>());
        catalogue.put(ReadingMaterialType.MAGAZINE, new HashMap<>());
        catalogue.put(ReadingMaterialType.TEXTBOOK, new HashMap<>());
    }

    public void rent(ReadingMaterial material, int duration) {
        if (duration > material.getMaxEndDuration()) {
            System.out.println("Sorry, maximum rent duration time is exceeded");
        }
        if (material.isTaken()) {
            System.out.println("Sorry " + material.getName() + " is taken");
        } else {
            material.rentStarted(duration);
            this.rentLog.put(material, new ReadingLog(LocalDateTime.now(),
                    LocalDateTime.now().plus(Duration.ofSeconds(duration))));
            System.out.println("You have rented " + material.getName() + " for " + duration + " days");
        }
    }

    public void returnReadingMaterial(ReadingMaterial material) {
        System.out.println("Tax: " + material.getTax());
        material.rentFinished();
        rentLog.remove(material);// remove from rentLog, so only those, which are not returned yet are there
    }

    public void addReadingMaterial(ReadingMaterial material) {
        if (!catalogue.get(material.getReadingMaterialType()).containsKey(material.getSubType())) {
            catalogue.get(material.getReadingMaterialType()).put(material.getSubType(), new TreeSet<>());
        }
        catalogue.get(material.getReadingMaterialType()).get(material.getSubType()).add(material);
    }

    void printAllAvailabilityCount() {
        int count = 0;
        for (Map.Entry<ReadingMaterialType, HashMap<SubTypes, TreeSet<ReadingMaterial>>> e : catalogue.entrySet()) {
            for (TreeSet<ReadingMaterial> e2 : e.getValue().values()) {
                for (ReadingMaterial r : e2) {
                    if (!r.isTaken()) {
                        count++;
                    }
                }
            }
        }
        System.out.println("All available reading materials in the library are : " + count);
    }

    private void saveToFileNotReturned() {
        File f = new File("taken.txt");
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(f, true));) {
            // all not returned reading materials should be sorted by date and saved to file
            ArrayList<Map.Entry<ReadingMaterial, ReadingLog>> list = new ArrayList<>();
            list.addAll(rentLog.entrySet());
            Collections.sort(list, new Comparator<Map.Entry<ReadingMaterial, ReadingLog>>() {
                @Override
                public int compare(Map.Entry<ReadingMaterial, ReadingLog> o1,
                                   Map.Entry<ReadingMaterial, ReadingLog> o2) {
                    return o1.getValue().start.compareTo(o2.getValue().start);
                }
            });
            for (Map.Entry<ReadingMaterial, ReadingLog> e : list) {
                pw.println(e.getKey().getName() + " " + e.getValue().start);
            }
            pw.println("Total : " + list.size());
            pw.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    class ReadingLog {
        LocalDateTime start;
        LocalDateTime end;

        public ReadingLog(LocalDateTime start, LocalDateTime end) {
            this.start = start;
            this.end = end;
        }
    }

    class Logger extends Thread {
        @Override
        public void run() {
            try {
                while (true) {
                    Thread.sleep(5000);
                    printAllAvailabilityCount();
                    saveToFileNotReturned();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
