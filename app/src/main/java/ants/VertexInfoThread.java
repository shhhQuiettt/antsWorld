package ants;

import javax.swing.Timer;
import java.util.function.Consumer;

/**
 * vertexInfoThread
 */
public class VertexInfoThread extends Thread {
    private VertexGui vertexGui = null;
    private Consumer<String> writeInfo;


    VertexInfoThread(Consumer<String> writeInfo) {
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
                return;
            }

            this.writeInfo.accept(vertexGui.vertex.info());
            this.vertexGui.addInfoBorder();
        });

        timer.start();

    }
}
