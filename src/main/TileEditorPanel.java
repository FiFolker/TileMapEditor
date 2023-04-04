package main;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import settings.Config;

public class TileEditorPanel extends JPanel implements Runnable{

	Thread drawThread;
	public TopMenuBar menuB = new TopMenuBar(this);
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

		topLeftCorner = jt.getPreferredSize().width+20;
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
		if(MainFrame.mouseH.leftClicked){
			this.requestFocusInWindow();
		}
		if(mouseCasePos.isInGrid() && menuB.configFrameState == false || this.hasFocus()){
			if(TopMenuBar.tileM != null && MainFrame.mouseH.leftClicked){
				TopMenuBar.tileM.map[mouseCasePos.column][mouseCasePos.line] = indexOfSelectedNode;
			}
			
			if(MainFrame.mouseH.wheel > 0 && keyH.ctrlPressed && Config.gridWidth/Config.tileSize < Config.nbCol && Config.gridHeight/Config.tileSize < Config.nbRow){
				Config.tileSize --;
				MainFrame.mouseH.wheel = 0;
			}
			else if(MainFrame.mouseH.wheel < 0 && keyH.ctrlPressed && Config.tileSize+1 <= 64){
				Config.tileSize ++;
				MainFrame.mouseH.wheel = 0;
			}
			this.requestFocusInWindow();
		}

		if(keyH.save){
			menuB.saveMap();
			keyH.save = false;
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
		if(keyH.moveDown && !keyH.ctrlPressed){
			System.out.println("Déplacement Bas !");
		}
		
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;

		for(int x = 0; x < Config.nbCol ; x++){
			for(int y = 0; y < Config.nbRow; y++){	
				int posX = topLeftCorner+Config.tileSize*x;
				int posY = topCorner+Config.tileSize*y;
				if(posX >= topLeftCorner && posX <= Config.gridWidth + topLeftCorner && posY >= topCorner && posY <= Config.gridHeight + topCorner){
					if(TopMenuBar.tileM != null && !TopMenuBar.tileM.tiles.isEmpty()){
						int tilesValue = TopMenuBar.tileM.map[x][y];
						g2.drawImage(TopMenuBar.tileM.tiles.get(tilesValue).image, posX, posY, Config.tileSize, Config.tileSize, null);
					}
					if(keyH.gridState){
						g2.drawRect(posX, posY, Config.tileSize, Config.tileSize);
					}	
				}
				
			}
		}
		if(!keyH.gridState){
			g2.drawRect(topLeftCorner, topCorner, Config.gridWidth, Config.gridHeight);
		}
		g2.drawString("Colonne : " + mouseCasePos.column, 200, 650);
		g2.drawString("Ligne : " + mouseCasePos.line, 275, 650);
	}
	
	public void convertMousePosToBoardPos() {
		int x = (MainFrame.mouseH.x - topLeftCorner - 10) / Config.tileSize; // 10 = marge du bord
		int y = (MainFrame.mouseH.y - topCorner - 56) / Config.tileSize; // 50 = marge du haut
		
		mouseCasePos.line = y;
		mouseCasePos.column = x;
		if (x < 0 || x > Config.nbCol || y < 0 || y > Config.nbRow) {
			mouseCasePos.line = -1;
			mouseCasePos.column = -1;
		}
		
		
	}

}
