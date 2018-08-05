package com.fahim.shoot;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JPanel;

/**
 *
 * @author fahim
 */

public class Gun extends JPanel {
	
	private final String fileCannonImage = "images/cannons/canon4.png",
			fileCannonFireImage = "images/cannons/canon2.png";
			//fileBackGround = "images/backgrounds/canon2.png";
	
	private final int width = 150, height = 200;
	
	private Image cannonImage, cannonFireImage;
	
	
	private int positionY, imagePosY;
	private double angle;
	private static boolean fire;
	
	public Gun() {
		
		positionY = (Game.HEIGHT / 2) - (height / 2) + 50;
		
		
		setBounds(0, positionY, width, height);

		setBackground(Color.white);
                setOpaque(false);
		 
		setFire(false);
		setImages();
	}

	private void setImages() {
		cannonImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource(fileCannonImage));
		cannonFireImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource(fileCannonFireImage));
		
		
		while(cannonImage.getHeight(null) == -1)
			System.out.println("class Canon: Waiting for image height...");
		
		imagePosY = (height / 2) - (cannonImage.getHeight(null) / 2) - 15;
		angle = 0;
	}

	public static void setFire(boolean f) {
		fire = f;
	}
	
	public void rotate(int x, int y, boolean fire) {
		setFire(fire);
		
		angle = Math.atan2(y - (positionY + imagePosY), x);
		
		repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2d = (Graphics2D) g;
		
		
		g2d.rotate(angle, 15, imagePosY + 60);
		
		if(!fire) 	
			g2d.drawImage(cannonImage, 0, imagePosY, null);
		else
			g2d.drawImage(cannonFireImage, 0, imagePosY, null);
	}
	
//	@Override
//	protected void paintComponent(Graphics g) {
//		super.paintComponent(g);
//		// Draw background of the panel.
//		g.drawImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(fileBackGround)), 0, 0, null);
//	}
}
