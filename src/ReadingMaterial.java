import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.TreeMap;

public abstract class ReadingMaterial {

    protected String name;
    protected LocalDate dateIssued;
    private String publisher;
    protected double tax = 0;
    private Library.ReadingMaterialType type;
    private TreeMap<LocalDateTime, LocalDateTime> rentHistory;
    private TaxAccumulator taxAccumulator;

    public ReadingMaterial(String name, LocalDate dateIssued, String publisher) {
        this.name = name;
        this.dateIssued = dateIssued;
        this.publisher = publisher;
        this.rentHistory = new TreeMap<>();
    }

    public void rentStarted(int duration) {
        taxAccumulator = new TaxAccumulator(duration);
        taxAccumulator.start();
        this.rentHistory.put(LocalDateTime.now(), null);
    }

    public void rentFinished() {
        taxAccumulator.interrupt();
        rentHistory.put(this.rentHistory.lastEntry().getKey(), LocalDateTime.now());
    }

    public abstract Library.ReadingMaterialType getReadingMaterialType();

    public abstract int getMaxEndDuration();

    public boolean isTaken() {
        if (rentHistory.isEmpty()) {
            return false;
        }
        return rentHistory.lastEntry().getValue() == null;
    }

    public double getTax() {
        return tax;
    }

    public abstract SubTypes getSubType();

    public String getName() {
        return name;
    }

    class TaxAccumulator extends Thread {
        private int duration;

        TaxAccumulator(int duration) {
            this.duration = duration;
        }

        // if the reading material is not returned in time,
        // accumulate 1% tax for every second it's not returned
        @Override
        public void run() {
            try {
                Thread.sleep(duration * 1000);//seconds
                if (isTaken()) {
                    while (true) {
                        if (isInterrupted()) {
                            System.out.println("Tax accumulation ended");
                            return;
                        }
                        Thread.sleep(1000);
                        tax *= 1.01;
                        System.out.println("1% tax accumulated. Now tax is: " + tax);
                    }
                }
            } catch (InterruptedException e) {
                System.out.println("Tax accumulation ended");
            }
        }
    }
}
