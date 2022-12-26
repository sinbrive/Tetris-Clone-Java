package com.sinbrive.states;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

public abstract class State {

	private static State currentState = null;

	public static void setState(State state) {
		currentState = state;
	}

	public static State getState() {
		return currentState;
	}

	// CLASS
	public abstract void setup();

	public abstract void update();

	public abstract void draw(Graphics g);

	public abstract void keyReleased(KeyEvent e);

	public abstract void keyPressed(KeyEvent e);

}