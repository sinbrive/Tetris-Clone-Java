package com.states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import com.game.Game;
import com.game.Launcher;

public class StartState extends State {

	private Game game;

	public StartState(Game game) {
		this.game = game;

	}

	@Override
	public void setup() {
		game.setup();
	}

	@Override
	public void update() {

	}

	@Override
	public void draw(Graphics2D g) {
		game.draw(g);
		g.setColor(new Color(255, 100, 100));
		game.text(g, "Space to start", Launcher.WIDTH - 250, Launcher.HEIGHT / 2 + 150);

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_SPACE) {
			State.setState(Launcher.playingState);
		}
	}

}
