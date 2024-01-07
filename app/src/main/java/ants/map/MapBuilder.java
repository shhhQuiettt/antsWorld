package ants.map;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

import ants.anttypes.AntColor;

/**
 * MapBuilder
 */
class MapBuilder {
    private Map map;

    public MapBuilder(int width, int height, int vertexNumber) {
        this.map = new Map(width, height, vertexNumber);
    }

    public Map collect() {
        return this.map;
    }

    public void generateVertices(double stoneProbability, double leafProbability, int maxLarvaePerVertex) {
        for (int i = 0; i < this.map.getVertexNumber(); i++) {
            map.addVertex(generateRandomVertex(stoneProbability, leafProbability, maxLarvaePerVertex));
        }
    }

    private Vertex generateRandomVertex(double stoneProbability, double leafProbability,
            int maxLarvaePerVertex) {
        Point position = generateRandomNonOverlappingPosition();

        boolean isStone = Math.random() < stoneProbability;
        boolean isLeaf = !isStone && Math.random() < leafProbability;

        Vertex vertex = new Vertex(position.x, position.y, isLeaf, isStone);

        for (int i = 1; i <= maxLarvaePerVertex; i++) {

            if (Math.random() < 0.36) {
                vertex.putLarva();
            }
        }

        return vertex;
    }

    public void generateAnthills() {
        Point redAnthillPosition = generateRandomNonOverlappingPosition();
        Point blueAnthillPosition = generateRandomNonOverlappingPosition();

        this.map.setRedAnthill(new Anthill(redAnthillPosition.x, redAnthillPosition.y, AntColor.RED));

        this.map.setBlueAnthill(new Anthill(blueAnthillPosition.x, blueAnthillPosition.y, AntColor.BLUE));
    }

    public void generateNeighbors(int vertexNeighborhoodSize) {

        ArrayList<Vertex> vertices = new ArrayList<>();
        vertices.addAll(this.map.getVertices());
        vertices.add(this.map.getRedAnthill());
        vertices.add(this.map.getBlueAnthill());

        for (Vertex v : vertices) {
            ArrayList<Vertex> neighbors = getKnearestVertices(v, vertexNeighborhoodSize);
            for (Vertex neighbor : neighbors) {
                if (v.getNeighbors().size() < vertexNeighborhoodSize)
                    v.addNeighbor(neighbor);
            }
        }

    }

    private Point generateRandomNonOverlappingPosition() {
        ArrayList<Vertex> obstacles = new ArrayList<>();
        obstacles.addAll(this.map.getVertices());

        if (this.map.getRedAnthill() != null) {
            obstacles.add(this.map.getRedAnthill());
        }

        if (this.map.getBlueAnthill() != null) {
            obstacles.add(this.map.getBlueAnthill());
        }

        int x, y;

        long startTime = System.currentTimeMillis();
        long timeOut = 1000;
        while (System.currentTimeMillis() - startTime < timeOut) {
            boolean validCoords = true;
            x = (int) (Math.random() * (this.map.getWidth() - 96)) + 96 / 2;
            y = (int) (Math.random() * (this.map.getHeight() - 96)) + 96 / 2;
            for (Vertex v : obstacles) {
                if (Math.abs(v.getX() - x) < 96 && Math.abs(v.getY() - y) < 96) {
                    validCoords = false;
                    break;
                }
            }
            if (validCoords) {
                return new Point(x, y);
            }
        }

        throw new RuntimeException("Could not generate random non-overlapping position");
    }

    private ArrayList<Vertex> getKnearestVertices(Vertex vertex, int k) {
        if (k >= this.map.getVertices().size() + 2) {
            throw new IllegalArgumentException("k must be smaller than the number of vertices");
        }

        PriorityQueue<Vertex> priorityQueue = new PriorityQueue<>(
                Comparator.comparingDouble(v -> distance(vertex, v)));

        for (Vertex v : map.getVertices()) {
            if (v != vertex) {
                priorityQueue.add(v);
            }
        }

        if (map.getRedAnthill() != null && map.getRedAnthill() != vertex) {
            priorityQueue.add(map.getRedAnthill());
        }
        if (map.getBlueAnthill() != null && map.getBlueAnthill() != vertex) {
            priorityQueue.add(map.getBlueAnthill());
        }

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
