package com.fahim.shoot;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.fahim.shoot.Game.GameState;

/**
 *
 * @author fahim
 */public class GameBoard extends JPanel {
	private final String fileBackground = "images/backgrounds/background2.png";
	private GeneratorAlien generator;
	protected static boolean isGameOver = false;
	int score;
	
	private boolean isStopped;
	
	private AudioClip bangClip;
	private Gun cannon;
	private JLabel lblScore;
	private JPanel headerPanel;
	private JButton restartButton, stopButton;
	private Timer mainTimer;
	private Font defaultFont;
	private JCheckBox effectOn;
	
	public GameBoard() throws IOException {
		isStopped = false;

		generator = new GeneratorAlien();
		isGameOver = false; 
		score = 0;
		defaultFont = new Font(Font.SERIF, Font.BOLD, 24);
		
		setEffectOn();
		
		URL clipUrl = getClass().getResource("sound/M1 Garand Single-SoundBible.com-1941178963.wav");
		if(clipUrl == null) {
			effectOn.setSelected(false);
			effectOn.setEnabled(false);
		}
		else
			bangClip = Applet.newAudioClip(clipUrl);
		
		setBounds(0, 0, Game.WIDTH, Game.HEIGHT);
		
		setLayout(null);
		
		setCursor(Game.CURSOR_UNLOCKED);
		setHeader();
		setCannon();
	}
	
	private void setEffectOn() {
		effectOn = new JCheckBox("Sound Effects    ", true);
		effectOn.setBackground(new Color(0, 0, 0, 0)); 
		effectOn.setFont(defaultFont);
		effectOn.setFocusable(false);
	}
	
	private void setHeader() {
		JLabel scoreMsg = new JLabel("Your Score: ");
		scoreMsg.setForeground(Color.BLACK);
		scoreMsg.setFont(defaultFont);
		
		lblScore = new JLabel(Integer.toString(score));
		lblScore.setForeground(Color.BLACK);
		lblScore.setFont(defaultFont);
		
		headerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		headerPanel.setBounds(0, 0, Game.WIDTH, 50);
		headerPanel.setOpaque(true);
		headerPanel.setBackground(new Color(0, 0, 0, 0));
		
		headerPanel.add(effectOn);
		headerPanel.add(scoreMsg);
		headerPanel.add(lblScore);
		
		//Add restart and stop buttons
		BufferedImage[] buttonIcons = null;
		try {
			buttonIcons = SpriteSheet.createSprites("images/buttons/innerbuttons.png", 2, 3);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon[] icons = new ImageIcon[buttonIcons.length];
		for(int i = 0; i < buttonIcons.length; ++i)
			icons[i] = new ImageIcon(buttonIcons[i]);

		restartButton = createButton(icons[0], icons[1], icons[2]);
		stopButton = createButton(icons[3], icons[4], icons[5]);
		
		headerPanel.add(restartButton);
		headerPanel.add(stopButton);

		add(headerPanel);
		
		headerPanel.setVisible(true);
	}
	
	
	private JButton createButton(Icon icon0, Icon icon1, Icon icon2) {
//		ImageIcon[] icon = new ImageIcon[iconFiles.length];
//		for(int i = 0; i < iconFiles.length; ++i)
//			icon[i] = new ImageIcon(getClass().getResource(iconFiles[i]));

		JButton button = new JButton(icon0);
		button.setPreferredSize(new Dimension(icon0.getIconWidth(), icon0.getIconHeight()));
		button.setBorderPainted(false);
		button.setFocusable(true);
		button.setFocusPainted(false);
		button.setRolloverEnabled(true);
		button.setRolloverIcon(icon1);
		button.setPressedIcon(icon2);
		button.setContentAreaFilled(false);
		button.addActionListener(new InnerButtonListener());
		
		return button;
	}
	
	private void setCannon() {
		cannon = new Gun();
		add(cannon);
		
		addMouseMotionListener(new MouseMotionListener() {		
			@Override
			public void mouseMoved(MouseEvent e) {
				cannon.rotate(e.getX(), e.getY(), false);
			}		
			@Override
			public void mouseDragged(MouseEvent arg0) {
			}
		});
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Gun.setFire(true);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				Gun.setFire(true);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				Gun.setFire(false);
			}		
		});
	}
	
	public Thread generator() {
		
		for(int i = 0; i < 6; ++i)
			add(generator.generateNewEnemy());
		
		
		return new Thread(new Runnable() {	

			@Override
			public void run() {
				int s = 1000;
				while(!isGameOver && !isStopped) {
					try {
						Thread.sleep(s);
					} catch (InterruptedException e) {
						JOptionPane.showMessageDialog(null,
								"We are sorry about that!\nError: " + e.getMessage(),
								"Opps!! Something went wrong!", JOptionPane.ERROR_MESSAGE);
					}
					if(isStopped)
						return;
					if(!isGameOver)
						add(generator.generateNewEnemy());
					if(s > Game.REFRESH_TIME + 20)
						s -= 5;
					else
						s = Game.REFRESH_TIME + 5;
				}
			}});
	}
	
	public void gameLoop() {
		generator().start();
		mainTimer = new Timer(Game.REFRESH_TIME, new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				repaint();
				if(isGameOver) {
					gameOver();
					return;
				}
				for(Component e : getComponents()) {
					if(e instanceof AlienCommonClass){
						AlienCommonClass i = (AlienCommonClass) e;
						if(!i.isAlive()) {
							if(effectOn.isSelected())
								bangClip.play();
							score += i.getScorePoint();
							lblScore.setText(Integer.toString(score));
							remove(e);
						}
					}
				}
			}
		});
		mainTimer.start();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(fileBackground)), 0, 0, null);
	}
	
	public void gameOver() {
		mainTimer.stop();
		for(Component c : getComponents()) {
			if(c instanceof AlienCommonClass)
				((AlienCommonClass) c).getAnimationTimer().stop();
			remove(c);
		}
		
		int selectedOption = JOptionPane.showConfirmDialog(this,
				("Your Score: " + score + "\nDo you want to play a new Game?"),
				"GAME OVER", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		
		Game.setHighScore(score);
		
		switch (selectedOption) {
		case JOptionPane.YES_OPTION:
			Game.setState(GameState.CONTINUE);
			break;

		case JOptionPane.NO_OPTION:
			Game.setState(GameState.OVER);
			break;
		}
	}
	
	private class InnerButtonListener implements ActionListener {
		public void gameStop() {
			isStopped = true;
			mainTimer.stop();
			for(Component c : getComponents()) {
				if(c instanceof AlienCommonClass)
					((AlienCommonClass) c).getAnimationTimer().stop();
				remove(c);
			}
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == restartButton) {
				gameStop();
				Game.setState(GameState.CONTINUE);
			}
			else if(e.getSource() == stopButton) {
				gameStop();
				Game.setState(GameState.OVER);
			}
		}
	}
}
