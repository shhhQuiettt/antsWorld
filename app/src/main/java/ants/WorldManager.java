package ants;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.Queue;

/**
 * WorldManager
 */

public class WorldManager {
    private final Map map;
    private ArrayList<Ant> ants;
    private Gui gui;
    private WorldConfig config;
    private ArrayList<AntThread> antThreads;

    public WorldManager(WorldConfig config) {
        this.map = Map.generateRandomMap(config.vertexNumber,
                config.stoneProbability,
                config.leafProbability,
                config.width,
                config.height);

        this.config = config;
        // this.ants = new ArrayBlockingQueue<Ant>(config.maxRedAnts +
        // config.maxBlueAnts);
        this.ants = new ArrayList<Ant>(config.maxRedAnts + config.maxBlueAnts);

        this.antThreads = new ArrayList<AntThread>(config.maxRedAnts + config.maxBlueAnts);

    }

    public void run() {
        this.gui = new Gui(this.config.width, this.config.height);

        for (int i = 0; i < 1; i++) {
            Ant ant = new SoldierAnt("red" + i, 100, 1, map.getRedAnthill());
            this.ants.add(ant);
            this.gui.addUpdatableSprite(new AntGui(ant, "antRed", "antRedAttacking", "antRedDying", "antRedDead"));
        }

        for (int i = 0; i < 1; i++) {
            Ant ant = new WorkerAnt("blue" + i, 100, 1, map.getBlueAnthill());
            this.ants.add(ant);
            this.gui.addUpdatableSprite(new AntGui(ant, "antBlue", "antBlueAttacking", "antBlueDying", "antBlueDead"));
        }


        for (Vertex v : this.map.getVertices()) {
            this.gui.addStaticSprite(new VertexGui(v));
        }

        this.gui.addStaticSprite(new AnthillGui(this.map.getRedAnthill()));
        this.gui.addStaticSprite(new AnthillGui(this.map.getBlueAnthill()));

        // for (int i = 0; i < this.antThreads.length; i++) {
        //
        for (Ant ant : this.ants) {
            this.antThreads.add(new AntThread(ant));
        }

        for (AntThread antThread : this.antThreads) {
            antThread.start();
        }

        while (true) {
            gui.update();
            System.out.println("Manager update");
        }
    }
}
