package ants;

/**
 * WorldConfig
 */
public class WorldConfig {
    public final int vertexNumber;
    public final double stoneProbability;
    public final double leafProbability;
    public final int width;
    public final int height;
    public final int maxRedAnts;
    public final int maxBlueAnts;

    public WorldConfig(int vertexNumber, double stoneProbability, double leafProbability, int width, int height, int maxRedAnts, int maxBlueAnts) {
        this.vertexNumber = vertexNumber;
        this.stoneProbability = stoneProbability;
        this.leafProbability = leafProbability;
        this.width = width;
        this.height = height;
        this.maxRedAnts = maxRedAnts;
        this.maxBlueAnts = maxBlueAnts;
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
