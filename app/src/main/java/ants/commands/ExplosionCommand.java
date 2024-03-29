package ants.commands;

import java.util.ArrayList;

import ants.anttypes.Ant;
import ants.map.Vertex;

/**
 * ExplosionCommand
 * Command that is issued to explode a vertex,
 * killing all ants in it
 */
public class ExplosionCommand extends Command {
    private Ant attacker;

    public ExplosionCommand(Ant attacker) {
        this.attacker = attacker;
    }

    public boolean execute() {

        Vertex vertex = this.attacker.getCurrentVertex();

        this.attacker.die();

        for (Ant ant : vertex.getBlueAnts()) {
            if (ant.leaveVertexSemaphore.tryAcquire()) {
                if (ant.isAlive()) {
                    ant.die();
                }
            }
        }

        for (Ant ant : vertex.getRedAnts()) {
            if (ant.leaveVertexSemaphore.tryAcquire()) {
                if (ant.isAlive()) {
                    ant.die();
                }
            }
        }

        return true;
    }

}
