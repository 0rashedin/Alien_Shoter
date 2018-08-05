package com.fahim.shoot;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 *
 * @author fahim
 */

public class SpriteSheet {
	
	public SpriteSheet() {		
	}
	
	
	public static BufferedImage[] createSprites(String filePath, int row, int col) 
			throws IOException {
		if(row <= 0 || col <= 0)
			throw new IllegalArgumentException("row and col must be positive!");
		
		
		BufferedImage[] sprites = new BufferedImage[row * col];
		
		
		BufferedImage spriteSheet = ImageIO.read(SpriteSheet.class.getResourceAsStream(filePath));
		
		
		int width = spriteSheet.getWidth() / col;
		int height = spriteSheet.getHeight() / row;

		
		for(int i = 0; i < row; ++i)
			for(int j = 0; j < col; ++j)
				sprites[(i * col) + j] = spriteSheet.getSubimage(j * width, i * height, width, height);
		
		return sprites;
	}
}
