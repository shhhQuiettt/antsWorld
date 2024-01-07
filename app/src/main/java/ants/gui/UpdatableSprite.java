package ants.gui;

/**
 * UpdatableSprite
 *
 * This class is used to create sprites that can be updated.
 */
public abstract class UpdatableSprite extends Sprite {
    UpdatableSprite(String fileName) {
        super(fileName);
    }

    public abstract void update();
}
