package com.states;

import java.awt.Graphics2D;

import com.game.Game;

public class GameState extends State{
	
	public Game game;
	
	public GameState(Game game){
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
	public void draw(Graphics2D g) {
		game.draw(g);
		
	}



}
