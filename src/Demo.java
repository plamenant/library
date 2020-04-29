import java.time.LocalDate;

public class Demo {
    public static void main(String[] args) throws InterruptedException {

        Library library = new Library();
        Magazine magazine = new Magazine("National Geographic",
                LocalDate.now(), "NG Media", 1, Magazine.Category.NATURE);
        Magazine magazine2 = new Magazine("National Geographic",
                LocalDate.now(), "NG Media", 2, Magazine.Category.NATURE);
        Magazine magazine3 = new Magazine("National Geographic",
                LocalDate.now(), "NG Media", 3, Magazine.Category.NATURE);
        TextBook textBook = new TextBook("Mathematics for 8 grade",
                LocalDate.of(1998, 01, 13),
                "Prosveta", TextBook.Subject.MATHEMATICS);
        TextBook textBook2 = new TextBook("English for 8 grade",
                LocalDate.of(1999, 01, 13),
                "Prosveta", TextBook.Subject.ENGLISH);
        Book book1 = new Book("The Dark Half", LocalDate.of(1989, 02, 03),
                "Grant", "Steven King", Book.Genre.HORROR);
        Book book2 = new Book("TMisery", LocalDate.of(2013, 02, 03),
                "Grant", "Steven King", Book.Genre.HORROR);
        library.addReadingMaterial(magazine);
        library.addReadingMaterial(magazine2);
        library.addReadingMaterial(magazine3);
        library.addReadingMaterial(textBook);
        library.addReadingMaterial(textBook2);
        library.addReadingMaterial(book1);
        library.addReadingMaterial(book2);
        library.rent(book1, 10);
        Thread.sleep(15000);// The book is not returned in time
        library.returnReadingMaterial(book1);
        library.printAllAvailabilityCount();

    }
}
