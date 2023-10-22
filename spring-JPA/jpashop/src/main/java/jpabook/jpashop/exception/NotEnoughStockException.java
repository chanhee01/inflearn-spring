package jpabook.jpashop.exception;

public class NotEnoughStockException extends RuntimeException {
    // extends 한 다음에 함수들 오버라이딩
    public NotEnoughStockException() {
        super();
    }

    public NotEnoughStockException(String message) {
        super(message);
    }

    public NotEnoughStockException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughStockException(Throwable cause) {
        super(cause);
    }
}
