package com.fahim.shoot;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 *
 * @author fahim
 */

public class Game extends JFrame implements ActionListener{
	
	protected static Cursor CURSOR_LOCKED, CURSOR_UNLOCKED, CURSOR_DEFAULT;
	private final String fileCursorLocked = "images/cursors/locked.png",
			fileCursorUnlocked = "images/cursors/unlocked.png",
			fileCursorDefault = "images/cursors/default.png";
	
	
	protected final static int WIDTH = 900, HEIGHT = 500;
	
	protected final static int REFRESH_TIME = 65;
	
	protected static int highScore = 0;
        
        JLabel imagemenu;
	
	
	private final String fileScore =
			new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath()).getParent() +
			File.separator + "__score_data__.txt";
	
	private final String fileCredits = "data/credits.txt";
	
	private Font defaultFont;
	private JPanel menuPanel, highScorePanel, creditsPanel;
	private JButton playButton,playButton1,playButton2, quitButton, creditsButton, highScoreButton,
			backButton0, backButton1;
	private JTextField txtHighScore;
	
	public enum GameState {NEW, CONTINUE, OVER, HIGHSCORE, CREDITS, WAIT, QUIT,NEW1,NEW2,CONTINUE1,OVER1};
	public static GameState state;
	
	public Game() throws HeadlessException, IOException {
		this("Alien Shotter");
	}

	public Game(String title) throws HeadlessException, IOException {
		super(title);
		
		initCursors();
		loadHighScore();
		setBackButtons();
		
		defaultFont = new Font(Font.SANS_SERIF, Font.BOLD, 24);
		
		setMenuPanel();
		setHighScorePanel();
		setCreditsPanel();
		
		setLayout(null);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBackground(Color.WHITE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		pack();
	}

	private void setBackButtons() {
		backButton0 = createButton(new String[] {"images/buttons/B0.png","images/buttons/B1.png"});
		backButton1 = createButton(new String[] {"images/buttons/B0.png", "images/buttons/B1.png"});		
	}

	public void initCursors() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		
		Image lockedImage = toolkit.getImage(getClass().getResource(fileCursorLocked));
		Image unlockedImage = toolkit.getImage(getClass().getResource(fileCursorUnlocked));
		Image menuImage = toolkit.getImage(getClass().getResource(fileCursorDefault));
		
		CURSOR_LOCKED = toolkit.createCustomCursor(lockedImage, new Point(20, 20), "cursorLocked");
		CURSOR_UNLOCKED = toolkit.createCustomCursor(unlockedImage, new Point(20, 20), "cursorUnlocked");
		CURSOR_DEFAULT = toolkit.createCustomCursor(menuImage, new Point(16, 16), "cursorDefault");
	}
	
	public void loadHighScore() throws IOException {
		File file = new File(URLDecoder.decode(fileScore, "UTF-8"));
		
		if(!file.canRead()) {
			file.createNewFile();
			setHighScore(0);
		}
		else {
			BufferedReader reader = new BufferedReader(new FileReader(file));//(new InputStreamReader(getClass().getResourceAsStream(fileScore)));
			String line = reader.readLine();
			setHighScore( (line != null) ? Integer.parseInt(line) : 0 );
			reader.close();
		}
	}

	public static void setState(GameState state) {
		Game.state = state;
	}

	public static void setHighScore(int score) {
		if(score < 0)
			throw new IllegalArgumentException("High Score CANNOT BE a negative number!");
		if(score > highScore)
			highScore = score;
	}
	
	public static int getHighScore() {
		return highScore;
	}

	public void saveHighScore() throws IOException, SecurityException {
		File file = new File(URLDecoder.decode(fileScore, "UTF-8"));
		
		if(!file.canWrite())
			file.createNewFile();
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		
		writer.write(Integer.toString(getHighScore()));
		writer.close();
	}
	
	public void setMenuPanel() {
                //imagemenu = new JLabel(new ImageIcon("/home/fahim/NetBeansProjects/ShooterGame/src/com/fahim/shoot/images/backgrounds/backMenu.jpg"));
               // imagemenu.setBounds(0, 0, WIDTH, HEIGHT);
                
		menuPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));  
		menuPanel.setBounds(0, 0, WIDTH, HEIGHT);
		menuPanel.setBackground(Color.LIGHT_GRAY);
                //menuPanel.setOpaque(false);
		menuPanel.setCursor(Game.CURSOR_DEFAULT);
                //add(imagemenu);
		
		JPanel innerPanel = new JPanel(new GridLayout(5, 1, 2, 3));
		innerPanel.setPreferredSize(new Dimension(310, 440));
		innerPanel.setBackground(Color.WHITE);
                innerPanel.setOpaque(false);
		
		playButton = createButton(new String[] {"images/buttons/e0.png","images/buttons/e1.png"});
                playButton1 = createButton(new String[] {"images/buttons/hr0.png","images/buttons/hr1.png"});
                
		
		highScoreButton = createButton(new String[] {"images/buttons/H0.png","images/buttons/H1.png"});
		
		creditsButton = createButton(new String[] {"images/buttons/C0.png","images/buttons/C1.png"});
		
		quitButton = createButton(new String[] {"images/buttons/Q0.png","images/buttons/Q1.png"});
		innerPanel.add(playButton);
                innerPanel.add(playButton1);
		innerPanel.add(highScoreButton);
		innerPanel.add(creditsButton);
		innerPanel.add(quitButton);
		
		menuPanel.add(innerPanel);
	}
	
	public void setHighScorePanel() {
		highScorePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		highScorePanel.setBounds(0, 0, WIDTH, HEIGHT);
		highScorePanel.setBackground(Color.WHITE);
		highScorePanel.setCursor(Game.CURSOR_DEFAULT);
		
		JPanel innerPanel = new JPanel(new GridLayout(2, 1, 5, 5));
		innerPanel.setPreferredSize(new Dimension(800, 220));
		innerPanel.setBackground(Color.WHITE);
		
		JLabel lblMessage = new JLabel("High Score", JLabel.CENTER);
		lblMessage.setForeground(Color.BLACK);
		lblMessage.setFont(defaultFont);
		lblMessage.setHorizontalTextPosition(JLabel.CENTER);
		
		txtHighScore = new JTextField(Integer.toString(highScore), 6);
		txtHighScore.setFont(defaultFont);
		txtHighScore.setHorizontalAlignment(JTextField.CENTER);
		txtHighScore.setEditable(false);
		
		innerPanel.add(lblMessage, BorderLayout.NORTH);
		innerPanel.add(txtHighScore, BorderLayout.CENTER);
		
		highScorePanel.add(innerPanel);
		highScorePanel.add(backButton0);
	}
	
	public void setCreditsPanel() throws IOException {
		creditsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		creditsPanel.setBounds(0, 0, WIDTH, HEIGHT);
		creditsPanel.setBackground(Color.WHITE);
		creditsPanel.setCursor(Game.CURSOR_DEFAULT);
		
		String text = "CREDITS";

		if(getClass().getResourceAsStream(fileCredits) == null)
			throw new FileNotFoundException("credits.txt could not be found!");
		else {
			BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(fileCredits)));
			String line = " ";
			while(line != null) {
				text = text + "\n" + line;
				line = reader.readLine();
			}
			reader.close();
		}
		
		JTextPane txtPane = new JTextPane();
		txtPane.setText(text);
		txtPane.setEditable(false);
		
		StyledDocument doc = txtPane.getStyledDocument();
		
		SimpleAttributeSet body = new SimpleAttributeSet();
		SimpleAttributeSet header = new SimpleAttributeSet();
		
		StyleConstants.setAlignment(body, StyleConstants.ALIGN_CENTER);
		StyleConstants.setFontSize(body, 16);
		
		StyleConstants.setFontSize(header, 24);
		StyleConstants.setBold(header, true);
		StyleConstants.setAlignment(header, StyleConstants.ALIGN_CENTER);
		
		doc.setParagraphAttributes(7, doc.getLength(), body, false);
		doc.setParagraphAttributes(0, 7, header, false);
		
		JScrollPane scrollPane = new JScrollPane(txtPane);
		scrollPane.setPreferredSize(new Dimension(850, HEIGHT - 200));
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		creditsPanel.add(scrollPane);
		creditsPanel.add(backButton1);
	}
	
	
	private JButton createButton(String[] iconFiles) {
		ImageIcon[] icon = new ImageIcon[iconFiles.length];
		for(int i = 0; i < iconFiles.length; ++i)
			icon[i] = new ImageIcon(getClass().getResource(iconFiles[i]));

		JButton button = new JButton(icon[0]);
		button.setBorderPainted(false);
		button.setFocusable(true);
		button.setFocusPainted(true);
		button.setRolloverEnabled(true);
		button.setRolloverIcon(icon[1]);
		button.setPressedIcon(icon[0]);
		button.setContentAreaFilled(false);
		button.addActionListener(this);
		
		return button;
	}
	
	public void startGame() throws IOException {
		
		Thread t = new Thread(new Runnable() {		
			@Override
			public void run() {
				setState(GameState.WAIT);
				add(menuPanel);
				GameBoard board = null;
                                GameBoard1 board1 = null;
				
				while(!state.equals(GameState.QUIT)) {
					switch (state) {
					case WAIT:
						try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
                                                  System.out.println(e.getMessage());
						}
						break;
					case NEW:
						remove(menuPanel);
						repaint();
						try {
							board = new GameBoard();
						} catch (IOException e) {
							 System.out.println(e.getMessage());
						}
						add(board);
						pack();
						board.gameLoop();
						setState(GameState.WAIT);
						break;
                                                
                                                case NEW1:
						remove(menuPanel);
						repaint();
                                        
                                            try {
                                                board1 = new GameBoard1();
                                            } catch (IOException ex) {
                                                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                        
						add(board1);
						pack();
						board1.gameLoop();
						setState(GameState.WAIT);
						break;
                                                
					case CONTINUE:
						try {
							saveHighScore();
						} catch (IOException e) {
							System.out.println(e.getMessage());
						} catch (SecurityException e) {
							System.out.println(e.getMessage());
						}
						remove(board);
						repaint();
						try {
							board = new GameBoard();
						} catch (IOException e) {
							System.out.println(e.getMessage());
						}
						add(board);
						repaint();
						pack();
						board.gameLoop();
						setState(GameState.WAIT);
						break;
                                                
                                                case CONTINUE1:
						try {
							saveHighScore();
						} catch (IOException e) {
						         System.out.println(e.getMessage());
						} catch (SecurityException e) {
							 System.out.println(e.getMessage());
						}
						remove(board1);
						repaint();
						try {
							board1 = new GameBoard1();
						} catch (IOException e) {
							System.out.println(e.getMessage());
						}
						add(board1);
						repaint();
						pack();
						board1.gameLoop();
						setState(GameState.WAIT);
						break;
                                                
					case OVER:
						try {
							saveHighScore();
						} catch (IOException e) {
							 System.out.println(e.getMessage());
						} catch (SecurityException e) {
							 System.out.println(e.getMessage());
						}
						remove(board);
						repaint();
						add(menuPanel);
						repaint();
						pack();
						setState(GameState.WAIT);
						break;
                                                
                                                case OVER1:
						try {
							saveHighScore();
						} catch (IOException e) {
							System.out.println(e.getMessage());
						} catch (SecurityException e) {
						        System.out.println(e.getMessage());
						}
						remove(board1);
						repaint();
						add(menuPanel);
						repaint();
						pack();
						setState(GameState.WAIT);
						break;
                                                
					case HIGHSCORE:
						txtHighScore.setText(Integer.toString(getHighScore()));
						remove(menuPanel);
						repaint();
						add(highScorePanel);
						repaint();
						pack();
						setState(GameState.WAIT);
						break;
					case CREDITS:
						remove(menuPanel);
						repaint();
						add(creditsPanel);
						repaint();
						pack();
						setState(GameState.WAIT);
						break;
					case QUIT:
						break;
					}
				}
				
				dispose();
				System.exit(0);
			}
		});
		t.start();
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(playButton))
			setState(GameState.NEW);
                else if(e.getSource().equals(playButton1))
                        setState(GameState.NEW1);
		
		else if(e.getSource().equals(highScoreButton))
			setState(GameState.HIGHSCORE);
		
		else if(e.getSource().equals(backButton0) || e.getSource().equals(backButton1)) {
			remove(((JButton) e.getSource()).getParent());
			repaint();
			add(menuPanel);
			pack();
			setState(GameState.WAIT);
		}
		
		else if(e.getSource().equals(quitButton))
			setState(GameState.QUIT);
		
		else if(e.getSource().equals(creditsButton))
			setState(GameState.CREDITS);
	}
	
        
        
}
