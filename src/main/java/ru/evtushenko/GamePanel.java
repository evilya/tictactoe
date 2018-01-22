package ru.evtushenko;

import ru.evtushenko.model.Shape;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class GamePanel extends JPanel implements Observer {

    private Shape[][] field;
    private int squareSize;
    private int padding;
    private int cellSize;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setMinimumSize(new Dimension(250, 250));
        squareSize = Math.min(getHeight(), getWidth());
        cellSize = getSquareSize() / field.length;
        padding = (int) (0.2 * cellSize);
        ((Graphics2D) g).setStroke(
                new BasicStroke(3.f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        drawGrid(g);
        drawShapes(g);
    }

    private void drawShapes(Graphics g) {
        for (int i = 0; i < field.length; i++) {
            int x = squareSize * i / field.length;
            for (int j = 0; j < field.length; j++) {
                int y = squareSize * j / field.length;
                drawShape(g, x, y, field[j][i]);
            }
        }
    }

    private void drawGrid(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.drawRect(0, 0, squareSize - 1, squareSize - 1);
        for (int i = 0; i < field.length - 1; i++) {
            int gridOffset = squareSize * (i + 1) / (field.length);
            g.drawLine(gridOffset, 0, gridOffset, squareSize);
            g.drawLine(0, gridOffset, squareSize, gridOffset);
        }
    }

    public void update(Observable o, Object arg) {
        field = (Shape[][]) arg;
        repaint();
    }

    private void drawShape(Graphics g, int x, int y, Shape shape) {
        switch (shape) {
            case X:
                g.setColor(Color.BLUE);
                g.drawLine(x + padding,
                        y + padding,
                        x + cellSize - padding,
                        y + cellSize - padding);
                g.drawLine(x + cellSize - padding,
                        y + padding,
                        x + padding,
                        y + cellSize - padding);
                break;
            case O:
                g.setColor(Color.RED);
                g.drawOval(x + padding,
                        y + padding,
                        cellSize - 2 * padding,
                        cellSize - 2 * padding);
                break;
            default:
                break;
        }

    }

    public int getSquareSize() {
        return squareSize;
    }
}
