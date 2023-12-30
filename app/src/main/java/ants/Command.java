package ants;

import java.util.concurrent.Semaphore;

/**
 * Command
 */
public abstract class Command {
    public final Semaphore semaphore = new Semaphore(0);

    abstract public Ant[] getInvolvedAnts();

    abstract protected void execute();

    private void lockAnts() {
        for (Ant ant : getInvolvedAnts()) {
            ant.mutex.lock();
        }
    }

    private void unlockAnts() {
        for (Ant ant : getInvolvedAnts()) {
            ant.mutex.unlock();
        }
    }

    public void lockAndExecute() {
        this.lockAnts();
        if (getInvolvedAnts().length > 0) {
            Vertex currentVertex = getInvolvedAnts()[0].getCurrentVertex();

            for (Ant ant : getInvolvedAnts()) {
                if (ant.getCurrentVertex() != currentVertex) {
                    this.semaphore.release();
                    return;
                }
            }
        }
        execute();
        this.unlockAnts();
        this.semaphore.release();
    };
}
