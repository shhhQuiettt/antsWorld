package ants;

import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {

        int vertexNumber = 20;
        double stoneProbability = 0.1;
        double leafProbability = 0.2;
        int maxLarvaePerVertex = 3;
        int width = 1000;
        int height = 900;
        int maxRedAnts = 20;
        int maxBlueAnts = 20 ;
        int initialRedAnts = 0;
        int initialBlueAnts = 0;
        int vertexNeighborhoodSize = 3;

        WorldConfig config = new WorldConfig(vertexNumber, stoneProbability, leafProbability, width, height, maxRedAnts,
                maxBlueAnts, initialRedAnts, initialBlueAnts, vertexNeighborhoodSize, maxLarvaePerVertex);

        // debug config
        int vertexNumber2 = 1;
        double stoneProbability2 = 0.0;
        double leafProbability2 = 0;
        int maxLarvaePerVertex2 = 2;
        int width2 = 500;
        int height2 = 500;
        int maxRedAnts2 = 3;
        int maxBlueAnts2 = 3;
        int initialRedAnts2 = 0;
        int initialBlueAnts2 = 0;
        int vertexNeighborhoodSize2 = 2;

        WorldConfig debugConfig = new WorldConfig(vertexNumber2, stoneProbability2, leafProbability2, width2, height2,
                maxRedAnts2, maxBlueAnts2, initialRedAnts2, initialBlueAnts2, vertexNeighborhoodSize2,
                maxLarvaePerVertex2);

        System.out.println(config.toString());


        WorldManager worldManager = new WorldManager(config);
        // WorldManager worldManager = new WorldManager(debugConfig);
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
        // System.out.println("Run");
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
