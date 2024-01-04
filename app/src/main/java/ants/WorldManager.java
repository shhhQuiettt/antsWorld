package ants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;

/**
 * WorldManager
 */

public class WorldManager implements AntDeathSubscriber {
    private final Map map;
    private ArrayList<Ant> ants;
    private Gui gui;
    private final WorldConfig config;
    private ArrayList<AntThread> antThreads;
    private HashMap<Ant, AntThread> antThreadMap = new HashMap<Ant, AntThread>();
    private HashMap<Ant, AntGui> antGuiMap = new HashMap<Ant, AntGui>();
    private Lock guiLock = new ReentrantLock();


    @Override
    public synchronized void onAntDeath(Ant ant) {
        System.out.println("Ant deaths broadcasted");
        AntThread antThread = this.antThreadMap.get(ant);
        // this.guiLock.lock();
        // this.gui.removeUpdatableSprite(this.antGuiMap.get(ant));
        // this.guiLock.unlock();

        //kill thread
        antThread.stop();
        this.antThreadMap.remove(ant);
        // this.ants.remove(ant);
    }

    public WorldManager(WorldConfig config) {
        this.map = Map.generateRandomMap(config.vertexNumber,
                config.stoneProbability,
                config.leafProbability,
                config.width,
                config.height,
                config.vertexNeighborhoodSize);

        this.config = config;
        // this.ants = new ArrayBlockingQueue<Ant>(config.maxRedAnts +
        // config.maxBlueAnts);
        this.ants = new ArrayList<Ant>(config.maxRedAnts + config.maxBlueAnts);

        this.antThreads = new ArrayList<AntThread>(config.maxRedAnts + config.maxBlueAnts);

    }

    public void run() {
        this.gui = new Gui(this.config.width, this.config.height);

        for (int i = 0; i < this.config.initialRedAnts; i++) {
            Ant ant = new SoldierAnt("red" + i, 1, 1, map.getRedAnthill());
            this.ants.add(ant);
            this.gui.addUpdatableSprite(new AntGui(ant, "antRed", "antRedAttacking", "antRedDying", "antRedDead"));
        }

        for (int i = 0; i < this.config.initialBlueAnts; i++) {
            Ant ant = new WorkerAnt("blue" + i, 1, 1, map.getBlueAnthill());
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
            AntThread antThread = new AntThread(ant);
            ant.subscribeDeath(this);
            this.antThreads.add(antThread);
            this.antThreadMap.put(ant, antThread);
        }

        for (AntThread antThread : this.antThreads) {
            antThread.start();
        }

        while (true) {
            for (Vertex v : this.map.getVertices()) {
                v.tryExecuteCommand();
            }

            this.guiLock.lock();
            gui.update();
            this.guiLock.unlock();
        }

    }
}
