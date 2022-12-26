package com.sinbrive.states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import com.sinbrive.game.Display;
import com.sinbrive.game.Game;
import com.sinbrive.game.Launcher;

public class MenuState extends State {

	private Display display;

	public MenuState() {
		display = new Display();
	}

	@Override
	public void setup() {
		
	}

	@Override
	public void update() {

	}

	@Override
	public void draw(Graphics g) {
		display.renderBoard(g);
		display.renderSide(g, Game.level, Game.score);
		g.setColor(new Color(255, 100, 100));
			  
		display.text(g, "left and right arrow for horizontal movement.", 30, 100, 12);
		display.text(g, "down arrow key to move down more quickly,", 30, 120, 12);
		display.text(g, "up arrow for  rotations.", 30, 140, 12);
		display.text(g, "Pressing 'q' quits the game.", 30, 160, 12);
		display.text(g, "Pressing 'p' pauses the game.", 30, 180, 12);
		display.text(g, "Enter to start the game", 30, 250, 12);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_ENTER) {
			State.setState(Launcher.playingState);
			State.getState().setup();
		}
	}

}
