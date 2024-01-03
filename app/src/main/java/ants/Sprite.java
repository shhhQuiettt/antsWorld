package ants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class Sprite extends JLabel {
    protected BufferedImage displayedImage;

    Sprite(String filename) {
        displayedImage = this.loadImage(filename);
        setIcon(new ImageIcon(displayedImage));
        setPosition(0, 0);
    }

    public abstract void setPosition();

    protected void setPosition(int x, int y) {
        setBounds(x, y, getPreferredSize().width, getPreferredSize().height);
    }

    public void setDisplayedImage(BufferedImage displayedImage) {
        if (displayedImage == null) {
            System.err.println("Error: null image passed to setDisplayedImage");
            return;
        }
        this.displayedImage = displayedImage;
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
}
