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
    public final int maxRedAnts;
    public final int maxBlueAnts;
    public final int initialRedAnts;
    public final int initialBlueAnts;

    public WorldConfig(int vertexNumber, double stoneProbability, double leafProbability, int width, int height, int maxRedAnts, int maxBlueAnts, int initialRedAnts, int initialBlueAnts, int vertexNeighborhoodSize, int maxLarvaePerVertex) {
        this.vertexNumber = vertexNumber;
        this.vertexNeighborhoodSize = vertexNeighborhoodSize;

        this.stoneProbability = stoneProbability;
        this.leafProbability = leafProbability;
        this.width = width;
        this.height = height;

        this.maxRedAnts = maxRedAnts;
        this.maxBlueAnts = maxBlueAnts;

        this.initialRedAnts = initialRedAnts;
        this.initialBlueAnts = initialBlueAnts;
        this.maxLarvaePerVertex = maxLarvaePerVertex;

        if (this.initialRedAnts > this.maxRedAnts) {
            throw new IllegalArgumentException("initialRedAnts must be less than or equal to maxRedAnts");
        }

        if (this.initialBlueAnts > this.maxBlueAnts) {
            throw new IllegalArgumentException("initialBlueAnts must be less than or equal to maxBlueAnts");
        }
    }


    public String toString() {
        return "vertexNumber: " + vertexNumber + "\n" +
                "stoneProbability: " + stoneProbability + "\n" +
                "leafProbability: " + leafProbability + "\n" +
                "width: " + width + "\n" +
                "height: " + height + "\n" +
                "maxRedAnts: " + maxRedAnts + "\n" +
                "maxBlueAnts: " + maxBlueAnts + "\n";
    }
}
