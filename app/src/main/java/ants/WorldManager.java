package ants;

import java.awt.Point;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.SwingUtilities;

import ants.anttypes.Ant;
import ants.anttypes.BlueAntFactory;
import ants.anttypes.RedAntFactory;
import ants.gui.AntCreationButtonPanel;
import ants.gui.AntGui;
import ants.gui.AntGuiFactory;
import ants.gui.AnthillGui;
import ants.gui.Gui;
import ants.gui.VertexGui;
import ants.interfaces.AntDeathSubscriber;
import ants.map.Map;
import ants.map.Vertex;
import ants.threads.AntInfoThread;
import ants.threads.AntThread;
import ants.threads.CommandExecutor;
import ants.threads.VertexInfoThread;

/**
 * WorldManager
 *
 * Main class responsible for running the simulation
 */

public class WorldManager implements AntDeathSubscriber {
    private final Map map;
    private Gui gui;

    private ArrayList<Ant> ants;

    private final WorldConfig config;
    private RedAntFactory redAntFactory;
    private BlueAntFactory blueAntFactory;
    private AntGuiFactory antGuiFactory;

    private ArrayList<AntThread> antThreads;
    private ArrayList<CommandExecutor> commandExecutors = new ArrayList<>();
    private AntInfoThread antInfoThread;
    private VertexInfoThread vertexInfoThread;

    private HashMap<Ant, AntThread> antThreadMap = new HashMap<Ant, AntThread>();

    public WorldManager(WorldConfig config) {
        this.map = Map.generateRandomMap(config.getVertexNumber(),
                config.getStoneProbability(),
                config.getLeafProbability(),
                config.getWorldWidth(),
                config.getWorldHeight(),
                config.getVertexNeighborhoodSize(),
                config.getMaxLarvaePerVertex());

        this.config = config;
        this.ants = new ArrayList<Ant>();
        this.antThreads = new ArrayList<AntThread>();
        this.commandExecutors = new ArrayList<CommandExecutor>(config.getVertexNumber());

        this.redAntFactory = new RedAntFactory(map.getRedAnthill(), map.getBlueAnthill());
        this.blueAntFactory = new BlueAntFactory(map.getBlueAnthill(), map.getRedAnthill());
        this.antGuiFactory = new AntGuiFactory();
    }

    public void run() {
        AntCreationButtonPanel antCreationButtonPanel = new AntCreationButtonPanel(this.redAntFactory,
                this.blueAntFactory,
                (ant) -> {
                    this.registerNewAnt(ant);
                });

        this.gui = new Gui(this.config.getWorldWidth(), this.config.getWorldHeight(), antCreationButtonPanel);

        for (Vertex v : this.map.getVertices()) {
            this.addVertexToGui(v);
        }

        this.addAnthillsToGui();

        this.addLinesBetweenNeighboursToGui();

        this.createCommandExecutors();

        this.createAntInfoThread();
        this.createVertexInfoThread();

        this.startCommandExecutors();
        this.antInfoThread.start();
        this.vertexInfoThread.start();

        this.startAntThreads();

        gui.start();

    }

    private synchronized void registerNewAnt(Ant ant) {
        this.ants.add(ant);

        AntThread antThread = new AntThread(ant);
        this.antThreads.add(antThread);
        this.antThreadMap.put(ant, antThread);

        ant.subscribeDeath(this);

        AntGui antGui = this.antGuiFactory.newAntGui(ant);
        this.attachAntInfoListener(antGui);

        this.gui.addUpdatableSprite(antGui);
        antThread.start();
    }

    private void addVertexToGui(Vertex v) {
        VertexGui vertexGui = new VertexGui(v);
        this.attachVertexInfoListener(vertexGui);
        this.gui.addStaticSprite(vertexGui);
    }

