
package com.fahim.shoot;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 *
 * @author fahim
 */

public class GeneratorAlien {
	private final String fileGreenAlien = "images/creatures/alien3.png",
                        file1 = "images/creatures/alien12.png",
                        file2 = "images/creatures/alien222.png",
			fileBlueAlien = "images/creatures/alien5.png";
	
	private BufferedImage[] greenFrames, blueFrames,f1rames,f2rames;
	
	private Random rand;
	public GeneratorAlien() throws IOException {
		rand = new Random(1000000000);
		greenFrames = SpriteSheet.createSprites(fileGreenAlien, 2, 6);
		blueFrames = SpriteSheet.createSprites(fileBlueAlien, 2, 6);
                f1rames = SpriteSheet.createSprites(file1, 2, 5);
                f2rames = SpriteSheet.createSprites(file2, 2, 6);
	}
	
	public AlienMain generateNewEnemy() {
		
		int creatureType = rand.nextInt(2);
		
		switch (creatureType) {
		case 0:
			return new AlienWeak(greenFrames, 6, (rand.nextInt(100) + (Game.WIDTH - 100)), rand.nextInt(Game.HEIGHT - 200) + 50);
		case 1:
			return new AlienWeak(blueFrames, 6, (rand.nextInt(100) + (Game.WIDTH - 100)), rand.nextInt(Game.HEIGHT - 200) + 50);                
		}
		return null;
	}
        
        public AlienMain1 generateNewEnemy1(){
            int creatureType = rand.nextInt(2);
		
		switch (creatureType) {
		case 0:
			return new AlienWeak1(f1rames, 5, (rand.nextInt(100) + (Game.WIDTH - 100)), rand.nextInt(Game.HEIGHT - 200) + 50);
		case 1:
			return new AlienWeak1(f2rames, 6, (rand.nextInt(100) + (Game.WIDTH - 100)), rand.nextInt(Game.HEIGHT - 200) + 50);                
		}
		return null;
        }
    

}
