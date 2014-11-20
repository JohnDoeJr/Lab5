package com.chiefs.client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.util.*;

import javax.swing.JPanel;

import com.chiefs.server.Forme;

public class GraphicPanel extends JPanel {

	private static final float VERTICAL_CENTER = 200;
	private static final float HORIZONTAL_CENTER = 300;
	private static final int STANDARD_RADIUS = 14;

	private final Forme forme;
	private List<Nokta> points;
	private boolean isGray;

	private int counter = 0;

	public GraphicPanel(Forme forme, List<Nokta> points) {
		this.forme = forme;
		setBackground(Color.YELLOW);
		this.points = points;
	}

	public void setGray(boolean value) {
		isGray = value;
	}

	@Override
	public void paint(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	private void draw(Graphics g) {
		drawForme(g);
		drawAxes(g);

		Graphics2D g2 = (Graphics2D) g;

		if (points.size() > 0) {
			for (int i = 0; i < points.size() - 1; i++) {
				Nokta point = points.get(i);
				if (isGray) {
					g2.setColor(Color.GRAY);
				} else if (forme.test(point)) {
					g2.setColor(Color.RED);
				} else {
					g2.setColor(Color.GREEN);
				}
				drawPoint(point, g2, STANDARD_RADIUS);
			}

			Nokta point = points.get(points.size() - 1);

			if (forme.test(point)) {
				if (isGray) {
					g2.setColor(Color.GRAY);
				} else {
					g2.setColor(Color.RED);
				}
				drawPoint(point, g2, STANDARD_RADIUS);
			} else {
				if (isGray) {
					g2.setColor(Color.GRAY);
				} else {
					g2.setColor(Color.GREEN);
				}
				drawPoint(point, g2, STANDARD_RADIUS + counter);
			}
		}
	}

	private void drawAxes(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLACK);
		// Draw axis X
		g2.drawLine(0, (int) VERTICAL_CENTER, getWidth(), (int) VERTICAL_CENTER);
		g2.drawLine(getWidth(), (int) VERTICAL_CENTER, getWidth() - 7,
				(int) (VERTICAL_CENTER + 7));
		g2.drawLine(getWidth(), (int) VERTICAL_CENTER, getWidth() - 7,
				(int) (VERTICAL_CENTER - 7));
		g2.drawString("R/2", HORIZONTAL_CENTER + forme.getRadius() / 2,
				VERTICAL_CENTER - 10);
		g2.drawString("R", HORIZONTAL_CENTER + forme.getRadius(), VERTICAL_CENTER - 10);
		g2.drawString("-R/2", HORIZONTAL_CENTER - forme.getRadius() / 2,
				VERTICAL_CENTER - 10);
		g2.drawString("X", getWidth() - 10, VERTICAL_CENTER + 19);

		// Draw axis Y
		g2.drawLine((int) HORIZONTAL_CENTER, 0, (int) HORIZONTAL_CENTER,
				getHeight());
		g2.drawLine((int) HORIZONTAL_CENTER, 0, (int) HORIZONTAL_CENTER - 7, 10);
		g2.drawLine((int) HORIZONTAL_CENTER, 0, (int) HORIZONTAL_CENTER + 7, 10);
		g2.drawString("R", HORIZONTAL_CENTER + 10,
				VERTICAL_CENTER - forme.getRadius());
		g2.drawString("-R/2", HORIZONTAL_CENTER + 10,
				VERTICAL_CENTER + forme.getRadius() / 2);
		g2.drawString("Y", HORIZONTAL_CENTER + 10, 15);
	}

	private void drawPoint(Nokta point, Graphics2D g2, int radius) {
		g2.fill(new Ellipse2D.Double(GraphicPanel.HORIZONTAL_CENTER
				+ point.getAxisX() - forme.getRadius() / (radius * 2),
				GraphicPanel.VERTICAL_CENTER - point.getAxisY()
						- forme.getRadius() / (radius * 2), forme.getRadius()
						/ radius, forme.getRadius() / radius));
	}

	private void drawForme(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.YELLOW);
		g2.fillRect(0, 0, getWidth(), getHeight());
		g2.setColor(Color.BLUE);

		// draw rectangle
		g2.fill(new Rectangle2D.Double(HORIZONTAL_CENTER, VERTICAL_CENTER - forme.getRadius() / 2,
				forme.getRadius(), forme.getRadius() / 2));

		// draw triangle
		float xPoints[] = { HORIZONTAL_CENTER - forme.getRadius() / 2,
				HORIZONTAL_CENTER, HORIZONTAL_CENTER };
		float yPoints[] = { VERTICAL_CENTER, VERTICAL_CENTER - forme.getRadius(),
				VERTICAL_CENTER };
		GeneralPath triangle = new GeneralPath(GeneralPath.WIND_NON_ZERO,
				xPoints.length);

		triangle.moveTo(xPoints[0], yPoints[0]);
		for (int i = 1; i < xPoints.length; i++) {
			triangle.lineTo(xPoints[i], yPoints[i]);
		}

		triangle.closePath();
		g2.fill(triangle);

		// draw sector
		g2.fill(new Arc2D.Double(HORIZONTAL_CENTER - forme.getRadius() / 2,
				VERTICAL_CENTER - forme.getRadius() / 2, forme.getRadius(),
				forme.getRadius(), 270, 90, Arc2D.PIE));
	}

	public void redraw() {
		for (int i = 0; i < 10; i++) {
			counter++;
			repaint();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {

			}
		}
		for (int i = 0; i < 10; i++) {
			counter--;
			repaint();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {

			}
		}
	}
}
