package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseHandler implements MouseListener, MouseMotionListener {
	
	int x;
	int y;
	
	boolean clicked;

	@Override
	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		System.out.println("x : " + x + " y : " + y);

	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("tryDraw " + e.getClickCount());
		clicked = true;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// rien pour le moment... rien tout court normalement d'ailleurs
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// rien pour le moment... rien tout court normalement d'ailleurs
		clicked = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// rien pour le moment... rien tout court normalement d'ailleurs
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// rien pour le moment... rien tout court normalement d'ailleurs
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// rien pour le moment... rien tout court normalement d'ailleurs
		
	}

	
	
}
