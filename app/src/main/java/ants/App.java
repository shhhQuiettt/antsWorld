package ants;

public class App {
    public static int getGreeting() {
        int a = 3;
        return a * 10;
    }

    public static void main(String[] args) {

        int vertexNumber = 8;
        double stoneProbability = 0.1;
        double leafProbability = 0.1;
        int width = 100;
        int height = 100;
        int maxRedAnts = 10;
        int maxBlueAnts = 10;


        WorldConfig config = new WorldConfig(vertexNumber, stoneProbability, leafProbability, width, height, maxRedAnts, maxBlueAnts);
        System.out.println(config.toString());
        WorldManager worldManager = new WorldManager(config);

        // Map map = Map.generateRandomMap(4, 0.1, 0.1, 100, 100);
        // for (Vertex v : map.getVertices()) {
        //     System.out.println(v.getNeighbors());
        // }
    }
}
