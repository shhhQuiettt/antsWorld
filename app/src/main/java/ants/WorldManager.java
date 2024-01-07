package ants;

import java.awt.Point;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.sound.sampled.Line;
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
 */

public class WorldManager implements AntDeathSubscriber {
    private final Map map;
    private Gui gui;

    private ArrayList<Ant> ants;

    private ArrayList<AntGui> antGuis = new ArrayList<AntGui>();
    private ArrayList<VertexGui> vertexGuis = new ArrayList<VertexGui>();

    private final WorldConfig config;
    private RedAntFactory redAntFactory;
    private BlueAntFactory blueAntFactory;
    private AntGuiFactory antGuiFactory;

    private ArrayList<AntThread> antThreads;
    private ArrayList<CommandExecutor> commandExecutors = new ArrayList<>();
    private AntInfoThread antInfoThread;
    private VertexInfoThread vertexInfoThread;

    private HashMap<Ant, AntThread> antThreadMap = new HashMap<Ant, AntThread>();
    // private HashMap<Ant, AntGui> antGuiMap = new HashMap<Ant, AntGui>();
    // private Lock guiLock = new ReentrantLock();

    public WorldManager(WorldConfig config) {
        this.map = Map.generateRandomMap(config.vertexNumber,
                config.stoneProbability,
                config.leafProbability,
                config.width,
                config.height,
                config.vertexNeighborhoodSize,
                config.maxLarvaePerVertex);

        this.config = config;
        // this.ants = new ArrayBlockingQueue<Ant>(config.maxRedAnts +
        // config.maxBlueAnts);
        this.ants = new ArrayList<Ant>(config.maxRedAnts + config.maxBlueAnts);

        this.antThreads = new ArrayList<AntThread>(config.maxRedAnts + config.maxBlueAnts);
        this.commandExecutors = new ArrayList<CommandExecutor>(config.vertexNumber);
        this.redAntFactory = new RedAntFactory(map.getRedAnthill(), map.getBlueAnthill());
        this.blueAntFactory = new BlueAntFactory(map.getBlueAnthill(), map.getRedAnthill());
        this.antGuiFactory = new AntGuiFactory();
    }

    public void run() {

        // WorkerAnt ant = this.blueAntFactory.newWorkerAnt("blue", 1, 1);

        System.out.println("Main Thread:" + Thread.currentThread().getName());

        AntCreationButtonPanel antCreationButtonPanel = new AntCreationButtonPanel(this.redAntFactory,
                this.blueAntFactory,
                (ant) -> {
                    this.registerNewAnt(ant);
                });

        this.gui = new Gui(this.config.width, this.config.height, antCreationButtonPanel);

        this.addVerticesToGui();

        this.addAnthillsToGui();

        this.addLinesBetweenNeighboursToGui();

        this.addAntsToGui();


        this.createAntThreads();

        this.createCommandExecutors();

        this.createAntInfoThread();

        this.createVertexInfoThread();

        this.addInfoListeners();

        this.startAntThreads();

        this.startCommandExecutors();

        this.antInfoThread.start();
        this.vertexInfoThread.start();

        // measure average time for one loop execution

        gui.start();

        System.out.println("Reachable? ");
    }

    private void addInfoListeners() {
        for (AntGui antGui : this.antGuis) {
            this.attachAntInfoListener(antGui);
        }

        for (VertexGui vertexGui : this.vertexGuis) {
            this.attachVertexInfoListener(vertexGui);
        }

            // this.attachVertexInfoListener(vertexGui);
            // this.attachVertexInfoListener(vertexGui);


    }

    private synchronized void registerNewAnt(Ant ant) {
        System.out.println(Thread.currentThread().getName());

        this.ants.add(ant);

        AntThread antThread = new AntThread(ant);
        this.antThreads.add(antThread);
        this.antThreadMap.put(ant, antThread);

        ant.subscribeDeath(this);

        AntGui antGui = this.antGuiFactory.newAntGui(ant);
        this.attachAntInfoListener(antGui);
        this.antGuis.add(antGui);

        this.gui.addUpdatableSprite(antGui);
        antThread.start();
    }

