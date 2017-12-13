package com.mime.minefront.graphics;
//这里有重要的一个方式： BITWISE Operation && >> or << or >>>>  
// 这个BITWISE Operation 画出了最原始的形状，然后我加上我画的8*8 pixels 在这个程序上 就出现来天花板跟地板 而最重要的是 & 在这里起的作业
// image.getRGB() 把我画的画 变在一个array里面，然后画出来 变成地板和墙 Return: array of RGB pixels.
//Returns an integer pixel in the default RGB color model (TYPE_INT_ARGB) and default sRGB colorspace.
import java.util.Random;

import com.mime.minefront.Game;
import com.mime.minefront.input.Controller;
import com.mime.minefront.level.Block;
import com.mime.minefront.level.Level;

public class Render3D extends Render {
	
	public double[] zBuffer;
	public double[] zBufferWall;
	//这个zBufferWall 会改变wall的透明
	private double renderDistance = 30000;//The brightness.
	//private double forwardGlobal;
	private double forward, left,right,up, cosine, sine, walking;

	

	public Render3D(int width, int height) {
		super(width, height);
		zBuffer = new double[width*height];
		zBufferWall = new double[width];	
	}
	//double time = 0;
	
	public void floor(Game game){
		
		for(int i = 0; i <width; i++){
			zBufferWall[i] = 0;
		}
		
		//double rotation = game.time/100.0;
		
		// change pixels floorPostion and CeilingPosition高度和低度 天花板的地板
		double ceilingPosition = 180; 
		double floorPosition = 16;
		
		
		forward = 3*game.controls.z;
		//forwardGlobal = forward;
		right = game.controls.x;
		//jump 4
		up = game.controls.y;
		//if(Controller.runWalk){
			//walking = Math.sin(game.time/6.0) * 0.8;
		//}
		double rotation = game.controls.rotation;
		cosine = Math.cos(rotation);
		sine = Math.sin(rotation);
		walking = 0; 

		for (int y = 0; y <height; y++){
			double ceiling = (y+  -height / 2.0) / height;
			double z = (floorPosition + up ) / ceiling;
			
			if(Controller.walk){
				walking = Math.sin(game.time/6.0) * 0.5;
				z = (floorPosition + up + walking ) / ceiling;
			}
			
			if(Controller.crouchWalk && Controller.walk){
				walking = Math.sin(game.time/6.0) * 0.25;
				z = (floorPosition + up + walking ) / ceiling;
			}
			if(Controller.runWalk && Controller.walk){
				walking = Math.sin(game.time/6.0) * 0.8;
				z = (floorPosition + up + walking ) / ceiling;
			}
			
			if(ceiling < 0){
				//ceiling =- ceiling;
				z = (ceilingPosition - up )/ -ceiling;
				if(Controller.walk){
					z = (ceilingPosition - up- walking )/ -ceiling;
			}
			}

			//这里是画的墙的第一步，画的一面纸或者一面单纯的墙，然后从这里开始出发。 Xx 横着的长度，Yy 是 竖这的长度，z 是面前的长度 近或者远 
			//然后 在这个loop 里面 x是画的pixel的密度
			for (int x = 0; x< width; x++){
				double depth = (x - width / 2.0)/ height;
				depth *= z;
				double xx = depth * cosine + z * sine;
				double yy = z * cosine - depth * sine;
				int xPix = (int)(xx + right);
				int yPix = (int)(yy + forward);
				zBuffer[x + y*width] = z;
				
				//这里画的天板和地板 如果 加8 在(xPix & 7) 后面，颜色会变化的
				pixels[x + y * width] = Texture.floor.pixels[((xPix & 4)) + (yPix & 7)*16];
				//Bitwise Operation! 
				//Epsiode 10
				
				//this z can let a gap between ceiling and floor 
				if(z > 500){
					pixels[x + y*width] = 0;
				}	
			}		
		}
			//System.out.println("walking :" + walking);
		//working is not working.
		//generate walls.
		Level level = game.level;
		//生产墙的数量。。。。。。the level size, 
		int size = 50 ;
		//generate blocks 生产墙跟，这个size 是生产多少墙
		for (int xBlock = -size; xBlock <= size; xBlock++){
			//for zBlock
			for(int zBlock = -size; zBlock <= size; zBlock++){
				Block block = level.create(xBlock,zBlock);
				Block East = level.create(xBlock + 1, zBlock);
				Block South = level.create(xBlock, zBlock + 1);
				
				if(block.solid){
					if(!East.solid){
					renderWall(xBlock +1,xBlock+1,zBlock,zBlock+1,0);
					}
					if(!South.solid){
						renderWall(xBlock+1,xBlock,zBlock+1,zBlock+1,0);
					}	
				}else{
					if(East.solid){
						renderWall(xBlock+1,xBlock+1,zBlock+1,zBlock,0);
					}
					if(South.solid){
						renderWall(xBlock,xBlock+1,zBlock+1,zBlock+1,0);
					}	
				}
			}
		}
		
		//generate blocks 生产墙头, 1,2,3,4,5
		for (int xBlock = -size; xBlock <= size; xBlock++){
			//for zBlock
			for(int zBlock = -size; zBlock <= size; zBlock++){
				Block block = level.create(xBlock,zBlock);
				Block East = level.create(xBlock + 1, zBlock);
				Block South = level.create(xBlock, zBlock + 1);
				
				if(block.solid){
					if(!East.solid){
					renderWall(xBlock +1 ,xBlock+1,zBlock,zBlock+1,0.5);
					}
					if(!South.solid){
						renderWall(xBlock+1,xBlock,zBlock+1,zBlock +1,0.5);
					}	
				}else{
					if(East.solid){
						renderWall(xBlock+1,xBlock+1,zBlock+1,zBlock,0.5);
					}
					if(South.solid){
						renderWall(xBlock,xBlock+1,zBlock+1,zBlock+1,0.5);
					}	
				}
			}
		}
		
		for (int xBlock = -size; xBlock <= size; xBlock++){
			//for zBlock
			for(int zBlock = -size; zBlock <= size; zBlock++){
				Block block = level.create(xBlock,zBlock);
				Block East = level.create(xBlock + 1, zBlock);
				Block South = level.create(xBlock, zBlock + 1);
				
				if(block.solid){
					if(!East.solid){
					renderWall(xBlock +1 ,xBlock+1,zBlock,zBlock+1,1);
					}
					if(!South.solid){
						renderWall(xBlock+1,xBlock,zBlock+1,zBlock +1,1);
					}	
				}else{
					if(East.solid){
						renderWall(xBlock+1,xBlock+1,zBlock+1,zBlock,1);
					}
					if(South.solid){
						renderWall(xBlock,xBlock+1,zBlock+1,zBlock+1,1);
					}	
				}
			}
		}
		
		for (int xBlock = -size; xBlock <= size; xBlock++){
			//for zBlock
			for(int zBlock = -size; zBlock <= size; zBlock++){
				Block block = level.create(xBlock,zBlock);
				Block East = level.create(xBlock + 1, zBlock);
				Block South = level.create(xBlock, zBlock + 1);
				
				if(block.solid){
					if(!East.solid){
					renderWall(xBlock +1 ,xBlock+1,zBlock,zBlock+1,1.5);
					}
					if(!South.solid){
						renderWall(xBlock+1,xBlock,zBlock+1,zBlock +1,1.5);
					}	
				}else{
					if(East.solid){
						renderWall(xBlock+1,xBlock+1,zBlock+1,zBlock,1.5);
					}
					if(South.solid){
						renderWall(xBlock,xBlock+1,zBlock+1,zBlock+1,1.5);
					}	
				}
			}
		}
		

		for (int xBlock = -size; xBlock <= size; xBlock++){
			//for zBlock
			for(int zBlock = -size; zBlock <= size; zBlock++){
				Block block = level.create(xBlock,zBlock);
				Block East = level.create(xBlock + 1, zBlock);
				Block South = level.create(xBlock, zBlock + 1);
				
				if(block.solid){
					if(!East.solid){
					renderWall(xBlock +1 ,xBlock+1,zBlock,zBlock+1,2);
					}
					if(!South.solid){
						renderWall(xBlock+1,xBlock,zBlock+1,zBlock +1,2);
					}	
				}else{
					if(East.solid){
						renderWall(xBlock+1,xBlock+1,zBlock+1,zBlock,2);
					}
					if(South.solid){
						renderWall(xBlock,xBlock+1,zBlock+1,zBlock+1,2);
					}	
				}
			}
		}
		
		for (int xBlock = -size; xBlock <= size; xBlock++){
			//for zBlock
			for(int zBlock = -size; zBlock <= size; zBlock++){
				Block block = level.create(xBlock,zBlock);
				Block East = level.create(xBlock + 1, zBlock);
				Block South = level.create(xBlock, zBlock + 1);
				
				if(block.solid){
					if(!East.solid){
					renderWall(xBlock +1 ,xBlock+1,zBlock,zBlock+1,2.5);
					}
					if(!South.solid){
						renderWall(xBlock+1,xBlock,zBlock+1,zBlock +1,2.5);
					}	
				}else{
					if(East.solid){
						renderWall(xBlock+1,xBlock+1,zBlock+1,zBlock,2.5);
					}
					if(South.solid){
						renderWall(xBlock,xBlock+1,zBlock+1,zBlock+1,2.5);
					}	
				}
			}
		}
		
	}
	
	

