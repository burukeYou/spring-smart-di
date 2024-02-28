package com.burukeyou.smartdi.exceptions;

public class SPIBeanTypeMismatchException extends RuntimeException{

    public SPIBeanTypeMismatchException() {
        super();
    }

    public SPIBeanTypeMismatchException(String message) {
        super(message);
    }

    public SPIBeanTypeMismatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public SPIBeanTypeMismatchException(Throwable cause) {
        super(cause);
    }

    protected SPIBeanTypeMismatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
