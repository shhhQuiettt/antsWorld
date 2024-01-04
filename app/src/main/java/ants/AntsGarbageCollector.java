// package ants;

// import java.util.ArrayList;
// import java.util.concurrent.locks.Lock;
// import java.util.HashMap;
// /**
//  * AntsGarbageCollector
//  */
// public class AntsGarbageCollector implements AntDeathSubscriber {
//     HashMap<Ant, AntThread> antThreads;
//     ArrayList<Ant> ants;
//     ArrayList<AntGui> antGuis;


//     @Override
//     public synchronized void onAntDeath(Ant ant) {
//         AntThread antThread = this.antThreads.get(ant);
//         this.antGuis.remove(ant.getAntGui());

//         antThread.interrupt();
//         this.antThreads.remove(ant);
//         this.ants.remove(ant);
//     }

    
//     public AntsGarbageCollector(ArrayList<Ant> ants, ArrayList<AntGui> antGuis, HashMap<Ant, AntThread> antThreads, Lock guiLock) {
//         this.antThreads = antThreads;
//         this.ants = ants;
//         this.antGuis = antGuis;
//     }
    
// }
