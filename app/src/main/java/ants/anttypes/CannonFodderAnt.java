package ants.anttypes;

import ants.map.Anthill;

/**
 * CannonFodderAnt
 * Ant that is used to distract the enemy and die
 */
public class CannonFodderAnt extends Ant {

    CannonFodderAnt(String name, int health, int strength, Anthill initialAnthill, Anthill enemyAnthill) {
        super(name, AntColor.BLUE, health, strength, initialAnthill, enemyAnthill);
    }

    @Override
    protected void doActionsInVertex() {
        this.doScanning();
    }

}
