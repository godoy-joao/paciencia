package org.paciencia;


import org.paciencia.game.Solitaire;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Paciência");
        Solitaire game = new Solitaire();
        frame.add(game);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        game.start();

    }
}