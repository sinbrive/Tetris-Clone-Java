package com.sinbrive.game;

import java.awt.Color;

public class Point {
	int x, y;
	Color c;

	// ------------------
	Point(int x, int y) {
		this.x = x;
		this.y = y;
		this.c = Color.BLACK;
	}

	// ------------------
	Point(int x, int y, Color c) {
		this.x = x;
		this.y = y;
		this.c = c;
	}
}
