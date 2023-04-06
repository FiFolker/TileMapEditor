package main;
import java.awt.Dimension;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;

import controls.MouseHandler;
import interfaces.TopMenuBar;

import com.formdev.flatlaf.FlatDarkLaf;

import settings.Config;
public class MainFrame {

	public static MouseHandler mouseH = new MouseHandler();
	public static Dimension sizeOfWindow;
	public static final String darculaTheme = "com.formdev.flatlaf.FlatDarculaLaf";
	public static final String darkTheme = "com.formdev.flatlaf.FlatDarkLaf";
	public static final String lightTheme = "com.formdev.flatlaf.FlatIntelliJLaf";
	public static JFrame window;

    public static void main(String[] args) throws Exception {
		FlatIntelliJLaf.setup();
		FlatDarculaLaf.setup();
		FlatDarkLaf.setup();
		try{
			UIManager.setLookAndFeel(Config.theme);
		}catch(Exception e){
			System.out.println("erreur dans le load du theme au mainFrame : " + e);
		}

        window = new JFrame();

		window.setTitle("Tile Map Editor");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);
		window.setIconImage(ImageIO.read(new File("assets/icons/tiled.png")));
		window.setPreferredSize(new Dimension(1080, 720));
		sizeOfWindow = window.getPreferredSize();
		
		TileEditorPanel TileEdit = new TileEditorPanel();
		window.setJMenuBar(new TopMenuBar(TileEdit));
		window.addMouseListener(mouseH);
		window.addMouseMotionListener(mouseH);
		window.addMouseWheelListener(mouseH);
		
		

		window.add(TileEdit);

		window.pack();

		window.setLocationRelativeTo(null);
		window.setVisible(true);

		


		TileEdit.startGameThread();
    }


}
