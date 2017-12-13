package com.mime.minefront;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import com.mime.minefront.gui.Launcher;

public class RunGame {
	

	public RunGame(){
		BufferedImage cursor = new BufferedImage(16,16,BufferedImage.TYPE_INT_ARGB);
		Cursor blank = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(0,0), "blank");
		
		Display game = new Display();
		JFrame frame = new JFrame();
		// let mouse be blank #2
		//frame.getContentPane().setCursor(blank);
		frame.add(game);
		frame.pack();
		frame.setTitle(Display.TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setSize(WIDTH,HEIGHT); Because I have dimension 
		frame.setLocationRelativeTo(null);
		frame.setResizable(false );
		frame.setVisible(true);
		
		game.start();
		stopMenuThread(); 
	
		
	}
	private void stopMenuThread(){
		Display.getLauncherInstance().stopMenu();
	}
	

}
