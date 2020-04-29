import java.time.LocalDate;

public class TextBook extends ReadingMaterial implements Comparable<TextBook>{

    enum Subject implements SubTypes {HISTORY, GEOGRAPHY, MATHEMATICS, CHEMISTRY, ENGLISH}

    private Subject subject;

    public TextBook(String name, LocalDate dateIssued, String publisher,Subject subject) {
        super(name, dateIssued, publisher);
        this.subject= subject;
        this.tax=3;
    }

    @Override
    public Library.ReadingMaterialType getReadingMaterialType() {
        return Library.ReadingMaterialType.TEXTBOOK;
    }

    @Override
    public int getMaxEndDuration() {
        return 150;
    }

    @Override
    public SubTypes getSubType() {
        return subject;
    }


    @Override
    public int compareTo(TextBook o) {
        if(this.subject.compareTo(o.subject)==0){
            return this.name.compareTo(o.name);
        }
        return this.subject.compareTo(o.subject);
    }
}
