package com.fahim.shoot;

import javax.swing.Timer;

/**
 *
 * @author fahim
 */

public interface AlienCommonClass {
	public void move();
	public boolean isAlive();
	public void update();
	public int getScorePoint();
	Timer getAnimationTimer();
}
