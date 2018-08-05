/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fahim.shoot;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 *
 * @author bdafahim
 */
public class AlienWeak1 extends AlienMain1 {
    public AlienWeak1(BufferedImage[] frames, int frameLivingLimit, int x, int y) {
		super(frames, frameLivingLimit, x, y);
		
		setMoveSpeed(5);
	}
        
	public AlienWeak1(String filePath, int row, int col, int frameLivingLimit,
			int x, int y) throws IOException {
		this(SpriteSheet.createSprites(filePath, row, col), frameLivingLimit, x, y);
	}

	
	@Override
	public void shooting() {
            
		manIsDown = true;
	}
}
