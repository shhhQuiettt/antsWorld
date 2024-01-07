package ants.gui;

/**
 * GuiUpdatable
 */
public abstract class UpdatableSprite extends Sprite {
    UpdatableSprite(String fileName) {
        super(fileName);
    }

    public abstract void update(int timeElapsed);
}
