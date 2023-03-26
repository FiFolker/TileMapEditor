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
        if(Config.directoryOfTiles != null){
            filesOfTiles = Config.directoryOfTiles.listFiles();
            getTileImage();
        }
        
    }
    
    private void getTileImage() {
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
