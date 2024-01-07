package ants.gui;

import ants.*;
/**
 * AnthillGui
 */
public class AnthillGui extends VertexGui{
    private Anthill anthill;

    private static String getFileName(Anthill anthill) {
        if (anthill.getColor() == AntColor.RED) {
            return "anthillRed";
        } else {
            return "anthillBlue";
        }
    }
    public AnthillGui(Anthill anthill) {
        super(anthill, getFileName(anthill));
        this.anthill = anthill;
    }

    public void setPosition() {
        super.setPosition(this.anthill.getX(), this.anthill.getY());
    }
    
}
