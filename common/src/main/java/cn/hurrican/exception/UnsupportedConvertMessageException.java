package cn.hurrican.exception;

public class UnsupportedConvertMessageException extends RuntimeException {

    public UnsupportedConvertMessageException() {
    }

    public UnsupportedConvertMessageException(String message) {
        super(message);
    }

    public UnsupportedConvertMessageException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedConvertMessageException(Throwable cause) {
        super(cause);
    }

    public UnsupportedConvertMessageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
