package org.paciencia.game;

import org.paciencia.card.Deck;
import org.paciencia.control.InputHandler;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Solitaire extends JPanel implements Runnable {

    public static List<Rectangle> foundationsRect;
    public static Rectangle pileRect;
    public static Rectangle wasteRect;

    private final Thread thread;
    private InputHandler handler;
    private boolean running;

    public Solitaire() {
        setSize(1366, 768);
        setBackground(new Color(176, 182, 210));
        thread = new Thread(this);
        handler = new InputHandler();
        pileRect = new Rectangle(40, 50, 250, 140);
        wasteRect = new Rectangle(300, 50, 250, 140);
        foundationsRect.add(new Rectangle(getWidth() - 40, 50, 100, 140));
        foundationsRect.add(new Rectangle(getWidth() - 150, 50, 100, 140));
        foundationsRect.add(new Rectangle(getWidth() - 260, 50, 100, 140));
        foundationsRect.add(new Rectangle(getWidth() - 370, 50, 100, 140));
        addMouseListener(handler);
        addMouseMotionListener(handler);
    }

    public void start() {
        if (running) return;
        running = true;
        thread.start();
        Deck.createDeck();
    }

    public void stop() {
        if (!running) return;
        try {
            thread.join();
        } catch (InterruptedException e) {
           System.exit(0);
        }
    }

    @Override
    public void run() {
        while (running) {
            if (Render.hasChanges) {
                repaint();
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        Render.render(g2d);
    }
}
