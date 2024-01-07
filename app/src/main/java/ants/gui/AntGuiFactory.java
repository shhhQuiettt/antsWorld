package ants.gui;

import java.util.HashMap;

import ants.anttypes.Ant;
import ants.anttypes.BlundererAnt;
import ants.anttypes.CannonFodderAnt;
import ants.anttypes.CollectorAnt;
import ants.anttypes.KamikazeAnt;
import ants.anttypes.SoldierAnt;
import ants.anttypes.WorkerAnt;

/**
 * RedAntGuiFactory
 */
public class AntGuiFactory {
    private final HashMap<String, AntImageConfig> antImagesConfigs = new HashMap<>();

    public AntGuiFactory() {
        this.antImagesConfigs.put("soldier", new AntImageConfig("antRedSoldier", "antRedSoldierAttacking", "antRedSoldierDying",
                "antRedSoldierDead"));
        this.antImagesConfigs.put("collector",
                new AntImageConfig("antRedCollector", null, "antRedCollectorDying", "antRedCollectorDead"));
        this.antImagesConfigs.put("worker", new AntImageConfig("antBlueWorker", "antBlueWorkerAttacking", "antBlueWorkerDying",
                "antBlueWorkerDead"));
        this.antImagesConfigs.put("blunderer", new AntImageConfig("antRedBlunderer", null,
                "antRedBlundererDying", "antRedBlundererDead"));
        this.antImagesConfigs.put("cannon fodder", new AntImageConfig("antBlueCannonFodder", null,
                "antBlueCannonFodderDying", "antBlueCannonFodderDead"));

        this.antImagesConfigs.put("kamikaze", new AntImageConfig("antBlueKamikaze", "antBlueKamikazeDying",
                "antBlueKamikazeDying", "antBlueKamikazeDead"));
    }

    public AntGui newAntGui(Ant ant) {
        if (ant instanceof SoldierAnt) {
            return this.newSoldierAntGui((SoldierAnt) ant);
        } else if (ant instanceof CollectorAnt) {
            return this.newCollectorAntGui((CollectorAnt) ant);
        } else if (ant instanceof WorkerAnt) {
            return this.newWorkerAntGui((WorkerAnt) ant);
        } else if (ant instanceof BlundererAnt) {
            return this.newBlundererAntGui((BlundererAnt) ant);
        } else if (ant instanceof CannonFodderAnt) {
            return this.newCannonFodderGui((CannonFodderAnt) ant);
        } else if (ant instanceof KamikazeAnt) {
            return this.newKamikazeAntGui((KamikazeAnt) ant);
        } else {
            throw new RuntimeException("Unknown ant type");
        }
    }

    public AntGui newSoldierAntGui(SoldierAnt ant) {
        return new AntGui(ant, this.antImagesConfigs.get("soldier"));
    }

    public AntGui newCollectorAntGui(CollectorAnt ant) {
        return new AntGui(ant, this.antImagesConfigs.get("collector"));
    }

    public AntGui newBlundererAntGui(BlundererAnt ant) {
        // return new LarveableAntGui(ant, ant, this.antImagesConfigs.get("blunderer"));
        return new AntGui(ant, this.antImagesConfigs.get("blunderer"));
    }

    public AntGui newWorkerAntGui(WorkerAnt ant) {
        // TODO: fix this shit
        // return new LarveableAntGui(ant, ant, this.antImagesConfigs.get("worker"));
        return new AntGui(ant, this.antImagesConfigs.get("worker"));
    }

    public AntGui newCannonFodderGui(CannonFodderAnt ant) {
        return new AntGui(ant, this.antImagesConfigs.get("cannon fodder"));
    }

    public AntGui newKamikazeAntGui(KamikazeAnt ant) {
        return new AntGui(ant, this.antImagesConfigs.get("kamikaze"));
    }

}
