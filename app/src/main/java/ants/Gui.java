package ants;

import javax.swing.*;

/**
 * Gui
 */
public class Gui {
    JFrame frame;

    public Gui(int width, int height) {
        frame = new JFrame("Ants world");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
        frame.setVisible(true);
    }

}
