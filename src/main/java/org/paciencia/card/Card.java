package org.paciencia.card;

import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Card extends Rectangle{

    private Suit suit;
    private int rank;
    private boolean faceUp;
    private BufferedImage face;
    private static BufferedImage back = null;
    private static BufferedImage backLowOpacity = null;

    public Card(Suit suit, int rank) {
        if (back == null) {
            back = getImage("/cards-img/card_back.png");
        }
        if (backLowOpacity == null) {
            backLowOpacity = getImage("/cards-img/back_low_opacity.png");
        }
        this.face = getImage("/cards-img/" + getCardName(suit, rank));
        this.suit = suit;
        this.rank = rank;
        this.setSize(face.getWidth(), face.getHeight());
    }

    public BufferedImage getFace() {
        return face;
    }

    public static BufferedImage getBack() {
        return back;
    }

    public static BufferedImage getBackLowOpacity() {
        return backLowOpacity;
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
            BufferedImage srcImage = ImageIO.read(getClass().getResourceAsStream(path));
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

    public void flipUp() {
        faceUp = true;
    }

    public void flipDown() {
        faceUp = false;
    }

    public boolean isBlack() {
        return (this.getSuit() == Suit.CLUBS || this.getSuit() == Suit.SPADES);
    }

    public boolean isRed() {
        return (this.getSuit() == Suit.HEARTS || this.getSuit() == Suit.DIAMONDS);
    }

}
