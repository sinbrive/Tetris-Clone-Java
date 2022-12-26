package com.sinbrive.game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Shape extends Tetros {

	private Color[] colrs = { Color.cyan, Color.blue, Color.orange, Color.yellow, Color.green, Color.red,
			Color.magenta };

	int x, y;
	Color c;
	int index, rotIndex;
	final Point[][] pattern;
	Point[] p;

	// ------------------
	Shape(int x, int y) {
		this.x = x;
		this.y = y;
		rotIndex = 0;
		index = (int) (Math.random() * 7);
		pattern = tetrosList[index];
		p = pattern[rotIndex];
		c = colrs[index];
	}

	// ------------------
	int getXoffset() {
		return x;
	}

	// ------------------
	int getYoffset() {
		return y;
	}

	// ------------------
	void setXoffset(int _x) {
		x = _x;
	}

	// ------------------
	void setYoffset(int _y) {
		y = _y;
	}

	// ------------------
	void rotate(int rot) {
		rotIndex = rot;
		p = pattern[rotIndex];
	}

	// ------------------
	Point[] getPattern() {
		return p;
	}

	// ------------------
	Color getColor() {
		return c;
	}

	// ------------------
	void move(int _x, int _y) {
		x += _x;
		y += _y;
	}

	// ------------------
	public void draw(Graphics g) {
		g.setColor(c);
		for (int i = 0; i < p.length; i++) {
			Point s = p[i];
			int a = s.x * 20 + this.x;
			int b = s.y * 20 + this.y;
			g.fillRect(a - 10, b - 10, 20, 20);
		}
	}

	// ------------------
	int maxY() {
		int largest = 0;
		for (int i = 0; i < p.length; i++) {
			if (largest < p[i].y) {
				largest = p[i].y;
			}
		}
		return largest;
	}

	// ------------------
	int getNbX() {
		int largest = 0;
		for (int i = 0; i < p.length; i++) {
			if (largest < p[i].x) {
				largest = p[i].x;
			}
		}
		return largest + 1;
	}

	// ------------------
	boolean xOutRightSide(int limit) {
		int l;
		for (int i = 0; i < p.length; i++) {
			Point s = p[i];
			l = s.x * 20 + this.x;
			if (l > limit)
				return true;
		}
		return false;
	}

	// ------------------
	boolean canMoveDown(ArrayList<Point> wall) {
		for (Point ln : wall) {
			for (int j = 0; j < 4; j++) {
				int a = p[j].x * 20 + x;
				int b = p[j].y * 20 + y;
				if ((a == ln.x) && (b == ln.y)) {
					return false;
				}
			}
		}
		return true;
	}

	// ------------------
	int highestY() {
		return (y + 20 * maxY());
	}

	// ------------------
	ArrayList<Point> getRealCoords() {
		ArrayList<Point> ret = new ArrayList<Point>();
		Point q;
		int _x = 0;
		int _y = 0;

		for (int i = 0; i < p.length; i++) {
			_x = p[i].x * 20 + this.x;
			_y = p[i].y * 20 + this.y;
			q = new Point(_x, _y, c);
			ret.add(q);
		}
		return ret;
	}
}
