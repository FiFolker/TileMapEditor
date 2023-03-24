package main;
import java.awt.Dimension;
import java.awt.Menu;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class MainFrame {


    public static void main(String[] args) throws Exception {
        JFrame window = new JFrame();
		window.setTitle("Tile Map Editor");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setIconImage(ImageIO.read(new File("assets/icons/tiled.png")));
		window.setPreferredSize(new Dimension(1080, 720));

		window.setJMenuBar(new MenuBar());
		window.addMouseListener(new MouseHandler());
		window.addMouseMotionListener(new MouseHandler());

		Draw d = new Draw();
		window.add(d);

		window.pack();

		window.setLocationRelativeTo(null);
		window.setVisible(true);

		d.startGameThread();
    }


}
