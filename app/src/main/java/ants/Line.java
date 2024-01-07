package ants;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

class Line extends JLabel {
    private Point startPoint;
    private Point endPoint;
    private float lineStroke = 1.0f;

    public Line(Point startPoint, Point endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        // this.setLayout(null);
        this.setOpaque(false);
        this.setPosition();

        // int width = Math.abs(this.endPoint.x - this.startPoint.x);
        // int height = Math.abs(this.endPoint.y - this.startPoint.y);
        // this.setBounds(this.startPoint.x, this.startPoint.y, width, height);
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

    // @Override
    // public Dimension getPreferredSize() {
    // // Calculate preferred size based on start and end points
    // int width = Math.abs(endPoint.x - startPoint.x);
    // int height = Math.abs(endPoint.y - startPoint.y);
    // return new Dimension(width, height);
    // }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw the line
        // g2d.setColor(Color.BLACK);
        // g2d.draw(new Line2D.Double(startPoint, endPoint));
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(this.lineStroke));
        g2d.drawLine(startPoint.x - getX(), startPoint.y - getY(), endPoint.x - getX(), endPoint.y - getY());
    }
}
