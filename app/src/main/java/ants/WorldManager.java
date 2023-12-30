package ants;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;
/**
 * WorldManager
 */


public class WorldManager {
    private final Map map;
    private BlockingQueue<Ant> redAnts;
    private BlockingQueue<Ant> blueAnts;

    public WorldManager(WorldConfig config) {
        this.map = Map.generateRandomMap(config.vertexNumber,
                config.stoneProbability,
                config.leafProbability,
                config.width,
                config.height);

        this.redAnts = new ArrayBlockingQueue<Ant>(config.maxRedAnts);
        this.blueAnts = new ArrayBlockingQueue<Ant>(config.maxBlueAnts);

    }

}
