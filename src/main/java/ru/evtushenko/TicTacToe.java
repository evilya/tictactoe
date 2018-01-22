package ru.evtushenko;

import ru.evtushenko.model.Model;

import javax.swing.*;

class TicTacToe extends JFrame {

    private final Game game;

    private TicTacToe() {
        setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Model model = new Model();
        GamePanel gamePanel = new GamePanel();
        model.addObserver(gamePanel);
        add(gamePanel);
        game = new Game(gamePanel, model);
    }

    public static void main(String[] args) {
        TicTacToe frame = new TicTacToe();
        frame.setVisible(true);
        frame.game.run();
    }
}
