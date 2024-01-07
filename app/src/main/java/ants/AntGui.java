package ants;

import java.awt.image.BufferedImage;
import javax.swing.SwingUtilities;
import java.util.ArrayList;

/**
 * AntGui
 */
// public class AntGui extends AnimatedSprite implements GuiUpdatable{
public class AntGui extends UpdatableSprite implements AntStateSubscriber {
    final Ant ant;

    private BufferedImage movingImage;
    private BufferedImage attackingImage;
    private BufferedImage dyingImage;
    private BufferedImage deadImage;

    // public ArrayList<BufferedImage> getAllImages() {
    // ArrayList<BufferedImage> images = new ArrayList<>();
    // if (this.movingImage != null) {
    // images.add(this.movingImage);
    // }
    // if (this.attackingImage != null) {
    // images.add(this.attackingImage);
    // }
    // if (this.dyingImage != null) {
    // images.add(this.dyingImage);
    // }
    // if (this.deadImage != null) {
    // images.add(this.deadImage);
    // }
    // return images;
    // }

    // private ArrayList<ImageOnEventUpdater> imageUpdaters = new ArrayList<>();

    // private static String getFileName(Ant ant) {
    // if (ant.getColor() == Color.RED) {
    // return "antRed";
    // } else {
    // return "antBlue";
    // }
    // }

    @Override
    public synchronized void onAntStateChange(AntState state) {
        if (state == AntState.MOVING) {
            this.setDisplayedImage(this.movingImage);
            // this.setDisplayedImage(this.attackingImage);
        } else if (state == AntState.ATTACKING) {
            this.setDisplayedImage(this.attackingImage);
        } else if (state == AntState.DYING) {
             this.setDisplayedImage(this.dyingImage);
            
        } else if (state == AntState.HIDING) {
            this.updateOpacity(0.5f);
        } else if (state == AntState.DEAD) {
            this.setDisplayedImage(this.deadImage);
        } else {
            this.setDisplayedImage(this.movingImage);
        }
    }

    // public void addImageUpdater(ImageOnEventUpdater updater) {
    // updater.register(this);
    // // this.imageUpdaters.add(updater);
    // }

    public AntGui(Ant ant, AntImageConfig imageConfig) {
        super(imageConfig.getMovingImageName());

        this.ant = ant;

        this.ant.subscribeStateChange(this);

        if (imageConfig.getMovingImageName() != null) {
            this.movingImage = this.loadImage(imageConfig.getMovingImageName());
        }
        if (imageConfig.getAttackingImageName() != null) {
            this.attackingImage = this.loadImage(imageConfig.getAttackingImageName());
        }
        if (imageConfig.getDyingImageName() != null) {
            this.dyingImage = this.loadImage(imageConfig.getDyingImageName());
        }
        if (imageConfig.getDeadImageName() != null) {
            this.deadImage = this.loadImage(imageConfig.getDeadImageName());
        }

    }

    @Override
    public void setPosition() {
        this.setPosition((int) this.ant.getX(), (int) this.ant.getY());
    }

    public void update(int timeElapsed) {
        this.setPosition();
    }

}
