package ants;

/**
 * CannonFodderAnt
 */
public class CannonFodderAnt extends Ant {

    CannonFodderAnt(String name, int health, int strength, Anthill initialAnthill, Anthill enemyAnthill) {
        super(name, AntColor.BLUE, health, strength, initialAnthill, enemyAnthill);
    }

    @Override
    protected void doActionsInVertex() {
        try {
            Thread.sleep(getRandomWaitingTime());
        } catch (InterruptedException e) {
            System.err.println("Interrupted while waiting in CannonFodderAnt");
        }
    }

}
