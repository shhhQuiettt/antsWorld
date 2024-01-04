package ants;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;

/**
 * GuiUpdatable
 */
public abstract class UpdatableSprite extends Sprite {
    //mutex
    public final Lock mutex = new ReentrantLock();
    // void update();
    UpdatableSprite(String fileName) {
        super(fileName);
    }

    

    public abstract void update(int timeElapsed);
}
