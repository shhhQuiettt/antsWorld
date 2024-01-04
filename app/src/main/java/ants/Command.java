package ants;

import java.util.concurrent.Semaphore;

/**
 * Command
 */
public abstract class Command {
    public final Semaphore executionSemaphore = new Semaphore(0);

    abstract protected boolean execute();

    // public void lockAndExecute() {
    //     if (getInvolvedAnts().length > 0) {
    //         Vertex currentVertex = getInvolvedAnts()[0].getCurrentVertex();

    //         for (Ant ant : getInvolvedAnts()) {
    //             if (ant.getCurrentVertex() != currentVertex) {
    //                 this.executionSemaphore.release();
    //                 return;
    //             }
    //         }
    //     }
    //     execute();
    //     this.unlockAnts();
    //     this.executionSemaphore.release();
    // };
}
