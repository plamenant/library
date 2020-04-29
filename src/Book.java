import java.time.LocalDate;

public class Book extends ReadingMaterial implements Comparable<Book> {

    enum Genre implements SubTypes {
        ADVENTURE, CLASSICS, MYSTERY, FANTASY, ROMANCE, SCI_FI, COMIC_BOOK, HISTORY, HORROR
    }

    private String author;
    private Genre genre;

    public Book(String name, LocalDate dateIssued, String publisher, String author, Genre genre) {
        super(name, dateIssued, publisher);
        this.author = author;
        this.genre = genre;
        this.tax = 2;
    }

    @Override
    public Library.ReadingMaterialType getReadingMaterialType() {
        return Library.ReadingMaterialType.BOOK;
    }

    @Override
    public int getMaxEndDuration() {
        return 300;
    }

    @Override
    public SubTypes getSubType() {
        return genre;
    }

    @Override
    public int compareTo(Book o) {
        return this.dateIssued.compareTo(o.dateIssued);
    }

}
