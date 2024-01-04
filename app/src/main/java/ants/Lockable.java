package ants;

import java.util.concurrent.locks.Lock;

/**
 * Lockable
 */
public interface Lockable {
    public Lock getMutex();
}
