package ru.evtushenko.model;

import java.util.Observable;
import java.util.Observer;

public class Model extends Observable {
    private final int FIELD_SIZE = 3;
    private final Shape[][] field;
    private Shape currentShape;
    private int turnNumber;

    public Model() {
        field = new Shape[FIELD_SIZE][FIELD_SIZE];
        clear();
    }

    public TurnResult setShape(int y, int x) {
        if (x < 0 || x >= FIELD_SIZE || y < 0 || y >= FIELD_SIZE) {
            throw new IllegalArgumentException();
        }

        field[y][x] = currentShape;

        setChanged();
        notifyObservers(field);

        boolean finished = isFinished(y, x);
        turnNumber++;
        if (finished) {
            if (currentShape == Shape.X) {
                return TurnResult.X_WON;
            } else {
                return TurnResult.O_WON;
            }
        } else if (turnNumber == FIELD_SIZE * FIELD_SIZE) {
            return TurnResult.TIE;
        }
        currentShape = currentShape.opposite();
        return TurnResult.NONE;
    }

    private boolean isFinished(int y, int x) {
        boolean horizontal = true;
        boolean vertical = true;
        boolean mainDiagonal = true;
        boolean antiDiagonal = true;
        for (int i = 0; i < FIELD_SIZE; i++) {
            if (field[i][x] != currentShape) {
                vertical = false;
            }
            if (field[y][i] != currentShape) {
                horizontal = false;
            }
            if (field[i][i] != currentShape) {
                mainDiagonal = false;
            }
            if (field[FIELD_SIZE - 1 - i][i] != currentShape) {
                antiDiagonal = false;
            }
        }
        return horizontal || vertical || mainDiagonal || antiDiagonal;
    }

    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
        setChanged();
        notifyObservers(field);
    }

    public Shape getCurrentShape() {
        return currentShape;
    }

    public void clear() {
        currentShape = Shape.X;
        turnNumber = 0;
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                field[i][j] = Shape.EMPTY;
            }
        }
        setChanged();
        notifyObservers(field);
    }

    public int getFieldSize() {
        return FIELD_SIZE;
    }

    public Shape[][] getField() {
        return field;
    }
}
