package ants;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.Queue;

/**
 * WorldManager
 */

public class WorldManager {
    private final Map map;
    private BlockingQueue<Ant> ants;
    private Gui gui;
    private WorldConfig config;

    public WorldManager(WorldConfig config) {
        this.map = Map.generateRandomMap(config.vertexNumber,
                config.stoneProbability,
                config.leafProbability,
                config.width,
                config.height);

        SoldierAnt tempRedAnt = new SoldierAnt("redi", 2, 5, map.getRedAnthill());
        WorkerAnt tempBlueAnt = new WorkerAnt("bluei", 2, 5, map.getBlueAnthill());

        this.config = config;
        this.ants = new ArrayBlockingQueue<Ant>(config.maxRedAnts + config.maxBlueAnts);

        this.ants.add(tempRedAnt);
        this.ants.add(tempBlueAnt);

    }

    public void run() {
        this.gui = new Gui(this.config.width, this.config.height);
        for (Ant ant : this.ants) {
            if (ant.getColor() == Color.RED) {
                this.gui.addUpdatableSprite(new AntGui(ant));
            } else {
                this.gui.addUpdatableSprite(new AntGui(ant));
            }
        }

        for (Vertex v : this.map.getVertices()) {
            this.gui.addStaticSprite(new VertexGui(v));
        }

        while (1 == 1) {
            gui.update();
            // System.out.println("update");
        }
    }

}
