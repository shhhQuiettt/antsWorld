package ants.gui;

/**
 * AntImageConfig
 *
 * Stores the image names for the ant various states
 */
public class AntImageConfig {
    private String movingImageName;
    private String attackingImageName;
    private String dyingImageName;
    private String deadImageName;

    public AntImageConfig(String movingImageName, String attackingImageName, String dyingImageName,
            String deadImageName) {
        this.movingImageName = movingImageName;
        this.attackingImageName = attackingImageName;
        this.dyingImageName = dyingImageName;
        this.deadImageName = deadImageName;
    }

    public String getMovingImageName() {
        return movingImageName;
    }

    public String getAttackingImageName() {
        return attackingImageName;
    }

    public String getDyingImageName() {
        return dyingImageName;
    }

    public String getDeadImageName() {
        return deadImageName;
    }
}
