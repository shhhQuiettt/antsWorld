package ants.gui;

import java.awt.Point;
import java.util.LinkedList;

import ants.interfaces.LarvaeSubscriber;
import ants.map.Vertex;

/**
 * VertexGui
 *
 * This class is responsible for displaying a vertex on the screen.
 */
public class VertexGui extends Sprite implements LarvaeSubscriber {
    Vertex vertex;
    LinkedList<Sprite> larvaes = new LinkedList<>();

    public Vertex getVertex() {
        return this.vertex;
    }

    private Point getRandomAroundVertexCoords(int centerX, int centerY) {
        double angle = Math.random() * 2 * Math.PI;
        double radius = 8 + Math.random() * 16;

        int x = centerX + (int) (radius * Math.cos(angle));
        int y = centerY + (int) (radius * Math.sin(angle));

        return new Point(x, y);
    }

    public static String getFileName(Vertex vertex) {
        if (vertex.isLeaf()) {
            return "vertexWithLeaf";
        } else if (vertex.isStone()) {
            return "vertexWithStone";
        } else {
            return "vertex";
        }
    }

    public VertexGui(Vertex vertex, String fileName) {
        super(fileName);
        this.vertex = vertex;
        for (int i = 0; i < vertex.getNumberOfLarvae().get(); i++) {
            this.addLarve();
        }

        vertex.subscribeLarvae(this);
    }

    public VertexGui(Vertex vertex) {
        super(getFileName(vertex));
        this.vertex = vertex;

        for (int i = 0; i < vertex.getNumberOfLarvae().get(); i++) {
            this.addLarve();
        }

        vertex.subscribeLarvae(this);
    }

    private void addLarve() {
        Sprite larva = new Sprite("larva") {
            @Override
            public void setPosition() {
                Point coords = VertexGui.this.getRandomAroundVertexCoords(
                        (int) VertexGui.this.getRelativeCenter().getX(),
                        (int) VertexGui.this.getRelativeCenter().getY());
                super.setPosition(coords.x, coords.y);
            }
        };
        larva.setPosition();
        larvaes.add(larva);
        this.add(larva);
    }

    @Override
    public synchronized void onLarvaeRemoved() {
        Sprite larva = this.larvaes.pop();
        this.remove(larva);
    }

    @Override
    public synchronized void onLarvaeAdded() {
        this.addLarve();
    }

    @Override
    public void setPosition() {
        super.setPosition(this.vertex.getX(), this.vertex.getY());
    }

}
