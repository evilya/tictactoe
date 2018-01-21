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
    private MouseEvent lastClickPosition;

    public HumanPlayer(final GamePanel gamePanel, final Model model) {
        this.model = model;
        this.gamePanel = gamePanel;
        gamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                synchronized (clickMonitor) {
                    lastClickPosition = e;
                    clickMonitor.notify();
                }
            }
        });
    }

    public Position turn() {
        synchronized (clickMonitor) {
            while (lastClickPosition == null) {
                try {
                    clickMonitor.wait();
                } catch (InterruptedException e) {
                    return null;
                }
            }
            int gridSize = model.getFieldSize();
            int panelSize = gamePanel.getSquareSize();

            int xPosition = lastClickPosition.getX() * gridSize / panelSize;
            int yPosition = lastClickPosition.getY() * gridSize / panelSize;

            lastClickPosition = null;
            return new Position(xPosition, yPosition);
        }
    }
}
