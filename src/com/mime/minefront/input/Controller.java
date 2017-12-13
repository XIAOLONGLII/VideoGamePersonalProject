package com.mime.minefront.input;

//import com.mime.minefront.Display;

public class Controller {

	public double x,y,z,rotation,xa,za,rotationa;
	
	
	//public static boolean turnLeft = false;
	//public static boolean turnRight = false;
	public static boolean walk = false;
	public static boolean crouchWalk = false;
	public static boolean runWalk = false;
	
	
	public void tick(boolean forward,boolean forward1,boolean exit,boolean back,
			boolean left,boolean right,boolean rleft,boolean rright,
			boolean jump,boolean jump1,
			boolean crouch, boolean run){
		// 鼠标的移动速度 double rotationSpeed = 0.0005 * Display.MouseSpeed;
		// 键盘的移动速度
		double rotationSpeed = 0.0044;
		double walkSpeed = 0.6;
		
		//jump 2
		double jumpHeight = 0.5;
		//crouch 2
		double crouchHeight = 0.3;
		double xMove = 0;
		double zMove = 0;
		
	
	
	if (forward){
		zMove++;
		walk = true;
	}
	if (forward1){
		zMove++;
		walk = true;
	}
	if (back){
		zMove--;
		walk = true;
	}
	if (left){
		xMove--;
		walk = true;
	}
	if (right){
		xMove++;
		walk = true;
	}
	//键盘的rotation 不再mouse
	if(rleft){
		rotationa -= 5*rotationSpeed;
	}
	if(rright){
		rotationa += 5*rotationSpeed;
	}
	if(exit){
		System.exit(0);
	}
	
	/* Mousemoment if (turnLeft){
		//when click right click, it wont turn around. 
		if(InputHandler.MouseButton == 3){	
		}else {
			rotationa -= rotationSpeed;
		}	
	}
	if (turnRight){
		if(InputHandler.MouseButton ==3 ){	
		}else{
		rotationa += rotationSpeed;
		}	
	} */
	// jump 3
	if (jump){
		y += 5*jumpHeight;
		run = false;
	}
	if (jump1){
		y += 10*jumpHeight;
		run = false;
	}
	//crouch 3
	if(crouch){
		y = y - crouchHeight;
		run = false;
		crouchWalk = true;
		walkSpeed = 0.2;
		
	}
	//sprinting 3
	if(run){
		walkSpeed = 1;
		walk = true;
	}
	//for not shaking 
	if(!run && !back && !left && !forward && !right){
		walk = false;
	}
	if(!crouch){
		crouchWalk = false;
	}
	
	if(!run){
		runWalk = false;
	}
	
	xa += (xMove * Math.cos(rotation) + zMove * Math.sin(rotation)) * walkSpeed;
	za += (zMove * Math.cos(rotation) - xMove * Math.sin(rotation)) * walkSpeed;
	
	x += xa;
	z += za;
	
	xa *= 0.1;
	za *= 0.1;
	
	y *=0.9;// jump 3: The max jump can reach
	
	rotation += rotationa;
	rotationa *= 0.5;
	
}

	
}
