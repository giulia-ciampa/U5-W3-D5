package giuliaciampa.U5_W3_D5.exceptions;

public class DuplicateRequestException extends RuntimeException {
    public DuplicateRequestException(String message) {
        super(message);
    }
}
