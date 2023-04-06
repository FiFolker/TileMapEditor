package interfaces;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Objects;

import main.GrilleCoord;
import main.MainFrame;
import main.TileEditorPanel;
import settings.Config;

public class TileSelector {

	TileEditorPanel TE;
	GrilleCoord mousePos;
	int leftPos,topPos;
	public final int tilePreviewSize = 32;
	public final int cols = 4;
	public final int rows = 22;
	int index = 0;

	public TileSelector(TileEditorPanel TE, int nLeftPos, int nTopPos){
		this.TE = TE;
		this.leftPos = nLeftPos;
		this.topPos = nTopPos;
		mousePos = new GrilleCoord(0, 0, cols, rows, tilePreviewSize);
	}

	public void update(){
		mousePos.convertMousePosToBoardPos(leftPos, topPos);
		
		if(TopMenuBar.tileM != null && mousePos.isInGrid() && MainFrame.mouseH.leftClicked){
			TE.indexOfSelectedTiles =  mousePos.line * cols + mousePos.column;
			System.out.println(TE.indexOfSelectedTiles);
		}
		
	}

	public void draw(Graphics2D g2){
		g2.setColor(Color.black);
		g2.setStroke(new BasicStroke(0f));
		index = 0;
		for(int r = 0; r<rows; r++){
			for(int c = 0;c<cols; c++){
				if(TopMenuBar.tileM != null && index < TopMenuBar.tileM.tiles.size()){
					g2.drawImage(TopMenuBar.tileM.tiles.get(index).image, c*tilePreviewSize, r*tilePreviewSize, tilePreviewSize, tilePreviewSize, null);
					index ++;
				}
				g2.drawRect(c*tilePreviewSize, r*tilePreviewSize, tilePreviewSize, tilePreviewSize);
				
			}
		}
		if(mousePos.isInGrid()){
			int posX = leftPos + mousePos.column*tilePreviewSize;
			int posY = topPos + mousePos.line*tilePreviewSize;
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
