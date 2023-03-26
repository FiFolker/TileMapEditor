package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import settings.Config;

public class TileEditorPanel extends JPanel implements Runnable{

	Thread drawThread;
	MouseHandler mouseH = new MouseHandler();
	TopMenuBar menuB = new TopMenuBar(this);
	TreeTiles TreeT = new TreeTiles(this);
	public JTree jt = new JTree(TreeT.listOfTiles);
	public int indexOfSelectedNode = 0;
		

	// FPS
	int FPS = 25;

	public TileEditorPanel(){
		this.setFocusable(true);
		jt.setPreferredSize(new Dimension(175, 720));
		jt.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e) {
				indexOfSelectedNode = jt.getRowForPath(jt.getSelectionPath()) - 1;
				if(indexOfSelectedNode <= -1){
					indexOfSelectedNode = 0;
				}
			}
			
		});
		this.add(jt);
		this.add(new JScrollPane(jt));
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
			}
			
			if(timer >= 1000000000){
				timer = 0;
			}
			
			

		}

	}

	public void update(){
		if(TopMenuBar.tileM != null && MainFrame.mouseH.clicked){
			int col = MainFrame.mouseH.x / Config.tileSize;
			int row = MainFrame.mouseH.y / Config.tileSize;
			try{
				TopMenuBar.tileM.map[col][row - 3] = indexOfSelectedNode;
			}catch(Exception e){
				System.out.println("Problème dans le set des tiles dans la map[][] " + e);
			}
			
		}
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;

		if(TopMenuBar.tileM != null && !TopMenuBar.tileM.tiles.isEmpty()){
			for(int x = 0; x<TopMenuBar.tileM.map.length ; x++){
				for(int y = 0; y<TopMenuBar.tileM.map[x].length ; y++){
					g2.drawImage(TopMenuBar.tileM.tiles.get(TopMenuBar.tileM.map[x][y]).image, x*Config.tileSize, y*Config.tileSize, Config.tileSize, Config.tileSize, null);
				}
			}
		}
		
		

		for(int x = 0; x < Config.nbCol; x++){
			for(int y = 0; y < Config.nbRow; y++){
				g2.drawRect(x*Config.tileSize,y*Config.tileSize, Config.tileSize, Config.tileSize);
			}
		}
	}
	
}
