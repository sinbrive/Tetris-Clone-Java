package com.sinbrive.game;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import com.sinbrive.states.EndState;
import com.sinbrive.states.PlayingState;
import com.sinbrive.states.MenuState;
import com.sinbrive.states.PauseState;
import com.sinbrive.states.State;

public class Launcher extends Canvas implements Runnable {

	public Game game;

	public final static int WIDTH = 300;
	public final static int HEIGHT = 400;

	public boolean running = false;
	private Thread gameThread; // thread where the game is updated AND drawn (single thread game)
	public JFrame frame;

	public static State playingState;
	public static State menuState;
	public static State endState;;
	public static State pauseState;;

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
		
		menuState = new MenuState();
		
		pauseState = new PauseState(game);

		playingState = new PlayingState(game);

		endState = new EndState();
		
		State.setState(menuState);

		State.getState().setup();

		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (State.getState() != null) 
					State.getState().keyReleased(e);
			}

			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_Q) {
					System.exit(0);
				}
				
				if (State.getState() != null) 
					State.getState().keyPressed(e);
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
				if (State.getState() != null) 
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

		Graphics g = buffer.getDrawGraphics();

		if (State.getState() != null) 
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
