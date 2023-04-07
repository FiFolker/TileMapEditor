package interfaces;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import main.MainFrame;
import main.TileEditorPanel;

public class BrushSelector {
	
	TileEditorPanel TE;
	int leftPos, topPos;
	public final int caseSize = 48;

	private HashMap<String, Button> listOfButtons;

	public BrushSelector(TileEditorPanel TE, int leftPos, int topPos){
		this.TE = TE;
		this.leftPos = leftPos;
		this.topPos = topPos;
		listOfButtons = new HashMap<>();
		setup();
		listOfButtons.get(TE.brush.defaultBrush).isSelected = true;
	}

	public void setup(){
		try {
			listOfButtons.put(TE.brush.defaultBrush, new Button(new Rectangle(leftPos,topPos+caseSize*listOfButtons.size()-1,caseSize, caseSize),
			 ImageIO.read(new File("assets/buttons/defaultBrush.png")), "Pinceau 1 à 1"));

			listOfButtons.put(TE.brush.repaintAll, new Button(new Rectangle(leftPos,topPos+(caseSize*listOfButtons.size()-1)+listOfButtons.size(),caseSize, caseSize),
			 ImageIO.read(new File("assets/buttons/bucket.png")), "Tout repaindre avec le même tiles"));

			listOfButtons.put(TE.brush.repaintAllOccurence, new Button(new Rectangle(leftPos,topPos+(caseSize*listOfButtons.size()-1)+listOfButtons.size(),caseSize, caseSize),
			 ImageIO.read(new File("assets/buttons/occurenceBrush.png")), "Repaindre toutes les occurences"));

			listOfButtons.put(TE.brush.erase, new Button(new Rectangle(leftPos,topPos+(caseSize*listOfButtons.size()-1)+listOfButtons.size(),caseSize, caseSize),
			 ImageIO.read(new File("assets/buttons/eraser.png")), "Effacer toute la partie visible de la grille"));
		} catch (IOException e) {
			System.out.println("Problème dans la génération des buttons du BrushSelector");
			e.printStackTrace();
		}
	}

	public void update(){
		if(listOfButtons.get(TE.brush.defaultBrush).isClicked()){
			TE.brush.changeBrush(TE.brush.defaultBrush);
			changeSelectedButton(TE.brush.defaultBrush);
			MainFrame.mouseH.leftClickedOnceTime = false;
		}
		if(listOfButtons.get(TE.brush.repaintAll).isClicked()){
			TE.brush.changeBrush(TE.brush.repaintAll);
			changeSelectedButton(TE.brush.repaintAll);
			MainFrame.mouseH.leftClickedOnceTime = false;
		}
		if(listOfButtons.get(TE.brush.repaintAllOccurence).isClicked()){
			TE.brush.changeBrush(TE.brush.repaintAllOccurence);
			changeSelectedButton(TE.brush.repaintAllOccurence);
			MainFrame.mouseH.leftClickedOnceTime = false;
		}
		if(listOfButtons.get(TE.brush.erase).isClicked()){
			TE.brush.changeBrush(TE.brush.erase);
			changeSelectedButton(TE.brush.erase);
			MainFrame.mouseH.leftClickedOnceTime = false;
		}
	}

	public void changeSelectedButton(String currentButton){
		for(String s : listOfButtons.keySet()){
			if(s.equals(currentButton)){
				listOfButtons.get(s).isSelected = true;
			}else{
				listOfButtons.get(s).isSelected = false;
			}
		}
	}

	public void draw(Graphics2D g2){
		g2.setStroke(new BasicStroke(2f));
		for(Button b : listOfButtons.values()){
			b.draw(g2);
		}
	}

}
