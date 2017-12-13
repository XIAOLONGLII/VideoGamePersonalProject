//this class i have leanred cant draw a frame on a window. 
// no Jframe and Jframe
//http://stackoverflow.com/questions/29546616/exception-in-thread-main-adding-window-to-container

package com.mime.minefront.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.security.auth.login.Configuration;
import javax.swing.*;

import com.mime.minefront.Display;
import com.mime.minefront.RunGame;
import com.mime.minefront.input.InputHandler;



public class Launcher extends Canvas implements Runnable{

	private static final long serialVersionUID = 1L;
	
	protected JPanel window = new JPanel();
	private JButton play,options,help, quit;
	private Rectangle rplay,roptions,rhelp,rquit;
	//Configuration config = new Configuration();
	

	private int width = 840;
	private int height = 820;
	protected int buttonWidth = 80;
	protected int buttonHeight = 40;
	boolean running = false;
	Thread thread;
	JFrame frame = new JFrame();
	
	//sprite 

	
	//private Image[] image;    
	
	
	
	public Launcher(int id){
	

		try{
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		}catch(Exception e){
			e.printStackTrace();
		}
		frame.setUndecorated(true);
		frame.setTitle("Maze Game");
		frame.setSize(new Dimension(width,height));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//getContentPane().add(window);
		//frame.add(display); 
		frame.add(this);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		//frame.setLocation(200,20);
		frame.setVisible(true);
		window.setLayout(null);
		//image = new Image[10];
		//if(id == 0){
			//drawButtons();
		//}
		InputHandler input = new InputHandler();
		addKeyListener(input);
		addFocusListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);
		startMenu();
		//display.start();
		frame.repaint();
	
	}
	
	public void updateFrame(){
		//setLocation(200,20);
		//System.out.println("X: "+InputHandler.MouseX + "Y "+ InputHandler.MouseY);
		if(InputHandler.dragged){
			
			Point p = frame.getLocation();
			frame.setLocation(p.x + InputHandler.MouseDX-InputHandler.MousePX, p.y + InputHandler.MouseDY-InputHandler.MousePY);
		}
	}
	
	
	public void startMenu(){
		running = true;
		thread = new Thread(this,"Menu");
		thread.start() ;
		
	}
	public void stopMenu(){
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	public void run() {
		requestFocus();
		while(running){
			try{
				renderMenu();
			}catch (IllegalStateException e){
				
			}
			
			updateFrame();
		}
		
	}

	private void renderMenu() throws IllegalStateException {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){ 
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, 800, 800);
		
		//这里是点击图片 然后会进入3D游戏
		try {
			g.drawImage(ImageIO.read(Display.class.getResource("/maze.png")),0,0,810,800,null);

	
			g.drawImage(ImageIO.read(Display.class.getResource("/bear2.jpg")),50,650,50,50,null);
			g.drawImage(ImageIO.read(Display.class.getResource("/bear1.png")),650,650,60,60,null);
			
			g.drawImage(ImageIO.read(Display.class.getResource("/play.png")),750,130,80,80,null);
			if(InputHandler.MouseButton ==1){
				frame.dispose();
				new RunGame();
				}
			
			
			//点击退出的照片
			if(g.drawImage(ImageIO.read(Display.class.getResource("/exit.png")),750,230,80,80,null)){
			if(InputHandler.MouseButton ==1){
				System.exit(0);
				}
			}
			
		
		} catch (IOException e) {
			e.printStackTrace();
		}

		g.dispose();
		bs.show();
	}

	
	
	
	/*private void drawButtons(){
		play = new JButton("Play");
		rplay = new Rectangle((width/2)-(buttonWidth/2),90,buttonWidth,buttonHeight);
		play.setBounds(rplay);
		window.add(play);
		
		
		options = new JButton("Options");
		roptions = new Rectangle((width/2)-(buttonWidth/2),140,buttonWidth,buttonHeight);
		options.setBounds(roptions);
		window.add(options);
		
		help = new JButton("Help");
		rhelp = new Rectangle((width/2)-(buttonWidth/2),190,buttonWidth,buttonHeight);
		help.setBounds(rhelp);
		window.add(help);
		
		quit = new JButton("Quit");
		rquit = new Rectangle((width/2)-(buttonWidth/2),240,buttonWidth,buttonHeight);
		quit.setBounds(rquit);
		window.add(quit);
		
		play.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//dispose();//get rid of small window 
				new RunGame();
			}
		});
		
		help.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {	
				
			}
		});
		
		options.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {	
				new Options();
			}
		});
		
		quit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {	
				System.exit(0);
			}
		});
	}*/

	
	

}
