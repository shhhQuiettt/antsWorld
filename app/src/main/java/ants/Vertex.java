package ants;

import java.util.ArrayList;

/**
 * Vertex
 */
public class Vertex {
    private final int x;
    private final int y;

    private ArrayList<Double> redAnts = new ArrayList<>();
    private ArrayList<Double> blueAnts = new ArrayList<>();

    private boolean isLeaf;
    private boolean isStone;

    private ArrayList<Vertex> neighbors;

    public Vertex(int x, int y, boolean isLeaf, boolean isStone) {
        this.x = x;
        this.y = y;
        this.isLeaf = isLeaf;
        this.isStone = isStone;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void addNeighbor(Vertex neighbor) {
        neighbors.add(neighbor);
    }

    public ArrayList<Vertex> getNeighbors() {
        return neighbors;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public boolean isStone() {
        return isStone;
    }

    public void addRedAnt(double ant) {
        redAnts.add(ant);
    }

    public void addBlueAnt(double ant) {
        blueAnts.add(ant);
    }
}
