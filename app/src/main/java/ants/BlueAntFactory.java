package ants;

/**
 * BlueAntFactory
 */
public class BlueAntFactory {
    Anthill initialAnthill;
    Anthill enemyAnthill;

    public BlueAntFactory(Anthill initialAnthill, Anthill enemyAnthill) {
        this.initialAnthill = initialAnthill;
        this.enemyAnthill = enemyAnthill;
    }

    public WorkerAnt newWorkerAnt(String name, int health, int strength) {
        return new WorkerAnt(name, health, strength, this.initialAnthill, this.enemyAnthill);
    }

    public CannonFodderAnt newCannonFodderAnt(String name, int health, int strength) {
        return new CannonFodderAnt(name, health, strength, this.initialAnthill, this.enemyAnthill);
    }

    public KamikazeAnt newKamikazeAnt(String name, int health, int strength) {
        return new KamikazeAnt(name, health, strength, this.initialAnthill, this.enemyAnthill);
    }
}
