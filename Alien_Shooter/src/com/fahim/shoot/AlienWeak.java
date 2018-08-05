package com.fahim.shoot;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 *
 * @author fahim
 */

public class AlienWeak extends AlienMain {
	
    
	public AlienWeak(BufferedImage[] frames, int frameLivingLimit, int x, int y) {
		super(frames, frameLivingLimit, x, y);
		
		setMoveSpeed(5);
	}
        
	public AlienWeak(String filePath, int row, int col, int frameLivingLimit,
			int x, int y) throws IOException {
		this(SpriteSheet.createSprites(filePath, row, col), frameLivingLimit, x, y);
	}

	
	@Override
	public void shooting() {
            
		manIsDown = true;
	}

}
