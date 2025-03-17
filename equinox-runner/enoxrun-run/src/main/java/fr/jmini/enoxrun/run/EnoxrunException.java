package fr.jmini.enoxrun.run;

public class EnoxrunException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public EnoxrunException(String message) {
        super(message);
    }

    public EnoxrunException(String message, Throwable cause) {
        super(message, cause);
    }
}
