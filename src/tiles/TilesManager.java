package tiles;

import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import settings.Config;

public class TilesManager {
	public ArrayList<Tile> tiles;
	File[] filesOfTiles;
	public int[][] map;

	public TilesManager() {
		tiles = new ArrayList<>();
		map = new int[Config.nbCol][Config.nbRow];
		getTileImage();
	}

	public TilesManager(int[][] nMap) {
		tiles = new ArrayList<>();
		if(Config.directoryOfTiles != null){
			getTileImage();
		}
		this.map = new int[Config.nbCol][];
		for(int i = 0; i < nMap.length; i++){
    		this.map[i] = nMap[i].clone();
		}
		
		
	}

	/**
	 * Permet de reload suivant la valeur du paramètres
	 * @param who 1 = map, 2 = image et tiles<>
	 */
	public TilesManager(int who) {
		if(who == 1){
			map = new int[Config.nbCol][Config.nbRow];
		}else if(who == 2){
			tiles = new ArrayList<>();
			getTileImage();
		}else{
			System.out.println("Paramètres non définis, 1 ou 2 seulement");
		}
			
	}
	
	public void getTileImage() {
		if(Config.directoryOfTiles != null){
			filesOfTiles = Config.directoryOfTiles.listFiles();
			try{
				for(int i=0; i<filesOfTiles.length; i++){
					tiles.add(new Tile());

					tiles.get(i).image = ImageIO.read(filesOfTiles[i]);

					tiles.get(i).name = filesOfTiles[i].getName().substring(3);
					//tiles.get(i).collision = filesOfTiles[i].getName().contains("#"); A VOIR POUR SET DES COLLISIONS ICI DIRECTEMENT
				}
			}catch(Exception ex){
				System.out.println("Erreur dans le load des Tiles : " + ex);
				ex.printStackTrace();
			}
		}
	}

	public void reloadMap(){
		map = new int[Config.nbCol][Config.nbRow];
	}


}
