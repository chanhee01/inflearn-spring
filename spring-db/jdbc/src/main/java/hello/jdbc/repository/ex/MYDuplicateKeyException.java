package hello.jdbc.repository.ex;

public class MYDuplicateKeyException extends MyDbException{
    public MYDuplicateKeyException() {
    }

    public MYDuplicateKeyException(String message) {
        super(message);
    }

    public MYDuplicateKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    public MYDuplicateKeyException(Throwable cause) {
        super(cause);
    }
}
