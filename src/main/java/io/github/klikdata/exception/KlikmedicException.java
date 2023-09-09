package io.github.klikdata.exception;

public class KlikmedicException extends Exception {
    static final long serialVersionUID = -925220451573356906L;

    private String id;

    private int httpStatusCode;

    public KlikmedicException(String msg) {
        super(msg);
    }

    public KlikmedicException(String msg, Throwable t) {
        super(msg, t);
    }

    public KlikmedicException(String msg, String id, int statusCode) {
        super(msg);
        this.id = id;
        this.httpStatusCode = statusCode;
    }

    public String getId() {
        return id;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }
}
