package ants.threads;

import ants.anttypes.Ant;

/**
 * AntThread
 *
 * Thread responsible for running an ant
 */
public class AntThread extends Thread {
    Ant ant;

    public AntThread(Ant ant) {
        this.ant = ant;
    }

    @Override
    public void run() {
        while (Thread.currentThread().isInterrupted() == false) {
            this.ant.iterateAction();
        }
    }
}
