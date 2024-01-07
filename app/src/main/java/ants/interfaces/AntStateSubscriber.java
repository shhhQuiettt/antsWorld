package ants.interfaces;

import ants.anttypes.AntState;

/**
 * AntStateSubscriber
 */
public interface AntStateSubscriber {
   public void onAntStateChange(AntState state); 
}
