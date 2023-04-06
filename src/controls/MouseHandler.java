package controls;

import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.SwingUtilities;

public class MouseHandler implements MouseListener, MouseMotionListener, MouseWheelListener {
	
	public int x;
	public int y;
	public Point location = new Point(0, 0);
	
	public int wheel;

	public boolean leftClicked, leftClickedOnceTime;

	@Override
	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		location = e.getPoint();
		location.x -= 10;
		location.y -= 56;
		//System.out.println("x : " + MainFrame.mouseH.x);
		//System.out.println("y : " + MainFrame.mouseH.y);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// nothing
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int modifiers = e.getModifiersEx();
		if((modifiers & InputEvent.BUTTON1_DOWN_MASK) == InputEvent.BUTTON1_DOWN_MASK){
			leftClicked = true;
			leftClickedOnceTime = true;
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {

		if (SwingUtilities.isLeftMouseButton(e)) {
			leftClicked = false;
			leftClickedOnceTime = false;
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
