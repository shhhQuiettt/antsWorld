package ants;

import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
        int vertexNumber = 20;
        double stoneProbability = 0.1;
        double leafProbability = 0.2;
        int maxLarvaePerVertex = 5;
        int worldWidth = 1000;
        int worldHeight = 800;
        int vertexNeighborhoodSize = 3;

        WorldConfig config = new WorldConfig(vertexNumber, stoneProbability, leafProbability, worldWidth, worldHeight,
                vertexNeighborhoodSize, maxLarvaePerVertex);

        WorldManager worldManager = new WorldManager(config);

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                worldManager.run();
            }
        });
    }

}
