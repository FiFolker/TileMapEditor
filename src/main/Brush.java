package main;

import interfaces.TopMenuBar;

public class Brush {

	public final String defaultBrush = "defaultBrush", repaintAll = "repaintAll", repaintAllOccurence = "repaintAllOccurence";
	public String currentBrush;
	TileEditorPanel TE;

	public Brush(TileEditorPanel TE){
		this.TE = TE;
		currentBrush = defaultBrush;

	}


	public void drawTile(){
		switch(currentBrush){
			case defaultBrush:
				try{
					TopMenuBar.tileM.map[TE.mouseCasePos.column][TE.mouseCasePos.line] = TE.indexOfSelectedNode;
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
		}
		
	}

	public void repaintAllBrush(){
		for(int i=0; i<TopMenuBar.tileM.map.length; i++){
			for(int j=0; j<TopMenuBar.tileM.map[i].length; j++){
				TopMenuBar.tileM.map[i][j] = TE.indexOfSelectedNode;
			}
		}
	}

	public void repaintAllOccurenceBrush(){
		int occurenceToReplace = TopMenuBar.tileM.map[TE.mouseCasePos.column][TE.mouseCasePos.line];
		for(int i=0; i<TopMenuBar.tileM.map.length; i++){
			for(int j=0; j<TopMenuBar.tileM.map[i].length; j++){
				if(occurenceToReplace == TopMenuBar.tileM.map[i][j]){
					TopMenuBar.tileM.map[i][j] = TE.indexOfSelectedNode;
				}
				
			}
		}
	}
}
