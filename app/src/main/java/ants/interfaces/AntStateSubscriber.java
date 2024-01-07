package ants.interfaces;

import ants.anttypes.AntState;

/**
 * AntStateSubscriber
 *
 * Observer interface notifying about ant state changes.
 */
public interface AntStateSubscriber {
   public void onAntStateChange(AntState state); 
}
