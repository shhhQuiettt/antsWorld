package ants.threads;

import ants.commands.Command;
import ants.map.Vertex;

/**
 * CommandExecuter
 *
 * Thread responsible for executing commands
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
                command.execute();
            } catch (InterruptedException e) {
                System.err.println("CommandExecutor interrupted");
            }

        }
    }
}
