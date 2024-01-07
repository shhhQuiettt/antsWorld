package ants.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class Sprite extends JLabel {
    protected BufferedImage displayedImage;
    private final int marginMultiplier = 2;

    Sprite(String filename) {
        displayedImage = this.loadImage(filename);

        this.setLayout(null);
        // this.
        setIcon(new ImageIcon(displayedImage));
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setVerticalAlignment(SwingConstants.CENTER);
        this.setOpaque(false);
        // setPosition(0, 0);

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

    protected void setPosition(int x, int y) {
        int xLeftUpper = (int) (x - (this.getImageWidth() / 2 * this.marginMultiplier));
        int yLeftUpper = (int) (y - (this.getImageHeight() / 2 * this.marginMultiplier));
        int width = this.marginMultiplier * this.getImageWidth();
        int height = this.marginMultiplier * this.getImageHeight();

        setBounds(xLeftUpper, yLeftUpper, width, height);
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

    protected BufferedImage loadImage(String filename) {
        try {
            return ImageIO.read(new File("images/" + filename + ".png"));
        } catch (IOException e) {
            System.err.println("Error while loading image: " + filename + ".png");
            System.err.println("\t System message: " + e.getMessage());
            return null;
        }
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
}
