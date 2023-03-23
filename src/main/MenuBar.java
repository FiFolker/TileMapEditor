package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import settings.ConfigFrame;

public class MenuBar extends JMenuBar{
	public JMenu fileMenu, tilesMenu, sortTilesMenu;
	public JMenuItem loadMapItem, saveMapItem, leaveItem, loadTilesFolderItem, loadTilesMapItem, sortByNumberItem, sortByOriginalItem, helpItem, configItem;

	public MenuBar(){
		fileMenu = new JMenu("Fichiers");

		loadMapItem = new JMenuItem("Charger Map");
		saveMapItem = new JMenuItem("Sauvegarder Map");
		configItem = new JMenuItem("Paramètres");
		helpItem = new JMenuItem("Aide");
		leaveItem = new JMenuItem("Quitter");

		fileMenu.add(loadMapItem);
		fileMenu.add(saveMapItem);
		fileMenu.add(configItem);
		fileMenu.add(helpItem);
		fileMenu.add(leaveItem);


		tilesMenu = new JMenu("Tiles");

		loadTilesFolderItem = new JMenuItem("Chargers Dossier de Tiles");
		loadTilesMapItem = new JMenuItem("Charger TilesMap");

		tilesMenu.add(loadTilesFolderItem);
		tilesMenu.add(loadTilesMapItem);


		sortTilesMenu = new JMenu("Trier Tiles");

		tilesMenu.add(sortTilesMenu);

		sortByNumberItem = new JMenuItem("Trier par numéro");
		sortByOriginalItem = new JMenuItem("Trier par sens d'apparition dans le dossier");

		sortTilesMenu.add(sortByNumberItem);
		sortTilesMenu.add(sortByOriginalItem);


		addActionListenerToJMenuItems();

		this.add(fileMenu);
		this.add(tilesMenu);
		

	}

	private void addActionListenerToJMenuItems(){ // add parameter with JMenuItems, arraylist of Items and add with for 
		loadMapItem.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent evt) { loadMapItemAction(evt); }});
		saveMapItem.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent evt) { saveMapItemAction(evt); }});
		configItem.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent evt) { configItemAction(evt); }});
		helpItem.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent evt) { helpItemAction(evt); }});
		leaveItem.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent evt) { System.exit(0); }});
		loadTilesFolderItem.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent evt) { loadTilesFolderItemAction(evt); }});
		loadTilesMapItem.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent evt) { loadTilesMapItemAction(evt); }});
		sortByNumberItem.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent evt) { sortByNumberItemAction(evt); }});
		sortByOriginalItem.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent evt) { sortByOriginalItemAction(evt); }});

		
		
	}

	protected void helpItemAction(ActionEvent evt) {
	}

	protected void configItemAction(ActionEvent evt) {
		ConfigFrame cF = new ConfigFrame();
	}

	protected void sortByOriginalItemAction(ActionEvent evt) {
	}

	protected void sortByNumberItemAction(ActionEvent evt) {
	}

	protected void loadTilesMapItemAction(ActionEvent evt) {
	}

	protected void loadTilesFolderItemAction(ActionEvent evt) {
	}

	protected void saveMapItemAction(ActionEvent evt) {
	}

	protected void loadMapItemAction(ActionEvent event) {
		
	}



}

