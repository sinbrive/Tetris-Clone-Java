package com.sinbrive.states;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import com.sinbrive.game.Game;

public class PlayingState extends State {

	private Game game;

	public PlayingState(Game game) {
		this.game = game;
	}

	@Override
	public void setup() {
		game.setup();

	}

	@Override
	public void update() {
		game.update();
	}

	@Override
	public void draw(Graphics g) {
		game.draw(g);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		game.keyPressed(e);

	}

	@Override
	public void keyPressed(KeyEvent e) {
		game.keyReleased(e);

	}

}
