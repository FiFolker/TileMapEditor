package main;

import java.awt.Dimension;
import java.awt.FlowLayout;
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
	Coordonnees mouseCasePos = new Coordonnees(0, 0);
	public JTree jt = new JTree(TreeT.listOfTiles);


	public int indexOfSelectedNode = 0;
	int topLeftCorner = 0;
	int topCorner = 0;
		

	// FPS
	int FPS = 25;

	public TileEditorPanel(){
		this.setFocusable(true);
		
		jt.setPreferredSize(new Dimension(175, MainFrame.sizeOfWindow.height*2));
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
		JScrollPane scroll = new JScrollPane(jt);
		scroll.setPreferredSize(new Dimension(175, MainFrame.sizeOfWindow.height - TopMenuBar.sizeOfTopMenuBar.height*2));
		this.add(scroll);
		this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

		topLeftCorner = jt.getPreferredSize().width+10;
		topCorner = TopMenuBar.sizeOfTopMenuBar.height;
		System.out.println(topLeftCorner);
		System.out.println(topCorner);
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
		convertMousePosToBoardPos();
		if(TopMenuBar.tileM != null && MainFrame.mouseH.clicked){
			
			if(mouseCasePos.isInGrid()){

				TopMenuBar.tileM.map[mouseCasePos.line][mouseCasePos.column] = indexOfSelectedNode;
			}
		}
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;

		int yTemp = topCorner;
		for(int x = 0; x < Config.nbCol; x++){
			int xTemp = topLeftCorner;
			for(int y = 0; y < Config.nbRow; y++){	
				if(TopMenuBar.tileM != null && !TopMenuBar.tileM.tiles.isEmpty()){
					g2.drawImage(TopMenuBar.tileM.tiles.get(TopMenuBar.tileM.map[x][y]).image, xTemp+Config.tileSize, yTemp+Config.tileSize, Config.tileSize, Config.tileSize, null);
				}
				g2.drawRect(xTemp+Config.tileSize, yTemp+Config.tileSize, Config.tileSize, Config.tileSize);
				xTemp += Config.tileSize;
			}
			yTemp += Config.tileSize;
		}
	}
	
	public void convertMousePosToBoardPos() {
        int x = (MainFrame.mouseH.x - 204) / Config.tileSize;
        int y = (MainFrame.mouseH.y - 92) / Config.tileSize;
        
        mouseCasePos.line = y;
        mouseCasePos.column = x;
        
        if (MainFrame.mouseH.x < 204 -10 || MainFrame.mouseH.y < 92 -10) {
            mouseCasePos.line = -1;
            mouseCasePos.column = -1;
        }
    }

}
