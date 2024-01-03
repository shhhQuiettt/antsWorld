package ants;

/**
 * AnthillGui
 */
public class AnthillGui extends Sprite{
    private Anthill anthill;

    private static String getFileName(Anthill anthill) {
        if (anthill.getColor() == Color.RED) {
            return "anthillRed";
        } else {
            return "anthillBlue";
        }
    }
    AnthillGui(Anthill anthill) {
        super(getFileName(anthill));
        this.anthill = anthill;
    }

    public void setPosition() {
        super.setPosition(this.anthill.getX(), this.anthill.getY());
    }
    
}
