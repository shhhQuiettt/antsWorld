package ants.map;

import java.util.ArrayList;

/**
 * Map
 */
public class Map {
    /**
     * Method to generate a random map with given parameters
     *
     * @param vertexNumber
     * @param stoneProbability
     * @param leafProbability
     * @param width
     * @param height
     * @param vertexNeighborhoodSize
     * @param maxLarvaePerVertex
     * @return
     */
    public static Map generateRandomMap(int vertexNumber, double stoneProbability, double leafProbability, int width,
            int height, int vertexNeighborhoodSize, int maxLarvaePerVertex) {

        MapBuilder mapBuilder = new MapBuilder(width, height, vertexNumber);

        mapBuilder.generateAnthills();
        mapBuilder.generateVertices(stoneProbability, leafProbability, maxLarvaePerVertex);

        mapBuilder.generateNeighbors(vertexNeighborhoodSize);

        return mapBuilder.collect();
    }

    private ArrayList<Vertex> vertices = new ArrayList<>();
    private Anthill redAnthill;
    private Anthill blueAnthill;
    private int width;
    private int height;

    private int vertexNumber;

    public Map(int width, int height, int vertexNumber) {
        this.width = width;
        this.height = height;
        this.vertexNumber = vertexNumber;
    }

    public ArrayList<Vertex> getVertices() {
        return vertices;
    }

    public void addVertex(Vertex vertex) {
        vertices.add(vertex);
    }

    public Anthill getRedAnthill() {
        return redAnthill;
    }

    public Anthill getBlueAnthill() {
        return blueAnthill;
    }

    public void setRedAnthill(Anthill redAnthill) {
        this.redAnthill = redAnthill;
    }

    public void setBlueAnthill(Anthill blueAnthill) {
        this.blueAnthill = blueAnthill;
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

}
