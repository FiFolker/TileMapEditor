package main;

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.SwingUtilities;

public class MouseHandler implements MouseListener, MouseMotionListener, MouseWheelListener {
	
	int x;
	int y;
	
	int wheel;

	boolean leftClicked, middleClicked;

	@Override
	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		//System.out.println("x : " + MainFrame.mouseH.x);
		//System.out.println("y : " + MainFrame.mouseH.y);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		//nothing
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int modifiers = e.getModifiersEx();
		if((modifiers & InputEvent.BUTTON1_DOWN_MASK) == InputEvent.BUTTON1_DOWN_MASK){
			leftClicked = true;
		}
		
		if ((modifiers & InputEvent.BUTTON2_DOWN_MASK) == InputEvent.BUTTON2_DOWN_MASK && SwingUtilities.isMiddleMouseButton(e)) {
			middleClicked = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {

		if (SwingUtilities.isLeftMouseButton(e)) {
			leftClicked = false;
		}

		if (SwingUtilities.isMiddleMouseButton(e)) {
			middleClicked = false;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		//nothing
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
		//nothing
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		//nothing
		x = e.getX();
		y = e.getY();
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		wheel = e.getWheelRotation();
	}

	
	
}
