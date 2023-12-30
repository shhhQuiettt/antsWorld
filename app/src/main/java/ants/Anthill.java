package ants;

enum Color {
    RED, BLUE
}

/**
 * Anthill
 */
public class Anthill extends Vertex {
    Color color;

    Anthill(int x, int y, Color color) {
        super(x, y, false, false);
        this.color = color;
    }
}
