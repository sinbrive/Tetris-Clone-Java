package com.game;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import com.states.GameState;
import com.states.StartState;
import com.states.State;

public class Launcher extends Canvas implements Runnable {

//	private Timer timer;
	public Game game;

	public final static int WIDTH = 300;
	public final static int HEIGHT = 400;

	public boolean running = false;
	private Thread gameThread; // thread where the game is updated AND drawn (single thread game)
	public JFrame frame;

	
	public State gameState;
	public State startState;
	
	// ------------------
	public Launcher() {

		frame = new JFrame();

		reset();

	}

	// ------------------
	private void reset() {

		Dimension size = new Dimension(WIDTH, HEIGHT);
		setPreferredSize(size);
		
		game = new Game();
		
		gameState = new GameState(game);
		
		startState = new StartState(game);
		
		State.setState(gameState);
		
		State.getState().setup();
		

		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				game.keyReleased(e);
			}

			@Override
			public void keyPressed(KeyEvent e) {
				game.keyPressed(e);
			}
		});

		setFocusable(true); // needed https://stackoverflow.com/a/8498296

	}

	// ------------------
	@Override
	public void run() {
		this.requestFocus();
		int fps = 60;
		double timePerTick = 1000000000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		
		while (running) {
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			lastTime = now;

			if (delta >= 1) {
				State.getState().update();
				this.draw();
				delta--;
			}
		}

		stop();

	}

	// ------------------
	private void draw() {

		BufferStrategy buffer = this.getBufferStrategy();
		if (buffer == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics2D g = (Graphics2D) buffer.getDrawGraphics();

		State.getState().draw(g);

		g.dispose();
		buffer.show();

	}

	// ------------------
	public synchronized void start() {
		gameThread = new Thread(this);
		gameThread.start();
		running = true;
	}

	// ------------------
	public void stop() {
		try {
			gameThread.join();
			running = false;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// ------------------
	public static void main(String[] args) {

		Launcher tetris = new Launcher();
		tetris.frame.setResizable(false);
		tetris.frame.setTitle("Tetris Game");
		tetris.frame.add(tetris);
		tetris.frame.pack();
		tetris.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tetris.frame.setLocationRelativeTo(null);
		tetris.frame.setVisible(true);

		tetris.start();

	}
}
