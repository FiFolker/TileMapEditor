package main;


import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import tiles.Tile;

public class TreeTiles{

	public DefaultMutableTreeNode listOfTiles = new DefaultMutableTreeNode("Listes des Tiles");

	public void addTilesToJTree(){
		if(MenuBar.tileM != null){
			for(Tile t : MenuBar.tileM.tiles){
				this.listOfTiles.add(new DefaultMutableTreeNode(t.name));
			}
		}
	}
	
}
