package main;


import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import tiles.Tile;

public class TreeTiles{

	public DefaultMutableTreeNode framework = new DefaultMutableTreeNode("Framework");

	public void addTilesToJTree(){
		if(MenuBar.tileM != null){
			for(Tile t : MenuBar.tileM.tiles){
				this.framework.add(new DefaultMutableTreeNode(t.name));
			}
		}
	}
	
}
