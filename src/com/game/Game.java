package com.game;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;

import com.states.EndGame;
import com.states.State;

public class Game {

	private int rotIndex = 0;
	private ArrayList<Point> lines;
	private int score = 0;
	private int points_per_level = 100;
	private int level = 1;
	private boolean game_over = false;
	private double chrono;
	private int timeLevel = 1000;
	public  Shape shape;
	private Shape nextShape;
	private EndGame endGame;

	// ------------------
	public Game() {
		game_over = false;
		chrono = System.currentTimeMillis();
		lines = new ArrayList<Point>();
		endGame = new EndGame(this);

	}

	// ------------------
	public void setup() {
//    rectMode(CENTER);
		shape = new Shape(10, 10);
		nextShape = new Shape(Launcher.WIDTH - 70, 50);
	}

	// ------------------
	public void update() {
		if (game_over)
			return;
		makeStepDown();
		if (!shape.canMoveDown(lines) || nextOnFloor()) {
			shape.move(0, -20);
			lockCurrentShape();
			switchToNextShape();
			checkUpdateScore();
			checkGameOver();
		}
	}

	// ------------------
	public void draw(Graphics2D g) {
		displayGrid(g);
		shape.draw(g);
		drawLines(g);
		displaySideBoard(g);
//		displayGameOver(g);
	}

	// ------------------
	void makeStepDown() {
		if ((System.currentTimeMillis() - chrono) > timeLevel) {
			chrono = System.currentTimeMillis();
			shape.move(0, 20);
		}
	}

	// ------------------
	boolean nextOnFloor() {
		return (!(shape.highestY() < Launcher.HEIGHT));
	}

	// ------------------
	void lockCurrentShape() {
		lines.addAll(shape.getRealCoords());
	}

	// ------------------
	void switchToNextShape() {
		rotIndex = 0;
		chrono = System.currentTimeMillis();
		shape = nextShape;
		shape.setXoffset(10);
		shape.setYoffset(10);
		nextShape = new Shape(Launcher.WIDTH - 70, 50);
	}

	// ------------------
	void checkUpdateScore() {
		int row = Launcher.HEIGHT - 10;
//		Iterator<Point> it = lines.iterator();
		int count;
		do {
			count = 0;
			for (Iterator<Point> it = lines.listIterator(); it.hasNext();) {
				Point ln = it.next();
				if (ln.y == row)
					count++;
				if (count == 10) {
					count = 0;
					removeLine(row);
					it = lines.listIterator(); // reset iterator cause lines were modified
												// https://stackoverflow.com/a/13689311
					score += 20;
					if (score > points_per_level * level) {
						score = 0;
						level += 1;
					}
				}
			}
			row -= 20;
		} while (row > 50);
	}

	// ------------------
	void removeLine(int row) {

		Iterator<Point> itr = lines.iterator();
		while (itr.hasNext()) {
			Point ln = itr.next();
			if (ln.y == row) {
				itr.remove();
			}
		}

		// lines.removeIf(p -> p.y == row); // Java 8

		for (Point ln : lines) {
			if (ln.y < row) {
				ln.y += 20;
			}
		}
	}

	// ------------------
	void checkGameOver() {
		for (Point ln : lines) {
			if (ln.y < 60) {
				State.setState(endGame);;
				return;
			}
		}
	}

	// ------------------
	public void displayGrid(Graphics2D g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, 200, Launcher.HEIGHT);
		g.setColor(new Color(50, 50, 50));
		Stroke stroke1 = new BasicStroke(0.25f);
		g.setStroke(stroke1);

		for (int y = 10; y < Launcher.HEIGHT; y += 20) {
			for (int x = 10; x < Launcher.WIDTH - 100; x += 20) {
				g.drawRect(x - 10, y - 10, 20, 20);
			}
		}
	}

	// ------------------
	public  void drawLines(Graphics2D g) {
		for (Point ln : lines) {
			g.setColor(ln.c);
			g.fillRect(ln.x - 10, ln.y - 10, 20, 20);
		}
	}

	// ------------------
	public void displaySideBoard(Graphics2D g) {
		g.setColor(new Color(34, 34, 34));
		g.fillRect(Launcher.WIDTH - 50 - 50, 0, 100, Launcher.HEIGHT);
		nextShape.draw(g);
		g.setColor(new Color(130, 130, 130));
		text(g, "Level " + level, Launcher.WIDTH - 80, Launcher.HEIGHT / 2);
		text(g, "Score " + score, Launcher.WIDTH - 80, Launcher.HEIGHT / 2 + 50);
	}

//	// ------------------
//	void displayGameOver(Graphics2D g) {
//		if (game_over) {
//			g.setColor(new Color(255, 100, 100));
//			text(g, "Game Over", Launcher.WIDTH - 90, Launcher.HEIGHT / 2 + 150);
//		}
//	}

	// ------------------
	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();

		float a = 0;
		float b = 0;
		if (key == KeyEvent.VK_RIGHT) {
			a = shape.getXoffset();
			if (a < Launcher.WIDTH - 100 - 20 * shape.getNbX()) {
				shape.move(20, 0);
			}
		}

		if (key == KeyEvent.VK_LEFT) {
			a = shape.getXoffset();
			if (a > 20) {
				shape.move(-20, 0);
			}
		}

		if (key == KeyEvent.VK_DOWN) {
			b = shape.getYoffset();
			if (b < Launcher.HEIGHT - 20) {
				shape.move(0, 20);
			}
		}

		if (key == KeyEvent.VK_UP) {
			int save = rotIndex;
			rotIndex += 1;
			rotIndex = rotIndex % 4;
			// disable rotation to prevent overflow
			shape.rotate(rotIndex);
			if (shape.xOutRightSide(Launcher.WIDTH - 100)) {
				rotIndex = save;
				shape.rotate(rotIndex);
			}
		}
	}

	public void text(Graphics2D g, String txt, int x, int y) {
		int fontSize = 15;
		Font f = new Font("Comic Sans MS", Font.CENTER_BASELINE, fontSize);
		g.setFont(f);
		g.drawString(txt, x, y);
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
