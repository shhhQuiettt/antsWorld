package ants;

import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {

        int vertexNumber = 4;
        double stoneProbability = 0.1;
        double leafProbability = 0.1;
        int width = 500;
        int height = 500;
        int maxRedAnts = 10;
        int maxBlueAnts = 10;

        WorldConfig config = new WorldConfig(vertexNumber, stoneProbability, leafProbability, width, height, maxRedAnts,
                maxBlueAnts);
        System.out.println(config.toString());

        // SwingUtilities.invokeLater(new Runnable() {
        //     public void run() {
        //         WorldManager worldManager = new WorldManager(config);
        //         worldManager.run();
        //     }
        // });

        WorldManager worldManager = new WorldManager(config);
        System.out.println("running");
        try {
        Thread.sleep(2000);
        } catch (Exception e) {
            // TODO: handle exception
        }
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
