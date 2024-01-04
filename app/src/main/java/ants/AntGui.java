package ants;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import javax.swing.SwingUtilities;

/**
 * AntGui
 */
// public class AntGui extends AnimatedSprite implements GuiUpdatable{
public class AntGui extends UpdatableSprite implements AntStateSubscriber {
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

    @Override
    public void onAntStateChange(AntState state) {
        System.out.println("state changed to " + state + " for " + this.ant.getName() + " ant broadcasted");
        if (state == AntState.MOVING) {
            this.setDisplayedImage(this.movingImage);
        }
        else if (state == AntState.ATTACKING) {
            this.setDisplayedImage(this.attackingImage);
        }
        else if (state == AntState.DYING) {
            this.setDisplayedImage(this.dyingImage);
        }
        else if (state == AntState.DEAD) {
            this.setDisplayedImage(this.deadImage);
        }
    }

    public AntGui(Ant ant, String movingFileName, String attackingFileName, String dyingFileName, String deadFileName) {
        super(movingFileName);

        this.ant = ant;

        this.ant.subscribeStateChange(this);

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
        // SwingUtilities.invokeLater(new Runnable() {
        // public void run() {
        // setPosition();
        // }
        // });
        //
        this.setPosition();
    }
}
