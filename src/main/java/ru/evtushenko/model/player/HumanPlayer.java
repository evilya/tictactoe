package ru.evtushenko.model.player;

import ru.evtushenko.view.GamePanel;
import ru.evtushenko.model.Model;
import ru.evtushenko.model.Position;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HumanPlayer implements Player {
    private final Object clickMonitor = new Object();

    private final GamePanel gamePanel;
    private final Model model;
    private MouseEvent lastClick;

    public HumanPlayer(final GamePanel gamePanel, final Model model) {
        this.model = model;
        this.gamePanel = gamePanel;
        gamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                synchronized (clickMonitor) {
                    lastClick = e;
                    clickMonitor.notify();
                }
            }
        });
    }

    public Position turn() {
        synchronized (clickMonitor) {
            while (lastClick == null) {
                try {
                    clickMonitor.wait();
                } catch (InterruptedException e) {
                    return null;
                }
            }
            int gridSize = model.getFieldSize();
            int panelSize = gamePanel.getSquareSize();

            int xPosition = lastClick.getX() * gridSize / panelSize;
            int yPosition = lastClick.getY() * gridSize / panelSize;

            lastClick = null;
            return new Position(xPosition, yPosition);
        }
    }
}
