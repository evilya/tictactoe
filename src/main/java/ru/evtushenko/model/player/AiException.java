package ru.evtushenko.model.player;

public class AiException extends IllegalStateException {
    public AiException() {
        super();
    }

    public AiException(String s) {
        super(s);
    }

    public AiException(String message, Throwable cause) {
        super(message, cause);
    }
}
