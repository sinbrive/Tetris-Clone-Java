package com.sinbrive.game;


import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;

import com.sinbrive.states.State;

public class Game {

	private int rotIndex;
	private ArrayList<Point> lines;
	public static int score;
	private int points_per_level = 100;
	public static int level = 1;
	private double chrono;
	private int timeLevel = 1000;
	public Shape shape;
	private Shape nextShape;
	private Display display;

	// ------------------
	public Game() {
		setup();
	}

	// ------------------
	public void setup() {
		chrono = System.currentTimeMillis();
		score = 0;
		level = 1;
		rotIndex = 0;
		lines = new ArrayList<Point>();
		display = new Display();
		shape = new Shape(10, 10);
		nextShape = new Shape(Launcher.WIDTH - 70, 50);
	}

	// ------------------
	public void update() {
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
	public void draw(Graphics g) {
		display.renderBoard(g);
		shape.draw(g);
		drawLines(g);
		display.renderSide(g, level, score);
		nextShape.draw(g);
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

		// lines.removeIf(p -> p.y == row); // Java 8 not used

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
				State.setState(Launcher.endState);
				State.getState().setup();
				return;
			}
		}
	}


	// ------------------
	public void drawLines(Graphics g) {
		for (Point ln : lines) {
			g.setColor(ln.c);
			g.fillRect(ln.x - 10, ln.y - 10, 20, 20);
		}
	}


	// ------------------
	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();

		float a = 0;
		float b = 0;
		
		  
	    if (key==KeyEvent.VK_SPACE) {
	      do {
	        shape.move(0, 20);
	      } while (shape.canMoveDown(lines) && !nextOnFloor());
	      shape.move(0, -20);
	      return;
	    }
	    
		
		
		if (key == KeyEvent.VK_P) {
			State.setState(Launcher.pauseState);
			State.getState().setup();
		}
		
		
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

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
