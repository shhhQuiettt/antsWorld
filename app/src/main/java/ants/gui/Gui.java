package ants.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

/**
 * Gui
 */
public class Gui {
    private JFrame frame;

    private JPanel world;
    private JPanel buttonPanel;
    // private JPanel infoPanel;
    //
    String antInfoDefaultText = "[Click an Ant to see the info]";
    String vertexDefaultInfoText = "[Click a Vertex to see the info]";

    private JLabel antInfo;
    private JLabel vertexInfo;

    private final int milisecondPerFrame = 1000 / 60;

    private BlockingQueue<UpdatableSprite> updatebleComponents = new LinkedBlockingQueue<UpdatableSprite>();
    private ArrayList<Sprite> sprites = new ArrayList<Sprite>();
    private ArrayList<Line> lines = new ArrayList<Line>();

    long startTime = System.currentTimeMillis();

    // private Lock mutex = new ReentrantLock();

    public Gui(int worldWidth, int worldHeight, JPanel buttonPanel) {
        int buttonPanelHeight = 100;
        int infoPanelWidth = 300;

        this.frame = new JFrame("Ants world");
        this.frame.setSize(worldWidth + infoPanelWidth, worldHeight + buttonPanelHeight);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.world = new JPanel();
        this.world.setLayout(null);
        this.world.setPreferredSize(new Dimension(worldWidth, worldHeight));
        this.frame.add(world);

        this.buttonPanel = buttonPanel;
        this.frame.add(this.buttonPanel, BorderLayout.SOUTH);
        buttonPanel.setVisible(true);

        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(new Color(50, 50, 50));

        this.antInfo = new JLabel(this.antInfoDefaultText);
        this.antInfo.setForeground(new Color(200, 200, 200));
        this.antInfo.setPreferredSize(new Dimension(infoPanelWidth, worldHeight / 2));
        this.antInfo.setBorder(BorderFactory.createLineBorder(Color.black));
        this.antInfo.setVerticalAlignment(SwingConstants.TOP);

        this.vertexInfo = new JLabel(this.vertexDefaultInfoText);
        this.vertexInfo.setForeground(new Color(200, 200, 200));
        this.vertexInfo.setPreferredSize(new Dimension(infoPanelWidth, worldHeight / 2));
        this.vertexInfo.setBorder(BorderFactory.createLineBorder(Color.black));
        this.vertexInfo.setVerticalAlignment(SwingConstants.TOP);

        infoPanel.setPreferredSize(new Dimension(infoPanelWidth, worldHeight));

        infoPanel.add(antInfo);
        infoPanel.add(vertexInfo);

        frame.add(infoPanel, BorderLayout.EAST);

        frame.setVisible(true);
    }

    public void addUnfocusListener(Runnable onUnfocus) {
        this.frame.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                System.out.println("ololo");
                for (Sprite sprite : sprites) {
                    if (sprite.imageContains(e.getPoint())) {
                        return;
                    }
                }

                for (UpdatableSprite updatableSprite : updatebleComponents) {
                    if (updatableSprite.imageContains(e.getPoint())) {
                        return;
                    }
                }

                onUnfocus.run();
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }

    public void addStaticSprite(Sprite sprite) {
        this.sprites.add(sprite);
        this.world.add(sprite);
        sprite.setPosition();
        sprite.setVisible(true);
    }

    public void addUpdatableSprite(UpdatableSprite animatedSprite) {
        this.updatebleComponents.add(animatedSprite);
        this.world.add(animatedSprite);
        this.world.setComponentZOrder(animatedSprite, 0);
        animatedSprite.setPosition();
        animatedSprite.setVisible(true);
    }

    public void addLine(Point start, Point end) {
        Line line = new Line(start, end);

        this.lines.add(line);
        this.world.add(line);
        line.setPosition();

        line.setVisible(true);
        // this.world.setComponentZOrder(l, 1);

        this.world.repaint();
    }

    public void setAntInfo(String text) {
        if (text == null) {
            this.antInfo.setText(this.antInfoDefaultText);
            return;
        }
        this.antInfo.setText(convertStringToHtml(text));
    }

    public void setVertexInfo(String text) {
        if (text == null) {
            this.vertexInfo.setText(this.vertexDefaultInfoText);
            return;
        }
        this.vertexInfo.setText(convertStringToHtml(text));
    }

    private String convertStringToHtml(String text) {
        StringBuffer sb = new StringBuffer();
        sb.append("<html><p>");
        sb.append(text.replaceAll("\n", "<br>"));
        sb.append("</p></html>");
        return sb.toString();
    }

    public void removeUpdatableSprite(UpdatableSprite animatedSprite) {
        this.updatebleComponents.remove(animatedSprite);
        this.world.remove(animatedSprite);
    }

    public void start() {
        Timer timer = new Timer(this.milisecondPerFrame, e -> {
            this.update();
        });
        timer.start();
    }

    public void update() {
        for (UpdatableSprite updatableSprite : this.updatebleComponents) {
            updatableSprite.update(this.milisecondPerFrame);
        }
        Toolkit.getDefaultToolkit().sync();
    }

}
