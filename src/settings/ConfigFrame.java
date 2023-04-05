package settings;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import interfaces.TopMenuBar;
import main.MainFrame;
import main.TileEditorPanel;

public class ConfigFrame extends JFrame{

	JFrame configWindow;

	JLabel nbColInfo = new JLabel("Nombre de colonnes : ");
	JTextField nbColTextField = new JTextField(Integer.toString(Config.nbCol), 6);

	JLabel nbRowInfo = new JLabel("Nombre de lignes : ");
	JTextField nbRowTextField = new JTextField(Integer.toString(Config.nbRow),6);

	JLabel delimiterInfo = new JLabel("Délimiteur : ");
	JTextField delimiterTextField = new JTextField(Config.delimiter,3);

	JLabel themeInfo = new JLabel("Thème : ");
	JRadioButton lightThemeRadio = new JRadioButton("Thème clair");
	JRadioButton darkThemeRadio = new JRadioButton("Thème sombre");
	JRadioButton darculaThemeRadio = new JRadioButton("Thème Darcula");

	JButton okButton = new JButton("Valider");
	JButton cancelButton = new JButton("Annuler");

	public TileEditorPanel TE;
	
	
	public ConfigFrame(TileEditorPanel nTE){
		this.TE = nTE;
		TE.menuB.configFrameState = true;
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

		configWindow.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				TE.menuB.configFrameState = false;
			}
		});

		switch(Config.theme){
			case MainFrame.lightTheme:
				lightThemeRadio.setSelected(true);
				break;
			case MainFrame.darkTheme:
				darkThemeRadio.setSelected(true);
				break;
			case MainFrame.darculaTheme:
				darculaThemeRadio.setSelected(true);
				break;
		}

		configWindow.add(setConfigPanel());
		setButtonActionListener();
		
		configWindow.pack();

		configWindow.setLocationRelativeTo(null);
		configWindow.setVisible(true);

	}

	public JPanel setConfigPanel(){
		JPanel configPanel = new JPanel();

		fieldOnlyIntMaxLengthListener(nbColTextField, 3);
		fieldOnlyIntMaxLengthListener(nbRowTextField, 3);

		configPanel.add(nbColInfo);
		configPanel.add(nbColTextField);

		configPanel.add(nbRowInfo);
		configPanel.add(nbRowTextField);

		configPanel.add(delimiterInfo);
		configPanel.add(delimiterTextField);

		configPanel.add(themeInfo);
		ButtonGroup bg = new ButtonGroup();
		bg.add(lightThemeRadio);
		bg.add(darkThemeRadio);
		bg.add(darculaThemeRadio);
		configPanel.add(lightThemeRadio);
		configPanel.add(darkThemeRadio);
		configPanel.add(darculaThemeRadio);
		

		configPanel.add(okButton);
		configPanel.add(cancelButton);

		return configPanel;
	}

	public void setButtonActionListener(){
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int newNbCol = Integer.parseInt(nbColTextField.getText());
				int newNbRow = Integer.parseInt(nbRowTextField.getText());

				if(Config.nbCol != newNbCol || Config.nbRow != newNbRow){
					int res = TopMenuBar.confirmMessage(TE);
					if(res == JOptionPane.YES_OPTION){
						Config.nbCol = newNbCol;
						Config.nbRow = newNbRow;
						Config.calculMapSize();
						if(TopMenuBar.tileM != null){
							
							TopMenuBar.tileM.reloadMap();
						}
					}
				}

				if(darkThemeRadio.isSelected()){
					Config.theme = MainFrame.darkTheme;
				}
				else if(lightThemeRadio.isSelected()){
					Config.theme = MainFrame.lightTheme;
				}
				else if(darculaThemeRadio.isSelected()){
					Config.theme = MainFrame.darculaTheme;
				}

				try {
					UIManager.setLookAndFeel(Config.theme);
					SwingUtilities.updateComponentTreeUI(configWindow);
					SwingUtilities.updateComponentTreeUI(MainFrame.window);
				} catch (Exception e1) {
					System.out.println("Erreur dans le changement de thème : " + e1);
					e1.printStackTrace();
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
