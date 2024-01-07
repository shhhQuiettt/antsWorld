package ants;

import ants.gui.AntGui;

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