    private void addLinesBetweenNeighboursToGui() {
        ArrayList<Vertex> verticesToConnect = new ArrayList<>();
        verticesToConnect.addAll(this.map.getVertices());
        verticesToConnect.add(this.map.getRedAnthill());
        verticesToConnect.add(this.map.getBlueAnthill());

        for (Vertex v : verticesToConnect) {
            for (Vertex neighbour : v.getNeighbors()) {
                Point p1 = new Point(v.getX(), v.getY());
                Point p2 = new Point(neighbour.getX(), neighbour.getY());

                double dx = p2.x - p1.x;
                double dy = p2.y - p1.y;

                double ratio = 0.8;
                p2.x = (int) (p1.x + ratio * dx);
                p2.y = (int) (p1.y + ratio * dy);

                p1.x = (int) (p1.x + (1 - ratio) * dx);
                p1.y = (int) (p1.y + (1 - ratio) * dy);

                this.gui.addLine(p1, p2);
            }
        }

    }

    private void addAnthillsToGui() {
        AnthillGui redAnthillGui = new AnthillGui(this.map.getRedAnthill());
        this.attachVertexInfoListener(redAnthillGui);
        this.gui.addStaticSprite(redAnthillGui);

        AnthillGui blueAnthillGui = new AnthillGui(this.map.getBlueAnthill());
        this.attachVertexInfoListener(blueAnthillGui);
        this.gui.addStaticSprite(blueAnthillGui);
    }

    private void startAntThreads() {
        for (AntThread antThread : this.antThreads) {
            antThread.start();
        }
    }

    private void createCommandExecutors() {
        for (Vertex v : this.map.getVertices()) {
            this.commandExecutors.add(new CommandExecutor(v));
        }

        this.commandExecutors.add(new CommandExecutor(this.map.getRedAnthill()));
        this.commandExecutors.add(new CommandExecutor(this.map.getBlueAnthill()));
    }

    private void startCommandExecutors() {
        for (CommandExecutor commandExecutor : this.commandExecutors) {
            commandExecutor.start();
        }
    }

    private void createAntInfoThread() {
        this.gui.addUnfocusListener(new Runnable() {
            @Override
            public void run() {
                WorldManager.this.antInfoThread.setAntGui(null);
            }
        });

        this.antInfoThread = new AntInfoThread(
                (info) -> {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            WorldManager.this.gui.setAntInfo(info);
                        }
                    });
                });
    }

    private void attachAntInfoListener(AntGui antGui) {
        antGui.addMouseListener(
                new MouseListener() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent e) {
                        WorldManager.this.antInfoThread.setAntGui(antGui);
                    }

                    @Override
                    public void mousePressed(java.awt.event.MouseEvent e) {
                    }

                    @Override
                    public void mouseReleased(java.awt.event.MouseEvent e) {
                    }

                    @Override
                    public void mouseEntered(java.awt.event.MouseEvent e) {
                    }

                    @Override
                    public void mouseExited(java.awt.event.MouseEvent e) {
                    }
                });
    }

    private void createVertexInfoThread() {
        this.gui.addUnfocusListener(new Runnable() {
            @Override
            public void run() {
                WorldManager.this.vertexInfoThread.setVertexGui(null);
            }
        });

        this.vertexInfoThread = new VertexInfoThread(
                (info) -> {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            WorldManager.this.gui.setVertexInfo(info);
                        }
                    });
                });
    }

    private void attachVertexInfoListener(VertexGui vertexGui) {
        vertexGui.addMouseListener(
                new MouseListener() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent e) {
                        WorldManager.this.vertexInfoThread.setVertexGui(vertexGui);
                    }

                    @Override
                    public void mousePressed(java.awt.event.MouseEvent e) {
                    }

                    @Override
                    public void mouseReleased(java.awt.event.MouseEvent e) {
                    }

                    @Override
                    public void mouseEntered(java.awt.event.MouseEvent e) {
                    }

                    @Override
                    public void mouseExited(java.awt.event.MouseEvent e) {
                    }
                });
    }

    @Override
    public synchronized void onAntDeath(Ant ant) {
        AntThread antThread = this.antThreadMap.get(ant);

        antThread.interrupt();
    }
}
