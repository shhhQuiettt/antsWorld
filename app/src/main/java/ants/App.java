package ants;

import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {

        int vertexNumber = 1;
        double stoneProbability = 0.1;
        double leafProbability = 0.1;
        int width = 500;
        int height = 500;
        int maxRedAnts = 10;
        int maxBlueAnts = 10;
        int initialRedAnts = 1;
        int initialBlueAnts = 1;
        int vertexNeighborhoodSize = 2;

        WorldConfig config = new WorldConfig(vertexNumber, stoneProbability, leafProbability, width, height, maxRedAnts,
                maxBlueAnts, initialRedAnts, initialBlueAnts, vertexNeighborhoodSize);
        System.out.println(config.toString());

        WorldManager worldManager = new WorldManager(config);
        // System.out.println("running");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.err.println("While waiting for initialization, Thread was interrupted");
        }
        // SwingUtilities.invokeLater(new Runnable() {
        // public void run() {
        // worldManager.run();
        // }
        // });
        System.out.println("Run");
        worldManager.run();
        // // String cwd = System.getProperty("user.dir");

        // // System.out.println("Current Working Directory: " + cwd);
        // worldManager.run();
        // Map map = Map.generateRandomMap(4, 0.1, 0.1, 100, 100);
        // for (Vertex v : map.getVertices()) {
        // System.out.println(v.getNeighbors());
        // }
    }

}
