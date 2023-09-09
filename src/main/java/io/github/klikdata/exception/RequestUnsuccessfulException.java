package io.github.klikdata.exception;

public class RequestUnsuccessfulException extends Exception {
    private static final long serialVersionUID = 6949447731150358499L;

    public RequestUnsuccessfulException(String msg) {
        super(msg);
    }

    public RequestUnsuccessfulException(String msg, Throwable t) {
        super(msg, t);
    }
}
