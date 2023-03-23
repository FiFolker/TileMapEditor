package main;
import java.awt.Dimension;
import java.awt.Menu;

import javax.swing.JFrame;

public class Main {


    public static void main(String[] args) throws Exception {
        JFrame window = new JFrame();
		window.setTitle("Tile Map Editor");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setPreferredSize(new Dimension(1080, 720));

		window.setJMenuBar(new MenuBar());

		window.pack();

		window.setLocationRelativeTo(null);
		window.setVisible(true);
    }


}
