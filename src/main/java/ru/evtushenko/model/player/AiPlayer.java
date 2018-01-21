package ru.evtushenko.model.player;

import ru.evtushenko.model.Model;
import ru.evtushenko.model.Position;
import ru.evtushenko.model.Shape;

import java.util.ArrayList;
import java.util.List;

public class AiPlayer implements Player {

    private final Shape[][] gameField;
    private final Shape currentShape;
    private final int fieldSize;

    public AiPlayer(Model model) {
        gameField = model.getField();
        currentShape = model.getCurrentShape();
        fieldSize = model.getFieldSize();
    }

    @Override
    public Position turn() {
        int[] result = bestTurnDepthSearch(2, currentShape);
        return new Position(result[0], result[1]);
    }

    private int[] bestTurnDepthSearch(int depth, Shape player) {
        List<Position> possibleMoves = getPossibleMoves();

        int maxScore = (player == currentShape) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int currentScore;
        int resultRow = -1;
        int resultCol = -1;

        if (possibleMoves.isEmpty() || depth == 0) {
            maxScore = evaluate();
        } else {
            for (Position move : possibleMoves) {
                gameField[move.getY()][move.getX()] = player;
                if (player == currentShape) {
                    currentScore = bestTurnDepthSearch(depth - 1, currentShape.opposite())[2];
                    if (currentScore > maxScore) {
                        maxScore = currentScore;
                        resultCol = move.getX();
                        resultRow = move.getY();
                    }
                } else {
                    currentScore = bestTurnDepthSearch(depth - 1, currentShape)[2];
                    if (currentScore < maxScore) {
                        maxScore = currentScore;
                        resultCol = move.getX();
                        resultRow = move.getY();
                    }
                }
                gameField[move.getY()][move.getX()] = Shape.EMPTY;
            }
        }
        return new int[]{resultCol, resultRow, maxScore};
    }

    private List<Position> getPossibleMoves() {
        List<Position> nextMoves = new ArrayList<>();
        for (int row = 0; row < fieldSize; row++) {
            for (int col = 0; col < fieldSize; col++) {
                if (gameField[row][col] == Shape.EMPTY) {
                    nextMoves.add(new Position(col, row));
                }
            }
        }
        return nextMoves;
    }

    private int evaluate() {
        int score = 0;
        score += evaluateByColumns();
        score += evaluateByRows();
        score += evaluateMainDiagonal();
        score += evaluateAntiDiagonal();
        return score;
    }

    private int evaluateByRows() {
        int score = 0;
        for (int row = 0; row < fieldSize; row++) {
            int myShapes = 0;
            int oppShapes = 0;
            for (int i = 0; i < fieldSize; i++) {
                if (gameField[row][i] == currentShape) {
                    myShapes++;
                } else if (gameField[row][i] == currentShape.opposite()) {
                    oppShapes++;
                }
            }
            if (myShapes > 0 && oppShapes == 0) {
                score += Math.pow(10, myShapes - 1);
            } else if (oppShapes > 0 && myShapes == 0) {
                score -= (int) Math.pow(10, oppShapes - 1);
            }
        }
        return score;
    }

    private int evaluateByColumns() {
        int score = 0;
        for (int col = 0; col < fieldSize; col++) {
            int myShapes = 0;
            int oppShapes = 0;
            for (int i = 0; i < fieldSize; i++) {
                if (gameField[i][col] == currentShape) {
                    myShapes++;
                } else if (gameField[i][col] == currentShape.opposite()) {
                    oppShapes++;
                }
            }
            if (myShapes > 0 && oppShapes == 0) {
                score += Math.pow(10, myShapes - 1);
            } else if (oppShapes > 0 && myShapes == 0) {
                score -= (int) Math.pow(10, oppShapes - 1);
            }
        }
        return score;
    }

    private int evaluateMainDiagonal() {
        int score = 0;
        int myShapes = 0;
        int oppShapes = 0;
        for (int i = 0; i < fieldSize; i++) {
            if (gameField[i][i] == currentShape) {
                myShapes++;
            } else if (gameField[i][i] == currentShape.opposite()) {
                oppShapes++;
            }
        }
        if (myShapes > 0 && oppShapes == 0) {
            score += Math.pow(10, myShapes - 1);
        } else if (oppShapes > 0 && myShapes == 0) {
            score -= (int) Math.pow(10, oppShapes - 1);
        }
        return score;
    }

    private int evaluateAntiDiagonal() {
        int score = 0;
        int myShapes = 0;
        int oppShapes = 0;
        for (int i = 0; i < fieldSize; i++) {
            if (gameField[i][fieldSize -1-i] == currentShape) {
                myShapes++;
            } else if (gameField[i][fieldSize -1-i] == currentShape.opposite()) {
                oppShapes++;
            }
        }
        if (myShapes > 0 && oppShapes == 0) {
            score += Math.pow(10, myShapes - 1);
        } else if (oppShapes > 0 && myShapes == 0) {
            score -= (int) Math.pow(10, oppShapes - 1);
        }
        return score;
    }

}
