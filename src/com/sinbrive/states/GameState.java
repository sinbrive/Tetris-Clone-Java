package com.sinbrive.states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import com.sinbrive.game.Display;
import com.sinbrive.game.Game;
import com.sinbrive.game.Launcher;

public class GameState extends State {

	private Game game;
	private Display display;

	public GameState(Game game) {
		this.game = game;
		display = new Display();
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
	public void draw(Graphics2D g) {
		game.draw(g);
//		g.setColor(new Color(100, 250, 100));
//		display.text(g,"SPACE to pause", Launcher.WIDTH - 90, Launcher.HEIGHT-30, 10);
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
