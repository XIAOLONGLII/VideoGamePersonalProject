package com.mime.minefront.graphics;

import java.util.Random;

import com.mime.minefront.Game;
//child class of Render
public class Screen extends Render{

	private Render test;
	private Render3D render;
	
	
	
	public Screen(int width, int height) {
		super(width, height);
		Random random = new Random();
		render = new Render3D(width,height);
		test = new Render(1024,1024);
		
		for (int i = 0; i < 1024*1024;i++){
			test.pixels[i] = random.nextInt()*(random.nextInt(5) / 4); 
			
		}
	}
	public void render(Game game){
		for(int i = 0; i<width*height;i++){
			pixels[i] = 0;
		}
		
		/*for(int i = 0; i < 50; i++){
		int anmin = (int)(Math.sin((game.time + i*2) %1000.0/100)* 100);
		int anmin2 = (int)(Math.cos((game.time + i*2) %1000.0/100)* 100);*/
		
		// draw(test, (width-256)/2 + anmin, (height-256)/2 + anmin2);Draw the cube moving
		render.floor(game);
		render.renderLimiter(); 
		
		/*render.renderWall(0,   0.5, 1.5, 3, 1);
		render.renderWall(0,   0,    1,  1.5, 1);
		render.renderWall(0,   0.5,  1,  1,   1);
		render.renderWall(0.5, 0.5,  1,  1.5, 1);
		
		render.renderWall(0,   1.5, 1.5, 3, 1);
		render.renderWall(0,   1,    1,  1.5, 1);
		render.renderWall(0,   1.5,  1,  1,   1);
		render.renderWall(0.5, 1.5,  1,  1.5, 1);
		 
		render.renderWall(1,  1.5, 2.5, 2.5, 0);
		render.renderWall(1,  1,    2,  2.5, 0);
		render.renderWall(1,  1.5,  2,  2,   0);
		render.renderWall(1.5,1.5,  2,  2.5, 0);
		
		render.renderWall(2,  2.5, 2.5, 2.5, 0);
		render.renderWall(2,  2,    2,  2.5, 0);
		render.renderWall(2,  2.5,  2,  2,   0);
		render.renderWall(2.5,2.5,  2,  2.5, 0);
		
		render.renderWall(3,  3.5, 2.5, 2.5, 0);
		render.renderWall(3,  3,    2,  2.5, 0);
		render.renderWall(3,  3.5,  2,  2,   0);
		render.renderWall(3.5,3.5,  2,  2.5, 0);
		
		render.renderWall(4,  4.5, 2.5, 2.5, 0);
		render.renderWall(4,  4,    2,  2.5, 0);
		render.renderWall(4,  4.5,  2,  2,   0);
		render.renderWall(4.5,4.5,  2,  2.5, 0);
		
		render.renderWall(5,  5.5, 2.5, 2.5, 0);
		render.renderWall(5,  5,    2,  2.5, 0);
		render.renderWall(5,  5.5,  2,  2,   0);
		render.renderWall(5.5,5.5,  2,  2.5, 0);
		
		render.renderWall(6,  6.5, 2.5, 2.5, 0);
		render.renderWall(6,  6,    2,  2.5, 0);
		render.renderWall(6,  6.5,  2,  2,   0);
		render.renderWall(6.5,6.5,  2,  2.5, 0);
		
		render.renderWall(7,  7.5, 2.5, 2.5, 0);
		render.renderWall(7,  7,    2,  2.5, 0);
		render.renderWall(7,  7.5,  2,  2,   0);
		render.renderWall(7.5,7.5,  2,  2.5, 0);
		*/
		
		draw(render,0,0);
		
	}

}