    // separate
    private void addAntsToGui() {
        for (int i = 0; i < this.config.initialRedAnts; i++) {

            Ant ant;
            // if (Math.random() < 0.5)
            // ant = this.redAntFactory.newCollectorAnt("red" + i, 1, 1);
            // else
            // ant = this.redAntFactory.newSoldierAnt("red" + i, 1, 1);
            ant = this.redAntFactory.newBlundererAnt("red" + i, 1, 1, 1.0);
            this.ants.add(ant);
            this.gui.addUpdatableSprite(antGuiFactory.newAntGui(ant));
        }

        for (int i = 0; i < this.config.initialBlueAnts; i++) {
            Ant ant = this.blueAntFactory.newWorkerAnt("blue" + i, 1, 1);
            this.ants.add(ant);
            AntGui antGui = this.antGuiFactory.newAntGui(ant);
            this.antGuis.add(antGui);

            // LarvaeOnHeadAdder updater = new LarvaeOnHeadAdder((LarvaeInfoEmiter) ant);
            // antGui.addImageUpdater(updater);

            this.gui.addUpdatableSprite(antGui);
        }
    }

    private void addVerticesToGui() {
        for (Vertex v : this.map.getVertices()) {
            VertexGui vertexGui = new VertexGui(v);
            this.vertexGuis.add(vertexGui);
            this.gui.addStaticSprite(vertexGui);
        }
    }

    private void addLinesBetweenNeighboursToGui() {
        // TODO: double lines
        for (Vertex v : this.map.getVertices()) {
            for (Vertex neighbour : v.getNeighbors()) {
                Point p1 = new Point(v.getX(), v.getY());
                Point p2 = new Point(neighbour.getX(), neighbour.getY());

                // make them a bit nearer to themselves
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


        for (Vertex neighbour : this.map.getRedAnthill().getNeighbors()) {
            Point p1 = new Point(this.map.getRedAnthill().getX(), this.map.getRedAnthill().getY());
            Point p2 = new Point(neighbour.getX(), neighbour.getY());

            // make them a bit nearer to themselves
            double dx = p2.x - p1.x;
            double dy = p2.y - p1.y;

            double ratio = 0.8;
            p2.x = (int) (p1.x + ratio * dx);
            p2.y = (int) (p1.y + ratio * dy);

            p1.x = (int) (p1.x + (1 - ratio) * dx);
            p1.y = (int) (p1.y + (1 - ratio) * dy);

            this.gui.addLine(p1, p2);
        }

        for (Vertex neighbour : this.map.getBlueAnthill().getNeighbors()) {
            Point p1 = new Point(this.map.getBlueAnthill().getX(), this.map.getRedAnthill().getY());
            Point p2 = new Point(neighbour.getX(), neighbour.getY());

            // make them a bit nearer to themselves
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

    private void addAnthillsToGui() {
        this.gui.addStaticSprite(new AnthillGui(this.map.getRedAnthill()));
        this.gui.addStaticSprite(new AnthillGui(this.map.getBlueAnthill()));
    }

    private void createAntThreads() {
        for (Ant ant : this.ants) {
            AntThread antThread = new AntThread(ant);
            // TODO: other functions
            ant.subscribeDeath(this);
            this.antThreads.add(antThread);
            this.antThreadMap.put(ant, antThread);
        }
    }

    private void startAntThreads() {
        for (AntThread antThread : this.antThreads) {
            antThread.start();
        }
    }

    private void createCommandExecutors() {
        for (Vertex v : this.map.getVertices()) {
            CommandExecutor commandExecutor = new CommandExecutor(v);
            this.commandExecutors.add(commandExecutor);
        }
    }

    private void startCommandExecutors() {
        for (CommandExecutor commandExecutor : this.commandExecutors) {
            commandExecutor.start();
        }
    }

    private void createAntInfoThread() {
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
                        System.out.println("dupa");
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
        System.out.println("Ant deaths broadcasted");
        AntThread antThread = this.antThreadMap.get(ant);
        // this.guiLock.lock();
        // this.gui.removeUpdatableSprite(this.antGuiMap.get(ant));
        // this.guiLock.unlock();

        // kill thread
        antThread.stop();
        this.antThreadMap.remove(ant);
        // this.ants.remove(ant);
    }
}
