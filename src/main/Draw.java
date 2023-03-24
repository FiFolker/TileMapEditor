package main;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import settings.Config;

public class Draw extends JPanel implements Runnable{

	Thread drawThread;
	MouseHandler mouseH = new MouseHandler();
	MenuBar menuB = new MenuBar();

	// FPS
	int FPS = 25;

	public void Draw(){
		this.setFocusable(true);
	}


	public void startGameThread(){
		drawThread = new Thread(this);
		drawThread.start();
	}


	@Override
	public void run() {
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long timer = 0;
		int drawCount = 0;
		long currentTime = 0;
		long lastTime = System.nanoTime();

		while(drawThread != null){
			
			currentTime = System.nanoTime();

			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;

			if(delta >= 1){
				update();
				repaint();
				delta --;
				drawCount ++;
			}
			
			if(timer >= 1000000000){
				System.out.println(drawCount);
				timer = 0;
				drawCount = 0;
			}
			
			

		}

	}

	public void update(){
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;

		if(mouseH.clicked){
			g2.drawImage(menuB.tileM.tiles.get(0).image, mouseH.x, mouseH.y, null);
			System.out.println("tryDraw");
		}

		for(int x = 0; x < Config.nbCol; x++){
			for(int y = 0; y < Config.nbRow; y++){
				g2.drawRect(x*Config.tileSize,y*Config.tileSize, Config.tileSize, Config.tileSize);
			}
		}

		

	}
	
}
