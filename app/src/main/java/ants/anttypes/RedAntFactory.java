package ants.anttypes;

import ants.map.Anthill;

/**
 * RedAntFactory
 * Allows to create red ants
 */
public class RedAntFactory {
    Anthill initialAnthill;
    Anthill enemyAnthill;

    public RedAntFactory(Anthill initialAnthill, Anthill enemyAnthill) {
        this.initialAnthill = initialAnthill;
        this.enemyAnthill = enemyAnthill;
    }

    public SoldierAnt newSoldierAnt(String name, int health, int strength) {
        return new SoldierAnt(name, health, strength, this.initialAnthill, this.enemyAnthill);
    }
    
    public CollectorAnt newCollectorAnt(String name, int health, int strength) {
        return new CollectorAnt(name, health, strength, this.initialAnthill, this.enemyAnthill);
    }

    public BlundererAnt newBlundererAnt(String name, int health, int strength, double probabilityOfDroppingLarva) {
        return new BlundererAnt(name, health, strength, probabilityOfDroppingLarva, this.initialAnthill, this.enemyAnthill);
    }


    public Ant newRandomAnt(String name, int health, int strength) {
        int random = (int) (Math.random() * 3);

        if (random == 0) {
            return new SoldierAnt(name, health, strength, initialAnthill, enemyAnthill);
        } else {
            return new WorkerAnt(name, health, strength, initialAnthill, enemyAnthill);
        }
    }

}
