package org.paciencia.card;

import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Card extends Rectangle{

    private Suit suit;
    private int rank;
    private boolean faceUp;
    private BufferedImage face;
    private static BufferedImage back = null;

    public Card(Suit suit, int rank) {
        if (back == null) {
            back = getImage("cards-img/card_back.png");
        }
        this.face = getImage("cards-img/" + getCardName(suit, rank));
        this.suit = suit;
        this.rank = rank;
        this.setSize(face.getWidth(), face.getHeight());
    }

    private String getCardName(Suit suit, int rank) {
        String value = switch (rank) {
            case 11 -> "jack";
            case 12 -> "queen";
            case 13 -> "king";
            default -> Integer.toString(rank);
        };
        return suit.name()+ "_" + value + ".png";
    }

    private @NotNull BufferedImage getImage(String path) {
        try {
            BufferedImage srcImage = ImageIO.read(new File(path));
            Image img = srcImage.getScaledInstance(100, 140, Image.SCALE_SMOOTH);
            BufferedImage bf = new BufferedImage(100, 140, BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2d = bf.createGraphics();
            g2d.drawImage(img, 0, 0, null);
            g2d.dispose();

            return bf;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public Suit getSuit() {
        return suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public boolean isFaceUp() {
        return faceUp;
    }

    public void flip() {
        faceUp = true;
    }
}
