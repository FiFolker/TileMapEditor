package settings;

import java.io.File;

public class Config {

    public static int tileSize = 16;
    public static int nbCol = 21, nbRow = 21;
	public static int mapWidth = tileSize*nbCol, mapHeight = tileSize*nbRow;
	public static File directoryOfTiles;
    public static String delimiter = " ";
    
}
