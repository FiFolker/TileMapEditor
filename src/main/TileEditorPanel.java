package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Objects;

import javax.swing.JPanel;

import controls.KeyHandler;
import interfaces.BrushSelector;
import interfaces.HUD;
import interfaces.TileSelector;
import interfaces.TopMenuBar;
import settings.Config;

public class TileEditorPanel extends JPanel implements Runnable{

	Thread drawThread;
	public TopMenuBar menuB = new TopMenuBar(this);
	public GrilleCoord mouseCasePos;
	public KeyHandler keyH = new KeyHandler();
	HUD hud = new HUD(this);
	public Brush brush = new Brush(this);
	TileSelector TS ;
	BrushSelector BS;

	// Paramètre du plateau
	public int indexOfSelectedTiles = 0;
	int topLeftCorner = 0;
	int topCorner = 0;
	int shiftHorizontal = 0; // + = right - = left
	int shiftVertical = 0; // + = top - = bottom

	ArrayList<int[][]> undo;
	int maxSizeUndo = 60;

	// FPS
	int FPS = 25;

	public TileEditorPanel(){
		this.setFocusable(true);
		this.addKeyListener(keyH);

		setup();
		
	}

	public void setup(){
		mouseCasePos = new GrilleCoord(0, 0, Config.nbCol, Config.nbRow);
		TS = new TileSelector(this, 0, 0);
		BS = new BrushSelector(this, 10, TS.rows*TS.tilePreviewSize + 10);
		undo = new ArrayList<>();
		topLeftCorner = TS.cols*TS.tilePreviewSize+2*TS.tilePreviewSize;
		topCorner = TopMenuBar.sizeOfTopMenuBar.height;
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
		mouseCasePos.convertMousePosToBoardPos(topLeftCorner, topCorner, shiftHorizontal, shiftVertical);
		if(mouseCasePos.isInGrid() && !menuB.configFrameState){
			if(TopMenuBar.tileM != null && MainFrame.mouseH.leftClicked){
				if(MainFrame.mouseH.leftClickedOnceTime){
					if(undo.size() >= maxSizeUndo){
						undo.remove(0);
					}
					undo.add(copyArray2D( TopMenuBar.tileM.map));
					MainFrame.mouseH.leftClickedOnceTime = false;
				}
				brush.drawTile();
			}
			if(MainFrame.mouseH.wheel > 0 && keyH.ctrlPressed && Config.gridWidth/Config.tileSize+shiftHorizontal < Config.nbCol-1 && Config.gridHeight/Config.tileSize+shiftVertical < Config.nbRow-1){
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
			hud.saved = true;
			keyH.save = false;
		}
		if(keyH.newMap){
			menuB.newMap();
			shiftHorizontal = 0;
			shiftVertical = 0;
			keyH.newMap = false;
		}
		if(keyH.undo && TopMenuBar.tileM != null && !undo.isEmpty()){
			int[][] undoMap = undo.get(undo.size()-1);
			TopMenuBar.tileM.map = copyArray2D(undoMap);
			undo.remove(undoMap);
			keyH.undo = false;
		}
		
		if(TopMenuBar.tileM != null){
			if(keyH.moveUp ){
				shiftVertical --;
				keyH.moveUp = false;
			}
			if(keyH.moveRight && (Config.gridWidth/Config.tileSize) + shiftHorizontal+1 < Config.nbCol){
				shiftHorizontal ++;
				keyH.moveRight = false;
			}
			if(keyH.moveLeft){
				shiftHorizontal --;
				keyH.moveLeft = false;
			}
			if(keyH.moveDown && !keyH.ctrlPressed && (Config.gridHeight/Config.tileSize) + shiftVertical+1 < Config.nbRow){
				shiftVertical ++;
				keyH.moveDown = false;
			}
		}
		TS.update();
		BS.update();
		hud.update();
	}

	public void printArray(int[][] array){
		System.out.println("--------------------------------------------------------------------------------");
		for(int i=0; i<array.length;i++){
			for(int j=0; j<array[i].length; j++){
				System.out.print(array[i][j] + " ");
			}
			System.out.println();
		}
	}

	public int[][] copyArray2D(int[][] arrayToCopy){
		int[][] newArray = new int[arrayToCopy.length][];
		for(int i=0; i<arrayToCopy.length;i++){
			newArray[i] = arrayToCopy[i].clone();
			
		}
		return newArray;
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;

		for(int x = 0; x < Config.nbCol ; x++){
			for(int y = 0; y < Config.nbRow; y++){	
				int posX = topLeftCorner+Config.tileSize*x;
				int posY = topCorner+Config.tileSize*y;
				if(posX >= topLeftCorner && posX <= Config.gridWidth + topLeftCorner && posY >= topCorner && posY <= Config.gridHeight + topCorner){
					if(TopMenuBar.tileM != null && !TopMenuBar.tileM.tiles.isEmpty()){
						
						if(x+shiftHorizontal < 0){
							shiftHorizontal = 0;
						}
						if(y+shiftVertical<0){
							shiftVertical = 0;
						}

						int tilesValue = TopMenuBar.tileM.map[x+shiftHorizontal][y+shiftVertical];
						
						g2.drawImage(TopMenuBar.tileM.tiles.get(tilesValue).image, posX, posY, Config.tileSize, Config.tileSize, null);
					}
					if(keyH.gridState){
						g2.drawRect(posX, posY, Config.tileSize, Config.tileSize);
					}	
				}
				
			}
		}
		if(keyH.previState && mouseCasePos.isInGrid()){
			int posX = topLeftCorner + (mouseCasePos.column-shiftHorizontal)*Config.tileSize;
			int posY = topCorner + (mouseCasePos.line-shiftVertical)*Config.tileSize;
			if(Objects.equals(Config.theme, MainFrame.lightTheme)){
				g2.setColor(Color.gray);
			}else{
				g2.setColor(Color.black);
			}
			g2.setStroke(new BasicStroke(2f));
			if(TopMenuBar.tileM != null){
				g2.drawImage(TopMenuBar.tileM.tiles.get(indexOfSelectedTiles).image, posX, posY, Config.tileSize, Config.tileSize, null);
			}
			g2.drawRect(posX, posY, Config.tileSize, Config.tileSize);
			
		}

		TS.draw(g2);
		
		BS.draw(g2);

		hud.draw(g2);
		
	}

}
