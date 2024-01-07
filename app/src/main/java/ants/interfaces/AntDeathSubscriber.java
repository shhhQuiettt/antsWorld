package ants.interfaces;

import ants.anttypes.Ant;

/**
 * AntDeathSubscriber
 *
 * Observer interface notified on Ant death
 */
public interface AntDeathSubscriber {

   public void onAntDeath(Ant ant); 
}
