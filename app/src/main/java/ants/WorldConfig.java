package ants;

/**
 * WorldConfig
 */
public class WorldConfig {
    public final int vertexNumber;
    public final int vertexNeighborhoodSize;
    public final double stoneProbability;
    public final double leafProbability;
    public final int maxLarvaePerVertex;
    public final int width;
    public final int height;

    public WorldConfig(int vertexNumber, double stoneProbability, double leafProbability, int width, int height,
            int vertexNeighborhoodSize, int maxLarvaePerVertex) {

        this.vertexNumber = vertexNumber;
        this.vertexNeighborhoodSize = vertexNeighborhoodSize;
        this.stoneProbability = stoneProbability;
        this.leafProbability = leafProbability;
        this.width = width;
        this.height = height;

        this.maxLarvaePerVertex = maxLarvaePerVertex;

    }

    public String toString() {
        return "vertexNumber: " + vertexNumber + "\n" +
                "stoneProbability: " + stoneProbability + "\n" +
                "leafProbability: " + leafProbability + "\n" +
                "width: " + width + "\n" +
                "height: " + height + "\n";
    }
}
