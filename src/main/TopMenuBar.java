package main;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.util.Objects;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import settings.Config;
import settings.ConfigFrame;
import tiles.TilesManager;

public class TopMenuBar extends JMenuBar{
	public JMenu fileMenu, tilesMenu, sortTilesMenu;
	public JMenuItem loadMapItem, saveMapItem, leaveItem, loadTilesFolderItem, loadTilesMapItem, sortByNumberItem, sortByOriginalItem, helpItem, configItem;

	public static TilesManager tileM;
	public TileEditorPanel TE;

	public TopMenuBar(TileEditorPanel TE){
		this.TE = TE;
		this.setPreferredSize(new Dimension(1080, 25));
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
		JFileChooser chooser = fileChooser("Dossier de Tiles", 1);
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			Config.directoryOfTiles = chooser.getSelectedFile();
			if(TopMenuBar.tileM != null && TopMenuBar.tileM.tiles.isEmpty()){
				TopMenuBar.tileM.getTileImage();
			}else{
				TopMenuBar.tileM = new TilesManager();
			}
			
			TE.TreeT.addTilesToJTree();
			TE.jt.updateUI();
		} else {
			System.out.println("No Selection ");
		}
	}

   

	protected void saveMapItemAction(ActionEvent evt) {
		if(TopMenuBar.tileM == null){
			System.out.println("Il faut avoir fait une map pour pouvoir la save");
		}else{
			String name = JOptionPane.showInputDialog(this, "Le délimiteur du fichier est \""+Config.delimiter+"\" et l'extension est "+Config.extension+"(modifiable dans File>Paramètres) "+
			"\nSous quel nom voulez vous l'enregistrer ?");
			try(FileWriter mapSaveFile = new FileWriter(new File("save/"+name+Config.extension))){
				for(int x = 0; x<TopMenuBar.tileM.map.length ; x++){
					for(int y = 0; y<TopMenuBar.tileM.map[x].length ; y++){
						String tileValue = Integer.toString(TopMenuBar.tileM.map[x][y]) + Config.delimiter;
						/*if(y < TopMenuBar.tileM.map[x].length-1){
							tileValue += Config.delimiter;
						}*/
						mapSaveFile.write(tileValue);
						//mapSaveFile.flush();
					}
					mapSaveFile.write("\n");
				}
				
			}catch(Exception e){
				System.out.println("Erreur dans le save de la map " + e);
			}
		}
	}

	protected void loadMapItemAction(ActionEvent event) {
		JFileChooser chooser = fileChooser("Fichier de map", 2);
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			int[][] mapLoaded = loadMap(chooser.getSelectedFile());
			TopMenuBar.tileM = new TilesManager(mapLoaded);
		} else {
			System.out.println("No Selection ");
		}
	}

	private int[][] loadMap(File currentFile) { // rajouter un showDialog avec demande du délimiteur
		int col = countCol(currentFile);
		int row = countRow(currentFile);

		int[][] mapLoaded = new int[col][row];

		int x;
		int y;
		try(Scanner scan = new Scanner(currentFile)){
			String[] split = scan.nextLine().split(Config.delimiter);
			y = 0;
			
			while(scan.hasNextLine()){
				x = 0;
				for(int i = 0; i<split.length; i++){
					if(!Objects.equals(split[i], Config.delimiter)){
						mapLoaded[x][y] = Integer.parseInt(split[i]);
						x++;
					}
				}
				y++;
				split = scan.nextLine().split(Config.delimiter);
			}
				
		}catch(Exception e){
			System.out.println("Exception catch dans le load d'une map "+ e);
			e.printStackTrace();
		}
		Config.nbCol = col;
		Config.nbRow = row;
		return mapLoaded;
	}

	private int countCol(File currentFile) {
		int col = 0;
		
		try(Scanner scan = new Scanner(currentFile)){
			String[] split = scan.nextLine().split(Config.delimiter);
			for(int i = 0; i<split.length; i++){
				if(!Objects.equals(split[i], Config.delimiter)){
					col ++;
				}
			}
		}catch(Exception e){
			System.out.println("Exception catch dans le compte des colonnes "+ e);
		}
		
		return col;
	}

	private int countRow(File currentFile){
		int row = 0;
		try(Scanner scan = new Scanner(currentFile)){
			while(scan.hasNextLine()){
				row ++;
				scan.nextLine();
			}
		}catch(Exception e){
			System.out.println("Exception catch dans le compte des lignes "+ e);
		}
		return row;
	}

	/**
	 * 
	 * @param nameOfWindow
	 * @param fileSelectionMode 1 = directory, 2 = file
	 * @return JFileChooser with parameters define
	 */
	private JFileChooser fileChooser(String nameOfWindow, int fileSelectionMode){
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("."));
		chooser.setDialogTitle(nameOfWindow);
		if(fileSelectionMode == 1){
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		}else if(fileSelectionMode == 2){
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		}
		chooser.setAcceptAllFileFilterUsed(false);

		return chooser;
	}


}

