package ants;

/**
 * GuiUpdatable
 */
public abstract class UpdatableSprite extends Sprite {
    // void update();
    UpdatableSprite(String fileName) {
        super(fileName);
    }

    public abstract void update(int timeElapsed);
}
