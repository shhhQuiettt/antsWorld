package ants.gui;

import java.awt.image.BufferedImage;

import ants.anttypes.Ant;
import ants.anttypes.AntState;
import ants.interfaces.AntStateSubscriber;

/**
 * AntGui
 *
 * Class responsible for managing Ant's display
 */
public class AntGui extends UpdatableSprite implements AntStateSubscriber {
    final Ant ant;

    private BufferedImage movingImage;
    private BufferedImage attackingImage;
    private BufferedImage dyingImage;
    private BufferedImage deadImage;

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
    public synchronized void onAntStateChange(AntState state) {
        if (state == AntState.MOVING) {
            this.setDisplayedImage(this.movingImage);
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

    @Override
    public void setPosition() {
        this.setPosition((int) this.ant.getX(), (int) this.ant.getY());
    }

    public Ant getAnt() {
        return this.ant;
    }

    public void update() {
        this.setPosition();
    }

}
