public class BadRentException extends RuntimeException {

    public BadRentException() {
        super("Invalid rent");
    }
}