	// want wall stay there, not move. 生产墙。。。
	public void renderWall(double xLeft,double xRight, double zDistanceLeft,double zDistanceRight,double yHeight){
		
		double upCorrect = 0.0650;
		
		// these methods can pretend the wall fly to the ceiling when jump 
		double rightCorrect = 0.0650;
		double forwardCorrect = 0.06500;
		double walkCorrect = -0.0650;
		
		
		double xcLeft = ((xLeft)-(right*rightCorrect))*2;
		double zcLeft =((zDistanceLeft - (forward*forwardCorrect))*2);
		
		double rotLeftSideX = xcLeft * cosine - zcLeft * sine;
		double yCornerTL = ((-yHeight)-(-up*upCorrect + (walking * walkCorrect))) *2;
		double yCornerBL = ((+0.5 - yHeight) -(-up*upCorrect+ (walking * walkCorrect))) *2;
		double rotLeftSideZ = zcLeft *cosine + xcLeft * sine;
		
		
		double xcRight = ((xRight)-(right*rightCorrect))*2;
		double zcRight =((zDistanceRight - (forward*forwardCorrect))*2);
		
		double rotRightSideX = xcRight * cosine - zcRight * sine;
		double yCornerTR = ((-yHeight)-(-up*upCorrect+ (walking * walkCorrect))) *2;
		double yCornerBR = ((+0.5 - yHeight) -(-up*upCorrect+ (walking * walkCorrect))) *2;
		double rotRightSideZ = zcRight *cosine + xcRight * sine;
		
		
		
		
		double tex30 = 0;
		double tex40 = 8;
		//修建clip, the amount of clip
		double clip   = 0.5;

		if(rotLeftSideZ < clip && rotRightSideZ < clip){
			return;
		}
		if(rotLeftSideZ < clip){
			double clip0 = (clip - rotLeftSideZ)/(rotRightSideZ-rotLeftSideZ);
			rotLeftSideZ = rotLeftSideZ + (rotRightSideZ- rotLeftSideZ) * clip0;
			rotLeftSideX = rotLeftSideX + (rotRightSideX- rotLeftSideX) * clip0;
			tex30 = tex30 + (tex40 -tex30)*clip0;
			
		}
		
		if(rotRightSideZ < clip){
			double clip0 = (clip - rotLeftSideZ)/(rotRightSideZ-rotLeftSideZ);
			rotRightSideZ = rotLeftSideZ + (rotRightSideZ- rotLeftSideZ) * clip0;
			rotRightSideX = rotLeftSideX + (rotRightSideX- rotLeftSideX) * clip0;
			tex40 = tex30 + (tex40 -tex30)*clip0;
			
		}
		double xPixLeft = (rotLeftSideX / rotLeftSideZ * height + width/2);
		double xPixRight = (rotRightSideX / rotRightSideZ * height + width/2);
		
		
		if(xPixLeft >= xPixRight){
			return;
		}
		
		int xPixLeftInt = (int) (xPixLeft);
		int xPixRightInt = (int) (xPixRight);
		
		if(xPixLeftInt < 0){
			xPixLeftInt = 0;
		}
		if(xPixRightInt > width){
			xPixRightInt = width;
		}
		
		double yPixLeftTop = yCornerTL/rotLeftSideZ * height + height/2.0;
		double yPixLeftBottom = yCornerBL/rotLeftSideZ * height + height/2.0;
		double yPixRightTop = yCornerTR/rotRightSideZ * height + height/2.0;
		double yPixRightBottom = yCornerBR/rotRightSideZ * height + height/2.0;
		
		//episode 20
		double tex1 = 1/ rotLeftSideZ;
		double tex2 = 2/rotRightSideZ;
		double tex3 = tex30/rotLeftSideZ;
		double tex4 = tex40/rotRightSideZ - tex3;
		
		
		
		for (int x = xPixLeftInt; x< xPixRightInt;x++){
			double pixelRotation = (x - xPixLeft)/(xPixRight-xPixLeft);
			double zWall = (tex1 +(tex2-tex1)*pixelRotation);
			
			//这里是看不到后面的墙，让墙不透明
			if(zBufferWall[x] >zWall){
				continue;
			}
			zBufferWall[x]  = zWall;
			int xTexture = (int) ((tex3 + tex4* pixelRotation) / zWall);
					
			double yPixTop = yPixLeftTop + (yPixRightTop - yPixLeftTop) * pixelRotation;
			double yPixBottom = yPixLeftBottom + (yPixRightBottom - yPixLeftBottom) * pixelRotation;
			
			int yPixTopInt = (int) (yPixTop);
			int yPixBottomInt =(int)(yPixBottom);
			
			if(yPixTopInt < 0){
				yPixTopInt = 0;
			}
			if(yPixBottomInt >height){
				yPixBottomInt = height;
			}
			//这里的 ((xTexture & 7)+8） 是在画墙，用另一半8pixels （一共16pixles），地板和天花板是不一样的颜色，因为 ((xTexture & 7） 没有加8.伸不到9-16pixle
			for (int y = yPixTopInt; y<yPixBottomInt;y++){
				
					double pixelRotationY = (y - yPixTop) / (yPixBottom - yPixTop);
					int yTexture = (int)( 8*pixelRotationY);
					pixels[x + y * width] = Texture.floor.pixels[(int) (((xTexture & 7)+8) + (yTexture & 7) * 16)];
					//pixels[x + y*width] = xTexture*10 + yTexture*100;
					zBuffer[x + y*width] = 1 / (tex1 + (tex2-tex1) * pixelRotation) *8;
			}
		
			
			
		}
		
		
		
		
	}
	
	//this method increase distance of every piece of block. 
	// Add gredience 
	public void renderLimiter(){
		for (int i = 0; i < width * height;i++){
			int color 	= pixels[i];
			int brightness = (int)(renderDistance/(zBuffer[i]));
			
			// the minimum of brightness cannot go to negative 
			if (brightness <0){
				brightness = 0;
			}
			// the maximum of brightness cannot go to bigger than 255
			if(brightness >255){
				brightness = 255;
			}
			
			int r = (color>>16) & 0xff;
			int g = (color>>8) & 0xff;
			int b = (color) & 0xff;
			
			r = r * brightness /255;
			g = g * brightness /255;
			b = b * brightness /255; 
			
			pixels[i] = r<< 16 | g<< 8 | b;
					
		}
		
	}

}
