package ants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;
import java.util.function.Consumer;

/**
 * AntCreationButtonPanel
 */
public class AntCreationButtonPanel extends JPanel {

    private RedAntFactory redAntFactory;
    private BlueAntFactory blueAntFactory;

    private int buttonNumber = 3;
    private int buttonHeight = 70;
    private int buttonWidth = 100;

    public AntCreationButtonPanel(RedAntFactory redAntFactory, BlueAntFactory blueAntFactory,
            Consumer<Ant> onAntCreation) {
        this.redAntFactory = redAntFactory;
        this.blueAntFactory = blueAntFactory;
        this.setBackground(
                new Color(30, 30, 30));

        this.setLayout(new FlowLayout());
        setPreferredSize(new Dimension(
                buttonWidth * buttonNumber,
                buttonHeight));

        JButton redSoldierButton = new JButton("Red Soldier");
        // redSoldierButton.setBounds(0, 0, buttonWidth, buttonHeight);
        redSoldierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ant ant = redAntFactory.newSoldierAnt("Red Soldier", 100, 10);
                onAntCreation.accept(ant);
            }
        });
        redSoldierButton.setVisible(true);

        JButton redCollectorButton = new JButton("Red Collector");
        // redCollectorButton.setBounds(1 * buttonWidth, buttonHeight, buttonWidth,
        // buttonHeight);
        redCollectorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ant ant = redAntFactory.newCollectorAnt("Red Collector", 100, 3);
                onAntCreation.accept(ant);
            }
        });
        redCollectorButton.setVisible(true);

        JButton blueWorkerButton = new JButton("Blue Worker");
        // blueWorkerButton.setBounds(2 * buttonWidth, 2 * buttonHeight, buttonWidth,
        // buttonHeight);
        blueWorkerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ant ant = blueAntFactory.newWorkerAnt("Blue Worker", 100, 2);
                onAntCreation.accept(ant);
            }
        });
        blueWorkerButton.setVisible(true);

        JButton blueBlundererButton = new JButton("Blue Cannon Fodder");
        // blueBlundererButton.setBounds(3 * buttonWidth, 3 * buttonHeight, buttonWidth,
        // buttonHeight);
        blueBlundererButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ant ant = blueAntFactory.newCannonFodderAnt("Blue Cannon Fodder", 100, 10);
                onAntCreation.accept(ant);
            }
        });
        blueBlundererButton.setVisible(true);

        JButton redBlundererButton = new JButton("Red Blunderer");
        // redBlundererButton.setBounds(3 * buttonWidth, 3 * buttonHeight, buttonWidth,
        // buttonHeight);
        redBlundererButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ant ant = redAntFactory.newBlundererAnt("Red Blunderer", 100, 3, 0.5);
                onAntCreation.accept(ant);
            }
        });
        redBlundererButton.setVisible(true);

        JButton blueKamikazeButton = new JButton("Blue Kamikaze");
        blueKamikazeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ant ant = blueAntFactory.newKamikazeAnt("Blue Kamikaze", 100, 10);
                onAntCreation.accept(ant);
            }
        });

        this.add(redSoldierButton);
        this.add(redCollectorButton);
        this.add(redBlundererButton);
        this.add(blueWorkerButton);
        this.add(blueBlundererButton);
        this.add(blueKamikazeButton);
    }

}
