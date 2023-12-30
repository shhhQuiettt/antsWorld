package ants;

/**
 * VertexGui
 */
public class VertexGui extends Sprite {
    Vertex vertex;

    public VertexGui(Vertex vertex) {
        // if isLeaf etc
        super("vertex");
        this.vertex = vertex;
    }

    public void setPosition() {
        super.setPosition(this.vertex.getX(), this.vertex.getY());
    }
}
