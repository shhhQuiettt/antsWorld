package ants;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import javax.swing.SwingUtilities;

/**
 * AntGui
 */
// public class AntGui extends AnimatedSprite implements GuiUpdatable{
public class AntGui extends UpdatableSprite {
    private final Ant ant;

    private BufferedImage movingImage;
    private BufferedImage attackingImage;
    private BufferedImage dyingImage;
    private BufferedImage deadImage;

    // private static String getFileName(Ant ant) {
    // if (ant.getColor() == Color.RED) {
    // return "antRed";
    // } else {
    // return "antBlue";
    // }
    // }

    public AntGui(Ant ant, String movingFileName, String attackingFileName, String dyingFileName, String deadFileName) {
        super(movingFileName);

        this.ant = ant;

        if (movingFileName != null) {
            this.movingImage = this.loadImage(movingFileName);
        }
        if (attackingFileName != null) {
            this.attackingImage = this.loadImage(attackingFileName);
        }
        if (dyingFileName != null) {
            this.dyingImage = this.loadImage(dyingFileName);
        }
        if (deadFileName != null) {
            this.deadImage = this.loadImage(deadFileName);
        }

    }

    @Override
    public void setPosition() {
        this.setPosition((int) this.ant.getX(), (int) this.ant.getY());
    }

    public void update(int timeElapsed) {
        System.out.println("AntGui update, state: " + this.ant.getState() + "\n");
        System.out.flush();
        // SwingUtilities.invokeLater(new Runnable() {
        // public void run() {
        // setPosition();
        // }
        // });
        //
        // CHANGE TO EVENTLISTENER
        this.setPosition();
        if (this.ant.getState() == AntState.MOVING && this.displayedImage != this.movingImage ){
            this.setDisplayedImage(this.movingImage);
        }
        else if (this.ant.getState() == AntState.ATTACKING && this.displayedImage != this.attackingImage){
            this.setDisplayedImage(this.attackingImage);
        }
        else if (this.ant.getState() == AntState.DYING && this.displayedImage != this.dyingImage){
            this.setDisplayedImage(this.dyingImage);
        }
        else if (this.ant.getState() == AntState.DEAD && this.displayedImage != this.deadImage){
            this.setDisplayedImage(this.deadImage);
        }
    }
}
