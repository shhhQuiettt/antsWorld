package ants.threads;

import ants.commands.Command;
import ants.map.Vertex;

/**
 * CommandExecuter
 */
public class CommandExecutor extends Thread {
    Vertex vertex;

    public CommandExecutor(Vertex vertex) {
        this.vertex = vertex;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Command command = this.vertex.getCommandQueue().take();
                System.out.println("CommandExecutor executing command: " + command);
                boolean result = command.execute();
                System.out.println("CommandExecutor executed command: " + command + " with result: " + result);
                System.out.println(this.vertex.getCommandQueue().size() + "commands left in queue\n\n");
            } catch (InterruptedException e) {
                System.err.println("CommandExecutor interrupted");
            }

        }
    }
}
