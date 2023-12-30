package ants;

import java.awt.Dimension;

/**
 * AntGui
 */
// public class AntGui extends AnimatedSprite implements GuiUpdatable{
public class AntGui extends UpdatableSprite {
    private final Ant ant;

    private static String getFileName(Ant ant) {
        if (ant.getColor() == Color.RED) {
            return "antRed.png";
        } else {
            return "antBlue.png";
        }
    }


    public AntGui(Ant ant) {
        //// 32x32 z dupy!!
        // super(getFileName(ant), new Dimension(32, 32), 2, 5);
        super(getFileName(ant));
        this.ant = ant;
    }

    @Override
    public void setPosition() {
        this.setPosition(this.ant.getX(), this.ant.getY());
    }

    public void update(int timeElapsed) {
        this.setPosition();
    }
}
