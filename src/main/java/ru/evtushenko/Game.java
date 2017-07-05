package ru.evtushenko;

import ru.evtushenko.model.Model;
import ru.evtushenko.model.Position;
import ru.evtushenko.model.Shape;
import ru.evtushenko.model.player.AiPlayer;
import ru.evtushenko.model.player.HumanPlayer;
import ru.evtushenko.model.player.Player;
import ru.evtushenko.view.GamePanel;

import javax.swing.*;
import java.util.Random;

class Game implements Runnable {

    private final GamePanel gamePanel;
    private final Model model;
    private final Player firstPlayer;
    private final Player secondPlayer;

    public Game(final GamePanel gamePanel, final Model model) {
        this.model = model;
        this.gamePanel = gamePanel;
        this.firstPlayer = new HumanPlayer(gamePanel, model);
        this.secondPlayer = new AiPlayer(model);
    }

    private void showDialog(TurnResult result) {
        String message;
        if (result == TurnResult.TIE) {
            message = "Tie\n";
        } else {
            message = model.getCurrentShape().name() + " won\n";
        }
        int dialogOption = JOptionPane.showOptionDialog(gamePanel,
                message + "Start new game?",
                "The end!",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{"New game", "Exit"},
                null);
        if (dialogOption == JOptionPane.YES_OPTION) {
            model.clear();
        } else {
            System.exit(0);
        }
    }

    public void run() {
        Player currentPlayer =
                new Random(System.currentTimeMillis()).nextBoolean() ? firstPlayer : secondPlayer;
        while (true) {
            Position position = currentPlayer.turn();
            int x = position.getX();
            int y = position.getY();
            int fieldSize = model.getFieldSize();
            if (x < 0
                    || x >= fieldSize
                    || y < 0
                    || y >= fieldSize
                    || model.getField()[y][x] != Shape.EMPTY) {
                continue;
            }

            TurnResult result = model.setShape(y, x);
            if (result != TurnResult.NONE) {
                showDialog(result);
            }

            currentPlayer = currentPlayer == firstPlayer ? secondPlayer : firstPlayer;
        }
    }
}
