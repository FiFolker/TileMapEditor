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
	TopMenuBar menuB = new TopMenuBar(this);
	TreeTiles TreeT = new TreeTiles(this);
	Coordonnees mouseCasePos = new Coordonnees(0, 0);
	public JTree jt = new JTree(TreeT.listOfTiles);
	boolean gridInMovement = false;


	public int indexOfSelectedNode = 0;
	int topLeftCorner = 0;
	int topCorner = 0;
	KeyHandler keyH = new KeyHandler();

	// FPS
	int FPS = 25;

	public TileEditorPanel(){
		this.setFocusable(true);
		this.addKeyListener(keyH);
		
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
		if(TopMenuBar.tileM != null && MainFrame.mouseH.leftClicked){
			
			if(mouseCasePos.isInGrid()){

				TopMenuBar.tileM.map[mouseCasePos.line][mouseCasePos.column] = indexOfSelectedNode;
			}
		}

		if(keyH.moveUp){
			System.out.println("Déplacement Haut !");
		}
		if(keyH.moveRight){
			System.out.println("Déplacement Droite !");
		}
		if(keyH.moveLeft){
			System.out.println("Déplacement Gauche !");
		}
		if(keyH.moveDown){
			System.out.println("Déplacement Bas !");
		}
		if(MainFrame.mouseH.wheel > 0 && keyH.ctrlPressed && Config.tileSize >= 8){
			Config.tileSize --;
			MainFrame.mouseH.wheel = 0;
			//Config.calculMapSize();
		}
		else if(MainFrame.mouseH.wheel < 0 && keyH.ctrlPressed && Config.tileSize <= 64){
			Config.tileSize ++;
			MainFrame.mouseH.wheel = 0;
			//Config.calculMapSize();
		}
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;

		for(int x = topLeftCorner; x < Config.gridWidht + topLeftCorner; x+= Config.tileSize){
			for(int y = topCorner; y < Config.gridHeight + topCorner; y+= Config.tileSize){	
				if(TopMenuBar.tileM != null && !TopMenuBar.tileM.tiles.isEmpty()){
					g2.drawImage(TopMenuBar.tileM.tiles.get(TopMenuBar.tileM.map[(x-topLeftCorner)/Config.tileSize][(y-topCorner)/Config.tileSize]).image, x, y, Config.tileSize, Config.tileSize, null);
				}
				g2.drawRect(x, y, Config.tileSize, Config.tileSize);
			}
		}
	}
	
	public void convertMousePosToBoardPos() {
        int x = (MainFrame.mouseH.x - topLeftCorner - 10) / Config.tileSize; // 10 = marge du bord
        int y = (MainFrame.mouseH.y - topCorner - 50) / Config.tileSize; // 50 = marge du haut
        
        mouseCasePos.line = x;
        mouseCasePos.column = y;
        
        if (MainFrame.mouseH.x < topLeftCorner - 10 || MainFrame.mouseH.y < topCorner  -10) {
            mouseCasePos.line = -1;
            mouseCasePos.column = -1;
        }
    }

}
