// package ants;

// import java.util.ArrayList;
// import java.util.concurrent.locks.Lock;
// import java.util.HashMap;
// /**
//  * AntsGarbageCollector
//  */
// public class AntsGarbageCollector extends Thread implements AntDeathSubscriber {
//     HashMap<Ant, AntThread> antThreads;

//     @Override
//     public synchronized void onAntDeath(Ant ant) {
//         this.run();
//     }

    
//     public AntsGarbageCollector(HashMap<Ant, AntThread> antThreads) {
//         this.antThreads = antThreads;
//     }

//     @Override
//     public void run() {
//         AntThread antThread = this.antThreads.get(ant);

//         antThread.stop();
//         this.antThreads.remove(ant);
//     }

//     // block, waiting for ant death

    
// }
