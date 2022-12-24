package com.states;

import java.awt.Color;
import java.awt.Graphics2D;

import com.game.Launcher;
import com.game.Game;

public class EndGame extends State {

	public Game game;
	
	public EndGame(Game game){
		this.game= game;
	}
	
	@Override
	public void setup() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics2D g) {
		game.draw(g);;
		g.setColor(new Color(255, 100, 100));
		game.text(g, "Game Over", Launcher.WIDTH - 90, Launcher.HEIGHT / 2 + 150);
	}

}
