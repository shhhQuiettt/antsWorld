package ants;

public class App {
    public static int getGreeting() {
        int a = 3;
        return a * 10;
    }

    public static void main(String[] args) {
        Map map = Map.generateRandomMap(4, 0.1, 0.1, 100, 100);
        for (Vertex v : map.getVertices()) {
            System.out.println(v.getNeighbors());
        }
        // System.out.println("dupa");
    }
}
