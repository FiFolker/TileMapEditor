package interfaces;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.MainFrame;

public class Button {

	Rectangle button;
	Polygon icon;
	BufferedImage image;
	boolean isSelected = false;
	String toolTipMessage;

	public Button(int x, int y, int width, int height, int[] xPoly, int[] yPoly, int nbVertices){
		this.button = new Rectangle(x,y,width,height);
		this.icon = new Polygon(xPoly, yPoly, nbVertices);
	}

	public Button(int x, int y, int width, int height){
		this.button = new Rectangle(x,y,width,height);
	}

	public Button(int x, int y, int width, int height, BufferedImage image){
		this.button = new Rectangle(x,y,width,height);
		this.image = image;
	}

	public Button(Rectangle rect){
		this.button = rect;
	}

	public Button(Rectangle rect, BufferedImage image){
		this.button = rect;
		this.image = image;
	}

	public Button(Rectangle rect, BufferedImage image, String desc){
		this.button = rect;
		this.image = image;
		this.toolTipMessage = desc;
	}

	public Button(Rectangle rect, Polygon poly){
		this.button = rect;
		this.icon = poly;
	}

	public void update(){
		
	}

	public void draw(Graphics2D g2){
		
		if(inCollision()){
			g2.setColor(Color.gray);
		}else if(isSelected){
			g2.setColor(Color.green);
		}else{
			g2.setColor(Color.black);
		}
		
		g2.draw(button);

		if(icon != null){
			g2.draw(icon);
		}

		if(image != null){
			g2.drawImage(image, button.x, button.y, button.width, button.height, null);
		}

		if(toolTipMessage != null && inCollision()){
			g2.setColor(Color.blue);
			g2.setFont(new Font("test", Font.BOLD, 14));
			g2.drawString(toolTipMessage, MainFrame.mouseH.x, MainFrame.mouseH.y - 56);
		}

	}

	public boolean inCollision(){
		return button.contains(MainFrame.mouseH.location);
	}

	public boolean isClicked(){
		return inCollision() && MainFrame.mouseH.leftClickedOnceTime;
	}
	
}
