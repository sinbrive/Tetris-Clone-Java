package com.sinbrive.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class Display {
	
	public Display(){
		
	}
	
	public void renderBoard(Graphics2D g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, 200, Launcher.HEIGHT);
		g.setColor(new Color(50, 50, 50));
		Stroke stroke1 = new BasicStroke(0.25f);
		g.setStroke(stroke1);

		for (int y = 10; y < Launcher.HEIGHT; y += 20) {
			for (int x = 10; x < Launcher.WIDTH - 100; x += 20) {
				g.drawRect(x - 10, y - 10, 20, 20);
			}
		}
	}

	// ------------------
		public void renderSide(Graphics2D g, int level, int score) {
			g.setColor(new Color(34, 34, 34));
			g.fillRect(Launcher.WIDTH - 50 - 50, 0, 100, Launcher.HEIGHT);
			g.setColor(new Color(130, 130, 130));
			text(g, "Level " + level, Launcher.WIDTH - 80, Launcher.HEIGHT / 2, 15);
			text(g, "Score " + score, Launcher.WIDTH - 80, Launcher.HEIGHT / 2 + 50, 15);
		}
	
	
		public void text(Graphics2D g, String txt, int x, int y, int fontSize) {
			Font f = new Font("Comic Sans MS", Font.CENTER_BASELINE, fontSize);
			g.setFont(f);
			g.drawString(txt, x, y);
		}


}
