package interfaces;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import main.GrilleCoord;
import main.MainFrame;
import main.TileEditorPanel;
import settings.Config;

public class TileSelector {


	// Attributs
	TileEditorPanel TE;
	int leftPos,topPos;
	public final int tilePreviewSize = 32;
	public final int cols = 4;
	public final int rows = 12;

	// Variables personnels
	GrilleCoord mousePos;
	private int index = 0;
	private HashMap<String, Button> listOfButtons;
	private Rectangle upButton;
	private Rectangle downButton;
	private Polygon upTriangle;
	private Polygon downTriangle;
	private int shiftVertical = 0;

	public TileSelector(TileEditorPanel TE, int nLeftPos, int nTopPos){
		this.TE = TE;
		this.leftPos = nLeftPos;
		this.topPos = nTopPos;
		
		setup();

		listOfButtons.put("upButton", new Button(upButton, upTriangle));
		listOfButtons.put("downButton", new Button(downButton, downTriangle));
	}

	public void setup(){
		listOfButtons = new HashMap<>();
		mousePos = new GrilleCoord(0, 0, cols, rows, tilePreviewSize);
		upButton = new Rectangle(leftPos + cols*tilePreviewSize + 5, topPos, tilePreviewSize, tilePreviewSize);
		downButton = new Rectangle(leftPos + cols*tilePreviewSize + 5, topPos + tilePreviewSize + 2, tilePreviewSize, tilePreviewSize);
		upTriangle = new Polygon(new int[]{upButton.x, upButton.x+upButton.width/2, upButton.x+upButton.width}, new int[]{upButton.y+upButton.height, upButton.y, upButton.y+upButton.height}, 3);
		downTriangle = new Polygon(new int[]{downButton.x, downButton.x+downButton.width/2, downButton.x+downButton.width}, new int[]{downButton.y, downButton.y+downButton.height, downButton.y}, 3);
	}

	public void update(){
		mousePos.convertMousePosToBoardPos(leftPos, topPos, shiftVertical);
		if(TopMenuBar.tileM != null){
			if( mousePos.isInGrid() && MainFrame.mouseH.leftClickedOnceTime){
				int numOfTiles = mousePos.line * cols + mousePos.column;
				if(numOfTiles < TopMenuBar.tileM.tiles.size()){
					TE.indexOfSelectedTiles =  numOfTiles;
				}	
			}
			
			if(listOfButtons.get("upButton").isClicked() && shiftVertical > 0){
				shiftVertical -= cols;
				MainFrame.mouseH.leftClickedOnceTime = false;
			}
			if(listOfButtons.get("downButton").isClicked()){
				shiftVertical += cols;
				MainFrame.mouseH.leftClickedOnceTime = false;
			}
		}
		

	}

	public void draw(Graphics2D g2){
		g2.setColor(Color.black);
		g2.setStroke(new BasicStroke(0f));
		index = shiftVertical;
		for(int r = 0; r<rows; r++){
			for(int c = 0;c<cols; c++){
				if(TopMenuBar.tileM != null && index < TopMenuBar.tileM.tiles.size()){
					g2.drawImage(TopMenuBar.tileM.tiles.get(index).image, c*tilePreviewSize+leftPos, r*tilePreviewSize+topPos, tilePreviewSize, tilePreviewSize, null);
					index ++;
				}
				g2.drawRect(c*tilePreviewSize+leftPos, r*tilePreviewSize+topPos, tilePreviewSize, tilePreviewSize);
			}
		}
		for(Button b : listOfButtons.values()){
			b.draw(g2);
		}
		if(mousePos.isInGrid()){
			int posX = leftPos + mousePos.column*tilePreviewSize;
			int posY = topPos + (mousePos.line-(shiftVertical/4))*tilePreviewSize;
			if(Objects.equals(Config.theme, MainFrame.lightTheme)){
				g2.setColor(Color.gray);
			}else{
				g2.setColor(Color.black);
			}
			g2.setStroke(new BasicStroke(2f));
			g2.drawRect(posX, posY, tilePreviewSize, tilePreviewSize);
		}
		
	}
	
}
