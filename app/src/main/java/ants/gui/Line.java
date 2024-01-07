package ants.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JLabel;

/**
 * Line
 *
 * Class displaying a line between two points
 */
class Line extends JLabel {
    private Point startPoint;
    private Point endPoint;
    private float lineStroke = 1.0f;

    public Line(Point startPoint, Point endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.setOpaque(false);
        this.setPosition();
    }

    public Line(Point startPoint, Point endPoint, float lineStroke) {
        this(startPoint, endPoint);
        this.lineStroke = lineStroke;
    }

    public void setPosition() {
        int width = Math.abs(this.endPoint.x - this.startPoint.x) + (int) Math.ceil(this.lineStroke);
        int height = Math.abs(this.endPoint.y - this.startPoint.y) + (int) Math.ceil(this.lineStroke);

        int leftUpperX = Math.min(this.startPoint.x, this.endPoint.x);
        int leftUpperY = Math.min(this.startPoint.y, this.endPoint.y);

        this.setPreferredSize(new Dimension(width, height));
        this.setBounds(leftUpperX, leftUpperY, width, height);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(this.lineStroke));
        g2d.drawLine(startPoint.x - getX(), startPoint.y - getY(), endPoint.x - getX(), endPoint.y - getY());
    }
}
