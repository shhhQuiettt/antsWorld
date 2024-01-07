package ants.threads;

import java.util.function.Consumer;

import javax.swing.Timer;

import ants.gui.VertexGui;

/**
 * VertexInfoThread
 */
public class VertexInfoThread extends Thread {
    private VertexGui vertexGui = null;
    private Consumer<String> writeInfo;

    public VertexInfoThread(Consumer<String> writeInfo) {
        this.writeInfo = writeInfo;
    }

    public void setVertexGui(VertexGui vertexGui) {
        if (this.vertexGui != null)
            this.vertexGui.removeInfoBorder();

        this.vertexGui = vertexGui;
    }

    @Override
    public void run() {
        int milisecondsPerFrame = 1000 / 60;

        Timer timer = new Timer(milisecondsPerFrame, e -> {
            if (vertexGui == null) {
                this.writeInfo.accept(null);
                return;
            }

            this.writeInfo.accept(vertexGui.getVertex().info());
            this.vertexGui.addInfoBorder();
        });

        timer.start();

    }
}
