package com.mime.minefront.graphics;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Texture {

	public static Render floor= loadBitMap("/paint.png"); 
	public static Render loadBitMap(String filename){
		try{
			//buffer image to reload image.
			BufferedImage image = ImageIO.read(Texture.class.getResource(filename));
			int width = image.getWidth();
			int height = image.getHeight();
			Render result = new Render(width, height);
			image.getRGB(0, 0, width, height, result.pixels, 0, width);
			return result;
		} catch(Exception e){
			System.out.println("Crash");
			throw new RuntimeException(e);
		}
			
		
	}
	
}
