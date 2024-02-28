package com.burukeyou.smartdi.exceptions;

public class AutowiredInjectBeanException  extends RuntimeException {

    private static final long serialVersionUID = -1971377706334841189L;


    public AutowiredInjectBeanException() {
    }

    public AutowiredInjectBeanException(String message) {
        super(message);
    }

    public AutowiredInjectBeanException(String message, Throwable cause) {
        super(message, cause);
    }

    public AutowiredInjectBeanException(Throwable cause) {
        super(cause);
    }

    public AutowiredInjectBeanException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
