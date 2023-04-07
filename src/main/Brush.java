package main;

import interfaces.TopMenuBar;
import settings.Config;

public class Brush {

	public final String defaultBrush = "defaultBrush", repaintAll = "repaintAll", repaintAllOccurence = "repaintAllOccurence", erase = "erase";
	public String currentBrush;
	TileEditorPanel TE;

	public Brush(TileEditorPanel TE){
		this.TE = TE;
		currentBrush = defaultBrush;

	}

	public void changeBrush(String brush){
		currentBrush = brush;
	}

	public void drawTile(){
		switch(currentBrush){
			case defaultBrush:
				try{
					TopMenuBar.tileM.map[TE.mouseCasePos.column][TE.mouseCasePos.line] = TE.indexOfSelectedTiles;
				}catch(Exception e){
					System.out.println("Erreur dans la pose du tiles : " + e);
				}
				break;
			case repaintAll:
				repaintAllBrush();
				break;
			case repaintAllOccurence:
				repaintAllOccurenceBrush();
				break;
			case erase:
				eraseBrush();
				break;
		}
		
	}

	public void eraseBrush(){
		int colVisible = Config.gridWidth/Config.tileSize;
		int rowVisible = Config.gridWidth/Config.tileSize;
		if(rowVisible < Config.nbRow){
			rowVisible ++;
		}
		if(colVisible < Config.nbCol){
			colVisible ++;
		}
		for(int i=0; i<colVisible; i++){
			for(int j=0; j<rowVisible; j++){
				TopMenuBar.tileM.map[i+TE.shiftHorizontal][j+TE.shiftVertical] = 0;
			}
		}
	}

	public void repaintAllBrush(){
		for(int i=0; i<TopMenuBar.tileM.map.length; i++){
			for(int j=0; j<TopMenuBar.tileM.map[i].length; j++){
				TopMenuBar.tileM.map[i][j] = TE.indexOfSelectedTiles;
			}
		}
	}

	public void repaintAllOccurenceBrush(){
		int occurenceToReplace = TopMenuBar.tileM.map[TE.mouseCasePos.column][TE.mouseCasePos.line];
		for(int i=0; i<TopMenuBar.tileM.map.length; i++){
			for(int j=0; j<TopMenuBar.tileM.map[i].length; j++){
				if(occurenceToReplace == TopMenuBar.tileM.map[i][j]){
					TopMenuBar.tileM.map[i][j] = TE.indexOfSelectedTiles;
				}
				
			}
		}
	}
}
