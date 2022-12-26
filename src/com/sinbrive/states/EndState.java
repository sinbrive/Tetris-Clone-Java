package com.sinbrive.states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import com.sinbrive.game.Display;
import com.sinbrive.game.Game;
import com.sinbrive.game.Launcher;

public class EndState extends State {

	public Display display;

	public EndState() {
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
		display.text(g, "Game Over", Launcher.WIDTH - 250, Launcher.HEIGHT / 2, 12);
		display.text(g, "ENTER to re-start", 50, Launcher.HEIGHT/2 + 100, 15);
		display.text(g, "'q' to quit", 50, Launcher.HEIGHT /2 + 150, 12);
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
