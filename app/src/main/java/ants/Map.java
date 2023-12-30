package ants;

import java.util.ArrayList;
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

    static Map generateRandomMap(int vertexNumber, double stoneProbability, double leafProbability, int width,
            int height) {

        Map map = new Map();

        //Generating vertices
        map.vertices = new ArrayList<>();
        for (int i = 0; i < vertexNumber; i++) {
            int x = (int) (Math.random() * width);
            int y = (int) (Math.random() * height);
            boolean isLeaf = Math.random() < leafProbability;
            boolean isStone = Math.random() < stoneProbability;
            map.vertices.add(new Vertex(x, y, isLeaf, isStone));
        }

        for (int i = 0; i < vertexNumber; i++) {
            Vertex v1 = map.vertices.get(i);
            for (Vertex v2 : map.getKnearestVertices(v1, 3)) {
                v1.addNeighbor(v2);
            }
        }

        //Generating anthills

        int redX = (int) (Math.random() * width);
        int redY = (int) (Math.random() * height);
        Anthill redAnthill = new Anthill(redX, redY, Color.RED);
        
        for (Vertex v2 : map.getKnearestVertices(redAnthill, 2)) {
            redAnthill.addNeighbor(v2);
        }

        int blueX = (int) (Math.random() * width);
        int blueY = (int) (Math.random() * height);

        Anthill blueAnthill = new Anthill(blueX, blueY, Color.BLUE);

        for (Vertex v2 : map.getKnearestVertices(blueAnthill, 2)) {
            blueAnthill.addNeighbor(v2);
        }

        map.redAnthill = redAnthill;
        map.blueAnthill = blueAnthill;

        return map;

    }

    private ArrayList<Vertex> getKnearestVertices(Vertex vertex, int k) {

        PriorityQueue<Vertex> priorityQueue = new PriorityQueue<>(
                Comparator.comparingDouble(v -> distance(vertex, v)));

        for (Vertex v : vertices) {
            priorityQueue.offer(v);
            if (priorityQueue.size() > k) {
                priorityQueue.poll(); 
            }
        }

        ArrayList<Vertex> result = new ArrayList<>(priorityQueue);
        return result;
    }

    private static double distance(Vertex v1, Vertex v2) {
        return Math.sqrt(Math.pow(v1.getX() - v2.getX(), 2) + Math.pow(v1.getY() - v2.getY(), 2));
    }

}
