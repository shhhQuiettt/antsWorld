package ants;

/**
 * WorldConfig
 *
 */
public class WorldConfig {
    private final int vertexNumber;

    private final int vertexNeighborhoodSize;

    private final double stoneProbability;

    private final double leafProbability;

    private final int maxLarvaePerVertex;

    private final int worldWidth;

    private final int worldHeight;

    /**
     * @param vertexNumber Number of vertices
     * @param stoneProbability Probability of a vertex to be a stone
     * @param leafProbability Probability of a vertex to be a leaf
     * @param worldWidth Width of the world
     * @param worldHeight Height of the world
     * @param vertexNeighborhoodSize Number of neighbors of a vertex
     * @param maxLarvaePerVertex Maximum number of larvae per vertex on initialization
     */
    public WorldConfig(int vertexNumber, double stoneProbability, double leafProbability, int worldWidth, int worldHeight,
            int vertexNeighborhoodSize, int maxLarvaePerVertex) {

        this.vertexNumber = vertexNumber;
        this.vertexNeighborhoodSize = vertexNeighborhoodSize;
        this.stoneProbability = stoneProbability;
        this.leafProbability = leafProbability;
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.maxLarvaePerVertex = maxLarvaePerVertex;

    }

    public int getVertexNumber() {
        return vertexNumber;
    }
    public int getVertexNeighborhoodSize() {
        return vertexNeighborhoodSize;
    }
    public double getStoneProbability() {
        return stoneProbability;
    }
    public double getLeafProbability() {
        return leafProbability;
    }
    public int getMaxLarvaePerVertex() {
        return maxLarvaePerVertex;
    }
    public int getWorldWidth() {
        return worldWidth;
    }

    public int getWorldHeight() {
        return worldHeight;
    }

    public String toString() {
        return "vertexNumber: " + vertexNumber + "\n" +
                "stoneProbability: " + stoneProbability + "\n" +
                "leafProbability: " + leafProbability + "\n" +
                "width: " + worldWidth + "\n" +
                "height: " + worldHeight + "\n";
    }
}
