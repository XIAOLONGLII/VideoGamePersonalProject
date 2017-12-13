/*The 1st class Display() 
 *1,a, purpose: create a window ( width, height, window title, 
 *b.Canvas as an object to help create a window. 
 *2.Game Loop. 
 *a.Create Thread to let this class use this thread, to let class to do multiple tasks same time 
 * b. Create start() and stop() to let thread works. 
 * 
 * The 2nd  class, Render() (paint) class
 * 1.a. Pixel[] array to ready to draw pixels on screen.
 * b, draw() method to starting drawing... 
 * 
 * The 3rd class is Screen() class
 * 1a. test() in class to test if draw() is working? 
 * b.BufferedImage() in Display() will help draw image, also with different colors. 
 * c. BufferedStrategy() in Display 
 * d. In Display() class write draw() to really draw image.
 * 
 * The 4th class Render(),to play around pixels
 * 1a.Use math Sin and COs to make the pixels move and draw more of them. The way is in a array methods, keep drawing..
 * 1b,Render draw() makes it draw more pixels. 
 * 
 * The 5th class in Dispaly() FPS
 * It is frame per second, FPS, to see how fast 
 * 
 * The 6th class enter in different numbers in screen(), it will draw different pixels. 
 * 
 * The 7th class Game Start 3D Game, create Render3D()
 * 1. Inhereint from Render()
 * 2. Floor() method to draw pixles on floor 
 * 3. Ceiling() methods 
 * 4. 地板 和 天花板 都在这里画的
 * 
 * The 8th Animation in Render3D()
 * 1. make a virable time = 0.0005; when time goes, then it will draw different pixels, it looks like Animation. 
 * 2. also game.time make animation works. 
 * 3. use cos and sin= game.time/int to make the game rotation .
 * 4. forward = game.time also let the screen go forward 
 * 
 * InputHandler class includes method gets keyboard working. 
 * The 8th 跳 跑 蹲 都在 Game（）
 * 
 * BufferStrategy() to create a beffereStrategy based on the number of buffers we want. 
 * Two of them for doubling buffering and page flipping. 
 * Last second: Make the launcher move 
 * a=60 , 60 = 0011 1100
 * c = a << 2;       /* 240 = 1111 0000 
 * Bitwise Operator :
 * 
 * */
 
package com.mime.minefront;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.mime.minefront.graphics.Render;
import com.mime.minefront.graphics.Screen;
import com.mime.minefront.gui.Launcher;
import com.mime.minefront.input.Controller;
import com.mime.minefront.input.InputHandler;

import com.mime.minefront.Sprite;

//import java.awt.image.DataBufferInt;
import java.awt.image.*;

public class Display extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 1024;
	public static final int HEIGHT = 1024;
	public static String TITLE = "3D Game";
	
	private Thread thread;
	private Screen screen;
	private Game game;
	private BufferedImage img;
	
	//Sprite hello;
	
	public static int MouseSpeed;
	static Launcher launcher;
	
	
	//private Render render;
	private boolean running = false;
	private int[] pixels;
	private InputHandler input;
	//mouse
	private int newX = 0;
	private int oldX = 0;
	//This methods can print integer and string
	private int fps;
	 
	
	public Display(){
		//put all WIDTH and HEIGHT in a parameter
		//sound = new Sound("/");
		Dimension size = new Dimension(WIDTH,HEIGHT);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		
		//hello = new Sprite(100, 100, "/tree1.png");
		
		//render = new Render(WIDTH,HEIGHT);
		screen = new Screen(WIDTH,HEIGHT);
		game = new Game();
		//RGB: red green blue
		img = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
		
		input = new InputHandler();
		addKeyListener(input);
		addFocusListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);
	}
	
	public static Launcher getLauncherInstance(){
		
			return launcher;
		
		
		
	}
	
	//get help from people in starbucks, Thread has to pass an object to run it. 
	public synchronized void start(){
		if(running) return;
		running = true;
		thread = new Thread(this,"game");
		thread.start();
	}
	
	
	public synchronized void stop(){
		if(!running)
			return;
		running = false;
		try{
			thread.join();
		}catch (Exception e){
			e.printStackTrace();
			System.exit(0);
			}
		
	}
	public void run(){
		//Sound.sound1.play();
		int frames = 0;
		double unprocessedSeconds=0;
		long previousTime = System.nanoTime();
		double secondsPerTick = 1/60.0;
		int tickCount = 0;
		boolean ticked = false;
		requestFocus();
		
		while(running){
			long currentTime = System.nanoTime();
			long passedTime = currentTime - previousTime;
			previousTime = currentTime;
			unprocessedSeconds += passedTime/ 1000000000.0;
			
			//launcher.updateFrame();
			
			while (unprocessedSeconds > secondsPerTick){
				tick();
				unprocessedSeconds -= secondsPerTick;
				ticked = true;
				tickCount++;
				if (tickCount % 60 == 0 ){
					//System.out.println(frames + "fps");
					fps = frames;
					previousTime +=1000;
					frames = 0;
				}
				if (ticked){
					 render();
					//renderMenu();
					frames++;
				}
				//renderMenu();
				//render();
				//frames++;
				
				}
			
			
			
			
			// Check for coordiantes: System.out.println("X :" + InputHandler.MouseX + " Y: " + InputHandler.MouseY);
			//这里是为什么鼠标可以转方向
			/*newX = InputHandler.MouseX;
			MouseSpeed = Math.abs(newX-oldX);// This is absolute value, no negative value.
			if(MouseSpeed == 0){
				MouseSpeed = MouseSpeed * -1;
			}
			if (newX > oldX){
				
				Controller.turnRight = true;
			}
			if(newX < oldX){
				
				Controller.turnLeft = true;
			}
			if(newX == oldX){
				
				Controller.turnLeft = false;
				Controller.turnLeft = false;
			}
			
			//Absolutely value here suppos ed to let mouse turn left and right
			
			oldX = newX;*/
			
			//System.out.println("X " +InputHandler.MouseX + "Y "+ InputHandler.MouseY);
		}
		
	}
	private void tick(){ //update methods
		game.tick(input.key);
 
	}
	
	
	
	
	
	private void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			createBufferStrategy(3);
			return;
		}
		
		screen.render(game);
		
		for(int i = 0; i<WIDTH*HEIGHT;i++){
			pixels[i] = screen.pixels[i];
		}
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(img, 0, 0, WIDTH+10, HEIGHT+10, null);
		//draw something on the Jframe
		g.setFont(new Font("Verdane",1, 50));
		g.setColor(Color.YELLOW);
		//g.drawString("FPS" + fps, 20, 50);
		g.dispose();
		bs.show();
	}
	
	
	public static void main(String[] args){
		// let mouse be blank #1 
		
		new Launcher(0);
		//getLauncherInstance();
		
	}
	

}
