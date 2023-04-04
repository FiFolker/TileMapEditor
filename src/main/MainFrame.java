package main;
import java.awt.Dimension;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatLightLaf;
public class MainFrame {

	public static MouseHandler mouseH = new MouseHandler();
	public static Dimension sizeOfWindow;

    public static void main(String[] args) throws Exception {
		FlatLightLaf.setup();
		UIManager.setLookAndFeel(new FlatLightLaf());

        JFrame window = new JFrame();

		window.setTitle("Tile Map Editor");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
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
