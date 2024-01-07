package ants;

import java.util.LinkedList;

/**
 * VertexGui
 */
public class VertexGui extends Sprite implements LarvaeSubscriber {
    Vertex vertex;
    LinkedList<Sprite> larvaes = new LinkedList<>();

    private int[] getRandomAroundVertexCoords(int centerX, int centerY) {
        int[] coords = new int[2];
        double angle = Math.random() * 2 * Math.PI;
        double radius = 8 + Math.random() * 16;

        coords[0] = centerX + (int) (radius * Math.cos(angle));
        coords[1] = centerY + (int) (radius * Math.sin(angle));

        return coords;
        // return new int[] { centerX, centerY };
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
                int[] coords = VertexGui.this.getRandomAroundVertexCoords(
                        (int) VertexGui.this.getRelativeCenter().getX(),
                        (int) VertexGui.this.getRelativeCenter().getY());
                // System.out.println("larva coords: " + coords[0] + " " + coords[1] + "");
                super.setPosition(coords[0], coords[1]);
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
