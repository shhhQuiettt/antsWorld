package ants;

import java.util.concurrent.Semaphore;

/**
 * Command
 */
public abstract class Command {
    public final Semaphore executionSemaphore = new Semaphore(0);

    abstract protected boolean execute();
}
