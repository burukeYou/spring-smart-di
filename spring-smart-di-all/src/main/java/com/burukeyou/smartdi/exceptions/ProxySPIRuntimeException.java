package com.burukeyou.smartdi.exceptions;

public class ProxySPIRuntimeException extends RuntimeException {

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
