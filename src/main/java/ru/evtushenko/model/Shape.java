package ru.evtushenko.model;

public enum Shape {
    X, O, EMPTY;

    public Shape opposite() {
        switch (this) {
            case O:
                return X;
            case X:
                return O;
            default:
                return EMPTY;
        }
    }
}
