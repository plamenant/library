import java.time.LocalDate;

public class Magazine extends ReadingMaterial implements Comparable<Magazine>{


    enum Category implements SubTypes {NATURE, SPORT, FASHION, SI_FI}

    private int issueNumber;
    private Category category;

    public Magazine(String name, LocalDate dateIssued, String publisher, int issueNumber, Category category) {
        super(name, dateIssued, publisher);
        this.issueNumber = issueNumber;
        this.category = category;
    }

    @Override
    public Library.ReadingMaterialType getReadingMaterialType() {
        return Library.ReadingMaterialType.MAGAZINE;
    }

    @Override
    public int getMaxEndDuration() {
        throw new BadRentException();
    }

    @Override
    public SubTypes getSubType() {
        return category;
    }


    @Override
    public int compareTo(Magazine o) {
        if( this.name.compareTo(o.name)==0){
            return this.issueNumber-o.issueNumber;
        }
        return  this.name.compareTo(o.name);
    }
}
