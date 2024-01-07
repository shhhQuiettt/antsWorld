package ants.commands;

import java.util.concurrent.Semaphore;

/**
 * Command
 *
 * These are issued, when there is a need to perform an action
 * typically including multiple ants, which need an arbiter.
 */
public abstract class Command {
    public final Semaphore executionSemaphore = new Semaphore(0);

    abstract public boolean execute();
}
