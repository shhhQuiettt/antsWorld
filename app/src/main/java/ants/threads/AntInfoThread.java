package ants.threads;

import java.util.function.Consumer;

import javax.swing.Timer;

import ants.gui.AntGui;

/**
 * AntInfoThread
 *
 * Thread monitoring seleteced ant and writing its info to the info panel.
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
        Timer timer = new Timer(milisecondsPerFrame, e -> {
            if (antGui == null) {
                this.writeInfo.accept(null);
                return;
            }

            this.writeInfo.accept(antGui.getAnt().info());
            this.antGui.addInfoBorder();
        });
        timer.start();
    }

}
