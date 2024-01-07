package ants;

import java.util.function.Consumer;
import ants.gui.AntGui;

import javax.swing.Timer;

/**
 * AntInfoThread
 */
public class AntInfoThread extends Thread {

    private AntGui antGui = null;
    Consumer<String> writeInfo;

    public AntInfoThread(Consumer<String> writeInfo) {
        this.writeInfo = writeInfo;
    }

    public void setAntGui(AntGui antGui) {
        if (this.antGui != null)
            this.antGui.removeInfoBorder();
        this.antGui = antGui;
    }

    @Override
    public void run() {
        int milisecondsPerFrame = 1000 / 60;
        System.out.println("AntInfoThread: " + Thread.currentThread().getName() + " started");

        Timer timer = new Timer(milisecondsPerFrame, e -> {
            if (antGui == null) {
                return;
            }

            // System.out.println("Ant info writing on thread: " + Thread.currentThread().getName());
            this.writeInfo.accept(antGui.getAnt().info());
            this.antGui.addInfoBorder();
        });
        timer.start();

        System.out.println("fini?");

    }

}
