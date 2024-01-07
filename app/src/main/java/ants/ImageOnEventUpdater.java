package ants;

import java.awt.image.BufferedImage;

/**
 * ImageOnEventUpdater
 */
public abstract class ImageOnEventUpdater {
    AntGui antGui;

    ImageOnEventUpdater() {
    }

    public void register(AntGui antGui) {
        this.antGui = antGui;
    }

}
