package com.mime.minefront;

import java.awt.event.KeyEvent;

import com.mime.minefront.input.Controller;
import com.mime.minefront.level.Level;

public class Game {
	public int time;
	public Controller controls;
	public Level level;
	
	public Game(){
		controls = new Controller();
		level = new Level(80,80);
	
	}
	public void tick(boolean[] key){
		
		// use boolean, if it is pressed, then it is true;
		
		time++;
		boolean forward = key[KeyEvent.VK_W];
		boolean forward1 = key[KeyEvent.VK_UP];
		boolean exit = key[KeyEvent.VK_Q];


		boolean back = key[KeyEvent.VK_S];
		boolean left = key[KeyEvent.VK_A];
		boolean right = key[KeyEvent.VK_D];
		boolean rleft = key[KeyEvent.VK_LEFT];
		boolean rright = key[KeyEvent.VK_RIGHT];
		
		/*boolean turnLeft = key[KeyEvent.VK_LEFT];
		boolean turnRight = key[KeyEvent.VK_RIGHT];*/
		
		//for jump 1
		boolean jump = key[KeyEvent.VK_SPACE];
		boolean jump1 = key[KeyEvent.VK_J];

		
		//for crouch 1
		boolean crouch = key[KeyEvent.VK_ENTER];
		//controls.tick(forward, back, left, right, turnLeft, turnRight);
		
		//for sprinting
		boolean run = key[KeyEvent.VK_SHIFT];
		
		//Mouse movement
		controls.tick(forward,forward1,exit, back, left, right,rleft,rright,jump,jump1,crouch,run);
		
		
	}
}
