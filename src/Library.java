import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class Library {

    // The catalogue should contain reading material type and each subtype should
    // be shown by genre for books,  category for magazines and subject for textbooks.
    // Then all of the subtypes should be ordered by different criteria.
    private HashMap<ReadingMaterialType, HashMap<SubTypes, TreeSet<ReadingMaterial>>> catalogue;
    private HashMap<ReadingMaterial, ReadingLog> rentLog;
    private Logger logger;

    public enum ReadingMaterialType {BOOK, TEXTBOOK, MAGAZINE}

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
    }

    public void addReadingMaterial(ReadingMaterial material) {
        if (!catalogue.get(material.getReadingMaterialType()).containsKey(material.getSubType())) {
            catalogue.get(material.getReadingMaterialType()).put(material.getSubType(), new TreeSet<>());
        }
        catalogue.get(material.getReadingMaterialType()).get(material.getSubType()).add(material);
    }

    public void printAllAvailabilityCount() {
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
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
