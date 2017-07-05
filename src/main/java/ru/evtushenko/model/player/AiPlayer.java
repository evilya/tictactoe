package ru.evtushenko.model.player;

import ru.evtushenko.model.Model;
import ru.evtushenko.model.Position;
import ru.evtushenko.model.Shape;

public class AiPlayer implements Player {

    private final int[][] preferredMoves = {
            {1, 1}, {0, 0}, {0, 2},
            {2, 0}, {2, 2}, {0, 1},
            {1, 0}, {1, 2}, {2, 1}};

    private final Shape[][] field;

    public AiPlayer(Model model) {
        field = model.getField();
    }

    public Position turn() {
        for (int[] move : preferredMoves) {
            if (field[move[1]][move[0]] == Shape.EMPTY) {
                return new Position(move[0], move[1]);
            }
        }
        throw new AiException("There are no possible turns");
    }
}
