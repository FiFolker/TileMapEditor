package interfaces;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import main.TileEditorPanel;

public class HUD {

    TileEditorPanel TE;

    public boolean saved = false;
    int counter = 0;

    Font arialBold = new Font("arial", Font.BOLD, 13);

    public HUD(TileEditorPanel TE){
        this.TE = TE;
    }
    
    public void update(){
        if(saved){
            counter ++;
            if(counter >= 60){
                saved = false;
                counter = 0;
            }
        }
    }

    public void draw(Graphics2D g2){
        g2.setFont(new Font("arial", Font.BOLD, 13));
        g2.setColor(Color.black);
		if(TopMenuBar.tileM != null){
			g2.drawString("Tiles séléctionné : ", 200, 615);
			g2.drawImage(TopMenuBar.tileM.tiles.get(TE.indexOfSelectedTiles).image, 320, 600, 24, 24, null);
			g2.drawString(TopMenuBar.tileM.tiles.get(TE.indexOfSelectedTiles).name, 350, 615);
		}
		g2.drawString("Colonne : " + TE.mouseCasePos.column, 200, 650);
		g2.drawString("Ligne : " + TE.mouseCasePos.line, 285, 650);

        if(saved){
            g2.drawString("Sauvegardé ! ", 0, 650);
        }
    }
}
