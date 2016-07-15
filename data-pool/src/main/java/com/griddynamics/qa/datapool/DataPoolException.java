package com.griddynamics.qa.datapool;

/**
 * @author Alexey Lyanguzov.
 */
public class DataPoolException extends RuntimeException{
    public DataPoolException(String message, Object...args) {
        super(String.format(message, args));
    }

    public DataPoolException(Throwable cause) {
        super(cause);
    }
}
