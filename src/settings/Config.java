package settings;

import java.io.File;

import javax.swing.LookAndFeel;

import com.formdev.flatlaf.FlatIntelliJLaf;

import main.MainFrame;

public class Config {

    public static int tileSize = 24;
    public static int nbCol = 21, nbRow = 21;
	public static int mapWidth = tileSize*nbCol, mapHeight = tileSize*nbRow;
    public static int gridWidth = tileSize*nbCol, gridHeight = tileSize*nbRow;
	public static File directoryOfTiles;
    public static String delimiter = " ";
    public static String extension = ".txt";
    public static String theme = MainFrame.lightTheme;
    public static String savePath = null;
    public static String nameOfSave = null;

    public static void calculMapSize(){
        Config.mapWidth = tileSize*nbCol;
        Config.mapHeight = tileSize*nbRow;
    }

    public static void resetConfig(){
        tileSize = 24;
        nbCol = 21;
        nbRow = 21;
        calculMapSize();
        directoryOfTiles = null;
        delimiter = " ";
        extension = ".txt";
        savePath = null;
        nameOfSave = null;
    }
    
}
