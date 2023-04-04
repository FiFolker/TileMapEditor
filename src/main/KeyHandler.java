package main;

import java.awt.RenderingHints.Key;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyHandler implements KeyListener{

	public boolean ctrlPressed, moveUp, moveRight, moveDown, moveLeft, gridState = true, save = false;

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();

		if(code == KeyEvent.VK_CONTROL){
			ctrlPressed = true;
		} 
		if(ctrlPressed && code == KeyEvent.VK_S){
			save = true;
		} 
		if(code == KeyEvent.VK_Z || code == KeyEvent.VK_UP){
			moveUp = true;
		}
		if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT){
			moveRight = true;
		}
		if(code == KeyEvent.VK_Q || code == KeyEvent.VK_LEFT){
			moveLeft = true;
		}
		if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN ){
			moveDown = true;
		}
		if(code == KeyEvent.VK_G){
			gridState = !gridState;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();

		if(code == KeyEvent.VK_CONTROL){
            ctrlPressed = false;
        }
		if(code == KeyEvent.VK_Z || code == KeyEvent.VK_UP){
			moveUp = false;
		}
		if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT){
			moveRight = false;
		}
		if(code == KeyEvent.VK_Q || code == KeyEvent.VK_LEFT){
			moveLeft = false;
		}
		if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
			moveDown = false;
		}
	}
	
}
