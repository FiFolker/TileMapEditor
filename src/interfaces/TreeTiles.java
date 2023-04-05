package interfaces;


import javax.swing.tree.DefaultMutableTreeNode;

import main.TileEditorPanel;
import tiles.Tile;

public class TreeTiles{

	public DefaultMutableTreeNode listOfTiles = new DefaultMutableTreeNode("Listes des Tiles");
	public TileEditorPanel TE;

	public TreeTiles(TileEditorPanel TE){
		this.TE = TE;
	}

	public void addTilesToJTree(){
		if(TopMenuBar.tileM != null){
			this.listOfTiles.removeAllChildren();
			//int i = 0;
			TE.indexOfSelectedNode = 0;
			for(Tile t : TopMenuBar.tileM.tiles){
				this.listOfTiles.add(new DefaultMutableTreeNode(t.name)); // i + " - " +  si substrint dans TileManager
				//i++;
			}
		}
	}
	
}
