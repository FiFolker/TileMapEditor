package settings;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.TopMenuBar;
import tiles.TilesManager;

public class ConfigFrame extends JFrame{

	JFrame configWindow;

	JLabel tileSizeInfo = new JLabel("Taille d'un tile : ");
	JTextField tileSizeTextField = new JTextField(Integer.toString(Config.tileSize), 6);

	JLabel nbColInfo = new JLabel("Nombre de colonnes : ");
	JTextField nbColTextField = new JTextField(Integer.toString(Config.nbCol), 6);

	JLabel nbRowInfo = new JLabel("Nombre de lignes : ");
	JTextField nbRowTextField = new JTextField(Integer.toString(Config.nbRow),6);

	JLabel delimiterInfo = new JLabel("Délimiteur : ");
	JTextField delimiterTextField = new JTextField(Config.delimiter,3);

	JButton okButton = new JButton("Valider");
	JButton cancelButton = new JButton("Annuler");
	
	
	public ConfigFrame(){
		configWindow = new JFrame();
		configWindow.setTitle("Configuration");
		configWindow.setResizable(false);
		configWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		try {
			configWindow.setIconImage(ImageIO.read(new File("assets/icons/reglages.png")));
		} catch (IOException e) {
			System.out.println("Erreur dans le load de l'image du réglages");
			e.printStackTrace();
		}
		configWindow.setPreferredSize(new Dimension(640, 480));

		configWindow.add(setConfigPanel());
		setButtonActionListener();

		configWindow.pack();

		configWindow.setLocationRelativeTo(null);
		configWindow.setVisible(true);

	}

	public JPanel setConfigPanel(){
		JPanel configPanel = new JPanel();

		fieldOnlyIntMaxLengthListener(tileSizeTextField, 3);
		fieldOnlyIntMaxLengthListener(nbColTextField, 3);
		fieldOnlyIntMaxLengthListener(nbRowTextField, 3);

		configPanel.add(tileSizeInfo);
		configPanel.add(tileSizeTextField);
		
		configPanel.add(nbColInfo);
		configPanel.add(nbColTextField);

		configPanel.add(nbRowInfo);
		configPanel.add(nbRowTextField);

		configPanel.add(delimiterInfo);
		configPanel.add(delimiterTextField);

		configPanel.add(okButton);
		configPanel.add(cancelButton);

		return configPanel;
	}

	public void setButtonActionListener(){
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int newTileSize = Integer.parseInt(tileSizeTextField.getText());
				int newNbCol = Integer.parseInt(nbColTextField.getText());
				int newNbRow = Integer.parseInt(nbRowTextField.getText());

				Config.tileSize = newTileSize;	
				Config.nbCol = newNbCol;
				Config.nbRow = newNbRow;
				Config.mapHeight = Config.nbRow*Config.tileSize;
				Config.mapWidth = Config.nbCol*Config.tileSize;
				if(TopMenuBar.tileM != null){
					TopMenuBar.tileM.reloadMap();
				}
				
				Config.delimiter = delimiterTextField.getText();
				configWindow.dispose();
			}

		});

		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				configWindow.dispose();
			}

		});
	}



	private void fieldOnlyIntMaxLengthListener(JTextField field, int maxLength) { // faire de manière générale avec un new JTextField pour tous les text field avec paramètre de length voulu
		field.addKeyListener(new KeyAdapter() {
			
			public void keyTyped(KeyEvent ke) {
				if (field.getText().length() >= maxLength) // limit textfield to maxLength characters
				{
					ke.consume();
				}
				if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') {
					field.setEditable(true);
				} else {
					field.setEditable(false);
				}
			}
			@Override
			public void keyPressed(KeyEvent ke) {
				if (ke.getKeyCode() == KeyEvent.VK_BACK_SPACE || ke.getKeyCode() == KeyEvent.VK_DELETE) {
					field.setEditable(true);
				}
				
			}
		});
	}
}
