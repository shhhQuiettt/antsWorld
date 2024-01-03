package ants;

/**
 * AntThread
 */
public class AntThread extends Thread {
    Ant ant;

    AntThread(Ant ant) {
        this.ant = ant;
    }

    @Override
    public void run() {
        while (this.ant.getState() != AntState.DEAD) {
            this.ant.lockAndDoActions();
        }
    }
}
