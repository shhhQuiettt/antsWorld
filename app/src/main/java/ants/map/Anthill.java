package ants.map;

import ants.anttypes.AntColor;

/**
 * Anthill
 */
public class Anthill extends Vertex {
    AntColor color;

    Anthill(int x, int y, AntColor color) {
        super(x, y, false, false);
        this.color = color;
    }

    public AntColor getColor() {
        return this.color;
    }
}
