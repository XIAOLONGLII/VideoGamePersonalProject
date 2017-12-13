package com.mime.minefront.gui;

import java.awt.Dimension;
import java.awt.*;

import javax.swing.JButton;

import com.mime.minefront.Display;

public class Options extends Launcher {

	
	private static final long serialVersionUID = 1L;
	private int width = 550;
	private int height =450;
	private JButton ok;
	private Rectangle rOk;
	
	public Options(){
		super(1);
		setName("Options!");
		setSize(new Dimension(width,height));
		setLocation(null);
		
		drawButtons();
	}
	private void drawButtons(){
		ok = new JButton("OK");
		rOk = new Rectangle();
	}
	

}
