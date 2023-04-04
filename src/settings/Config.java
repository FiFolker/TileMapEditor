package settings;

import java.io.File;

public class Config {

    public static int tileSize = 24;
    public static int nbCol = 21, nbRow = 21;
	public static int mapWidth = tileSize*nbCol, mapHeight = tileSize*nbRow;
    public static int gridWidht = tileSize*nbCol, gridHeight = tileSize*nbRow;
	public static File directoryOfTiles;
    public static String delimiter = " ";
    public static String extension = ".txt";

    public static void calculMapSize(){
        Config.mapWidth = tileSize*nbCol;
        Config.mapHeight = tileSize*nbRow;
    }
    
}
