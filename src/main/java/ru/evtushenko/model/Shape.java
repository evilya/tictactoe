package ru.evtushenko.model;

public enum Shape {
    X, O, EMPTY;

    public Shape opposite() {
        if (this == X) {
            return O;
        } else if (this == O) {
            return X;
        } else {
            return EMPTY;
        }
    }
}
