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
        // while (this.ant.getState() != AntState.DEAD) {
        while (true) {
            this.ant.lockAndDoActions();
        }

        // wait until inturrupted (blocking)
        // System.out.println("Exitin ant thread loop");
    }
}
