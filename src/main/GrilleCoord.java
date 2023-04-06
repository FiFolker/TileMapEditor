package main;

import settings.Config;

public class GrilleCoord {

	public int line;
	public int column;
	public int maxColumn;
	public int maxLine;
	public int tileSize;
	

	public GrilleCoord(int numLine, int numCol, int numMaxColumn, int numMaxLine, int nTileSize){
		line = numLine;
		column = numCol;
		maxColumn = numMaxColumn;
		maxLine = numMaxLine;
		tileSize = nTileSize;
	}

	public GrilleCoord(int numLine, int numCol, int numMaxColumn, int numMaxLine){
		line = numLine;
		column = numCol;
		maxColumn = numMaxColumn;
		maxLine = numMaxLine;
	}


	public boolean isInGrid(){
		return line >= 0 && line < maxLine 
		&& column >= 0 && column < maxColumn;
	}

	public void convertMousePosToBoardPos(int leftCorner, int topCorner, int shiftHorizontal, int shiftVertical) {
		int x = (MainFrame.mouseH.x - leftCorner - 10) / Config.tileSize; // 10 = marge du bord
		int y = (MainFrame.mouseH.y - topCorner - 56) / Config.tileSize; // 50 = marge du haut
		
		line = y + shiftVertical;
		column = x + shiftHorizontal;
		if (MainFrame.mouseH.x < leftCorner+10 || x > Config.gridWidth/Config.tileSize || MainFrame.mouseH.y < topCorner+56 || y > Config.gridHeight/Config.tileSize) {
			line = -1;
			column = -1;
		}
	}

	public void convertMousePosToBoardPos(int leftCorner, int topCorner, int shiftVertical) {
		int x = (MainFrame.mouseH.x - leftCorner - 10) / tileSize; // 10 = marge du bord
		int y = (MainFrame.mouseH.y - topCorner - 56) / tileSize; // 50 = marge du haut
		
		line = y + shiftVertical/4;
		column = x;
		if (MainFrame.mouseH.x < leftCorner+10 || x >= maxColumn || MainFrame.mouseH.y < topCorner+56 || y > maxLine) {
			line = -1;
			column = -1;
		}
	}
	

	public void debugView(){
		System.out.println("State isInGrid : " + isInGrid() + 
		"\nColumn : " + column +
		"\nLine : " + line +
		"\nMax Column : " + maxColumn +
		"\nMax Line : " + maxLine 
		);
	}
}
