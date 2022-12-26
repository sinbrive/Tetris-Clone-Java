package com.sinbrive.states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import com.sinbrive.game.Display;
import com.sinbrive.game.Game;
import com.sinbrive.game.Launcher;

public class PauseState extends State {

	private Display display;
	private Game game;

	public PauseState(Game game) {
		this.game = game;
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
		game.draw(g);
		g.setColor(new Color(100, 100, 100));
		display.text(g, "Enter to resume", Launcher.WIDTH - 250, Launcher.HEIGHT / 2 + 150, 15);
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
		}
	}

}
