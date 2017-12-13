package com.mime.minefront.input;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class InputHandler implements KeyListener, FocusListener,MouseListener,MouseMotionListener  {

	public boolean[] key = new boolean[68836];
	//Mouse move methods:
	public static int MouseX;
	public static int MouseY;
	public static int MouseDX; //Drag mouse
	public static int MouseDY;
	public static int MousePX;//Mouse pressed location
	public static int MousePY;
	public static int MouseButton;
	public static boolean dragged = false;
	
	@Override
	public void mouseDragged(MouseEvent e) {
		MouseDX = e.getX();
		MouseDY = e.getY();
		dragged = true;
		
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		//getX()
		MouseX = e.getX();
		MouseY = e.getY();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
				
	}
	
	
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void focusLost(FocusEvent e) {
		for (int i = 0; i<key.length; i++){
			key[i] = false;
		}
		
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		MouseButton = e.getButton();
		MousePX = e.getX();
		MousePY = e.getY();
		//dragged = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		dragged = false;
		MouseButton = 0;
	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}



	

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
			key[keyCode] = true;
		
		System.out.println("keyPress "+keyCode);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode > 0 && keyCode < key.length){
			key[keyCode] = false;
		}
		System.out.println("KeyRelease "+keyCode);

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
