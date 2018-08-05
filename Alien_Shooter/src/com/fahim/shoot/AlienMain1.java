/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fahim.shoot;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author bdafahim
 */
public abstract class AlienMain1 extends JPanel implements AlienCommonClass, MouseListener{
    	private final int Score = 100;
	
	protected int deadLine;
	protected boolean manIsDown;
	protected int x, y, width, height;
	protected int moveSpeed;
	
	protected BufferedImage[] frames;
	protected int frameLivingLimit, frameDeadLimit, frameCurrent, frameStart;
	
	protected Timer animationTimer;
	
	public AlienMain1(BufferedImage[] frames,int frameLivingLimit, int x, int y) {
		
		setFrames(frames, frameLivingLimit);
		setX(x);
		setY(y);
		
		width = frames[0].getWidth();
		height = frames[0].getHeight();
		
		manIsDown = false;

		deadLine = 100;
		
		// Set size and position of the panel.
		setPreferredSize(new Dimension(width, height));
		setBounds(x, y, width, height);
		
		setOpaque(true);
		setBackground(new Color(0, 0, 0, 0));
		
		addMouseListener(this);
		
		animationTimer = new Timer(Game.REFRESH_TIME, new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!GameBoard1.isGameOver)
					update();
			}});
		animationTimer.start();
	}
	
	public AlienMain1(String filePath, int row, int col, int frameLivingLimit, int x, int y) 
			throws IOException {
		this(SpriteSheet.createSprites(filePath, row, col), frameLivingLimit, x, y);
	}

	public void setFrames(BufferedImage[] frames, int frameLivingLimit) {
		this.frames = frames;
		if(frameLivingLimit > 0)
			this.frameLivingLimit = frameLivingLimit;
		else
			throw new IllegalArgumentException("frameLivingLimit CANNOT BE zero or a negative number!");
		this.frameDeadLimit = frames.length;
		this.frameStart = 0;
		this.frameCurrent = frameStart;
	}
	
	public void setY(int y) {
		if(y >= 0)
			this.y = y;
		else
			throw new IllegalArgumentException("y CANNOT BE negative!");		
	}

	public void setX(int x) {
		if(x >= 0)
			this.x = x;
		else
			throw new IllegalArgumentException("x CANNOT BE negative!");
	}
	
	public void setMoveSpeed(int moveSpeed) {
		if(moveSpeed >= 0)
			this.moveSpeed = moveSpeed;
		else
			throw new IllegalArgumentException("moveSpeed CANNOT BE negative!");
	}

	public int getMoveSpeed() {
		return moveSpeed;
	}
	
	public abstract void shooting();
	
	@Override
	public Timer getAnimationTimer() {
		return animationTimer;
	}
	
	@Override
	public void move() {
		if(!manIsDown){
			if(x > deadLine)
				x -= moveSpeed;
			else {
				x = deadLine;
				GameBoard1.isGameOver = true; 
				animationTimer.stop();
			}
			setLocation(x, y); 
		}
	}

	@Override
	public boolean isAlive() {
		return (frameCurrent < frameDeadLimit);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g); 
		Graphics2D g2d = (Graphics2D) g;
		
		if(isAlive())
			g2d.drawImage(frames[frameCurrent], 0, 0, null);
		else
			setForeground(new Color(0,0,0,0));
	}
	
	@Override
	public void update() {
		move();
		
		if((++frameCurrent == frameLivingLimit) && !manIsDown)
			frameCurrent = frameStart;
		else if(manIsDown) 
			frameCurrent = (frameCurrent < frameLivingLimit) ? frameLivingLimit : (frameCurrent + 1);
		
		repaint();
	}

	@Override
	public int getScorePoint() {
		return this.Score;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(GameBoard1.isGameOver)
			return;
		Gun.setFire(true);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		setCursor(Game.CURSOR_LOCKED);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		setCursor(Game.CURSOR_UNLOCKED);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(GameBoard1.isGameOver)
			return;
		Gun.setFire(true);
		shooting();
		update();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(GameBoard1.isGameOver)
			return;
		Gun.setFire(false);
	}
}
