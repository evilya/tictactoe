package ru.evtushenko.model.player;

public class NoTurnsException extends IllegalStateException {
    public NoTurnsException() {
        super();
    }

    public NoTurnsException(String s) {
        super(s);
    }

    public NoTurnsException(String message, Throwable cause) {
        super(message, cause);
    }
}
