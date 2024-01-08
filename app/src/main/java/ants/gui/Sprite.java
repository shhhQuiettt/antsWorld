package ants.gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * Sprite
 *
 * Abstract class representing a sprite
 */
public abstract class Sprite extends JLabel {
    protected BufferedImage displayedImage;
    private final int marginMultiplier = 2;

    Sprite(String filename) {
        displayedImage = this.loadImage(filename);

        this.setLayout(null);
        setIcon(new ImageIcon(displayedImage));
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setVerticalAlignment(SwingConstants.CENTER);
        this.setOpaque(false);
    }

    public abstract void setPosition();

    public Point getRelativeCenter() {
        return new Point(getImageWidth() / 2 * this.marginMultiplier, getImageHeight() / 2 * this.marginMultiplier);
    }

    public int getImageWidth() {
        return this.displayedImage.getWidth();
    }

    public int getImageHeight() {
        return this.displayedImage.getHeight();
    }

    public void setDisplayedImage(BufferedImage displayedImage) {
        if (displayedImage == null) {
            System.err.println("Error: null image passed to setDisplayedImage");
            return;
        }
        this.displayedImage = displayedImage;
        this.setIcon(new ImageIcon(displayedImage));
    }

    public BufferedImage getDisplayedImage() {
        return displayedImage;
    }

    public void updateOpacity(float opacity) {
        if (opacity < 0 || opacity > 1) {
            System.err.println("Error: opacity must be between 0 and 1");
            return;
        }

        BufferedImage newImage = new BufferedImage(displayedImage.getWidth(), displayedImage.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        g.drawImage(displayedImage, 0, 0, null);
        g.dispose();

        setDisplayedImage(newImage);
    }

    public Point getPosition() {
        return getBounds().getLocation();
    }

    public void addInfoBorder() {
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    public void removeInfoBorder() {
        this.setBorder(null);
    }

    public boolean imageContains(Point point) {
        Rectangle allAread = this.getBounds();
        Rectangle imageArea = new Rectangle(allAread.x + this.getImageWidth() / 2,
                allAread.y + this.getImageHeight() / 2, this.getImageWidth(), this.getImageHeight());

        return imageArea.contains(point);
    }

    protected void setPosition(int x, int y) {
        int xLeftUpper = (int) (x - (this.getImageWidth() / 2 * this.marginMultiplier));
        int yLeftUpper = (int) (y - (this.getImageHeight() / 2 * this.marginMultiplier));
        int width = this.marginMultiplier * this.getImageWidth();
        int height = this.marginMultiplier * this.getImageHeight();

        setBounds(xLeftUpper, yLeftUpper, width, height);
    }

    protected BufferedImage loadImage(String filename) {
        try {
            return ImageIO.read(getClass().getResource("/images/" + filename + ".png"));
        } catch (IOException e) {
            System.err.println("Error while loading image: " + filename + ".png");
            System.err.println("\t System message: " + e.getMessage());
            return null;
        }
    }

}
