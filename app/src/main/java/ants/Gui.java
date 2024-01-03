package ants;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Gui
 */
public class Gui {
    private JFrame frame;
    private JPanel world;
    private final int secondPerFrame = 1000 / 60;

    private ArrayList<UpdatableSprite> updatebleComponents = new ArrayList<UpdatableSprite>(50);
    private ArrayList<Sprite> sprites = new ArrayList<Sprite>(50);

    public Gui(int width, int height) {
        this.frame = new JFrame("Ants world");
        this.frame.setSize(width, height);

        this.world = new JPanel();

        this.world.setLayout(null);
        // this.world.setPreferredSize(new Dimension(width, height));
        //

        this.frame.add(world);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);
    }

    public void addStaticSprite(Sprite sprite) {
        this.sprites.add(sprite);
        this.world.add(sprite);
        sprite.setPosition();
        sprite.setVisible(true);
    }

    public void addUpdatableSprite(UpdatableSprite animatedSprite) {
        this.updatebleComponents.add(animatedSprite);
        this.world.add(animatedSprite);
        animatedSprite.setPosition();
        animatedSprite.setVisible(true);
    }

    public void update() {
        for (UpdatableSprite updatableSprite : this.updatebleComponents) {
            updatableSprite.update(this.secondPerFrame);
        }
    }

}
