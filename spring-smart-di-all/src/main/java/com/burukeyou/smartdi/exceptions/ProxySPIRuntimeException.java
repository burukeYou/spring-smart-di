package com.burukeyou.smartdi.exceptions;

public class ProxySPIRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 6508488585962440779L;


    public ProxySPIRuntimeException() {
    }

    public ProxySPIRuntimeException(String message) {
        super(message);
    }

    public ProxySPIRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProxySPIRuntimeException(Throwable cause) {
        super(cause);
    }

    public ProxySPIRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
