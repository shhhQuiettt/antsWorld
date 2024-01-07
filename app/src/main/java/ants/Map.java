package ants;

import java.util.ArrayList;
import java.util.Timer;
import java.util.PriorityQueue;
import java.util.Comparator;

/**
 * Map
 */
public class Map {
    private ArrayList<Vertex> vertices;

    private Anthill redAnthill;
    private Anthill blueAnthill;
    private int width;
    private int height;
    private int vertexNumber;

    public ArrayList<Vertex> getVertices() {
        return vertices;
    }

    public Anthill getRedAnthill() {
        return redAnthill;
    }

    public Anthill getBlueAnthill() {
        return blueAnthill;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getVertexNumber() {
        return vertexNumber;
    }

    private static int[] generateRandomNonOverlappingPosition(Map map) {
        ArrayList<Vertex> obstacles = new ArrayList<>();
        obstacles.addAll(map.getVertices());

        if (map.getRedAnthill() != null) {
            obstacles.add(map.getRedAnthill());
        }

        if (map.getBlueAnthill() != null) {
            obstacles.add(map.getBlueAnthill());
        }

        int x, y;

        // After some timeOut we throw an exception

        Timer timer = new Timer();
        long startTime = System.currentTimeMillis();
        long timeOut = 1000;
        while (System.currentTimeMillis() - startTime < timeOut) {
            boolean validCoords = true;
            x = (int) (Math.random() * (map.width - 96)) + 96 / 2;
            y = (int) (Math.random() * (map.height - 96)) + 96 / 2;
            for (Vertex v : obstacles) {
                if (Math.abs(v.getX() - x) < 96 && Math.abs(v.getY() - y) < 96) {
                    validCoords = false;
                    break;
                }
            }
            if (validCoords) {
                return new int[] { x, y };
            }
        }

        throw new RuntimeException("Could not generate random non-overlapping position");
    }

    public static Map generateRandomMap(int vertexNumber, double stoneProbability, double leafProbability, int width,
            int height, int vertexNeighborhoodSize, int maxLarvaePerVertex) {

        Map map = new Map();
        map.width = width;
        map.height = height;
        map.vertices = new ArrayList<>();

        // Generating vertices
        int[] redPosition = new int[2];
        redPosition = generateRandomNonOverlappingPosition(map);
        // int[] redPosition = {100, 100};
        int redX = redPosition[0];
        int redY = redPosition[1];
        Anthill redAnthill = new Anthill(redX, redY, AntColor.RED);
        map.redAnthill = redAnthill;

        int[] bluePosition = new int[2];
        bluePosition = generateRandomNonOverlappingPosition(map);
        // int[] bluePosition = {width - 100, height - 100};
        int blueX = bluePosition[0];
        int blueY = bluePosition[1];
        Anthill blueAnthill = new Anthill(blueX, blueY, AntColor.BLUE);
        map.blueAnthill = blueAnthill;

        for (int i = 0; i < vertexNumber; i++) {
            Vertex v = generateRandomVertex(map, stoneProbability, leafProbability, maxLarvaePerVertex);
            map.vertices.add(v);
        }

        for (int i = 0; i < vertexNumber; i++) {
            Vertex v1 = map.vertices.get(i);
            // for (Vertex v2 : map.getKnearestVertices(v1, 3)) {
            for (Vertex v2 : map.getKnearestVertices(v1, vertexNeighborhoodSize)) {
                v1.addNeighbor(v2);
            }
        }

        // neigbors of anthills
        for (Vertex v : map.getKnearestVertices(redAnthill, vertexNeighborhoodSize)) {
            redAnthill.addNeighbor(v);
        }

        for (Vertex v : map.getKnearestVertices(blueAnthill, vertexNeighborhoodSize)) {
            blueAnthill.addNeighbor(v);
        }

        // Generating anthills

        return map;

    }

    private static Vertex generateRandomVertex(Map map, double stoneProbability, double leafProbability,
            int maxLarvaePerVertex) {
        int[] position = new int[2];
        position = generateRandomNonOverlappingPosition(map);

        int x = position[0];
        int y = position[1];

        boolean isStone = Math.random() < stoneProbability;
        boolean isLeaf = !isStone && Math.random() < leafProbability;

        Vertex vertex = new Vertex(x, y, isLeaf, isStone);

        for (int i = 1; i <= maxLarvaePerVertex; i++) {
            if (Math.random() < 1.0 / ((double) i * (double) maxLarvaePerVertex)) {
                vertex.putLarva();
            }
        }

        return vertex;

    }

    private ArrayList<Vertex> getKnearestVertices(Vertex vertex, int k) {
        if (k >= this.vertices.size() + 2) {
            throw new IllegalArgumentException("k must be smaller than the number of vertices");
        }

        PriorityQueue<Vertex> priorityQueue = new PriorityQueue<>(
                Comparator.comparingDouble(v -> distance(vertex, v)));

        for (Vertex v : this.vertices) {
            if (v != vertex) {
                priorityQueue.add(v);
            }
        }

        if (this.redAnthill != vertex && this.redAnthill != null) {
            priorityQueue.add(this.redAnthill);
        }
        if (this.blueAnthill != vertex && this.blueAnthill != null) {
            priorityQueue.add(this.blueAnthill);
        }

        // return top k
        ArrayList<Vertex> neighbors = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            neighbors.add(priorityQueue.poll());
        }

        return neighbors;
    }

    private static double distance(Vertex v1, Vertex v2) {
        return Math.sqrt(Math.pow(v1.getX() - v2.getX(), 2) + Math.pow(v1.getY() - v2.getY(), 2));
    }

}
