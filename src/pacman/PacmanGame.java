package pacman;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import code.AnimationPanel;

public class PacmanGame extends AnimationPanel {
	private final static int WIDTH = 560;
	private final static int HEIGHT = 720;
	
	private final int PADDING_TOP = 60;
	private final int INTERVAL = 20;

	private final int MODE = 0;
	private final int SINGLEPLAYER = 1;
	private final int SINGLEPLAYER_START = 11;
	private final int SINGLEPLAYER_GAME = 111;
	private final int MULTIPLAYER = 2;
	private final int MULTIPLAYER_START = 22;
	private final int MULTIPLAYER_GAME = 222;
	private final int LEADERBOARD = 3;
	private final int KEYBOARD = 4;
	
	private int gameState;
	private int score;
	private int highScore;
	private String[][] keyboard =
		{
			{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"},
			{"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T"},
			{"U", "V", "W", "X", "Y", "Z", ".", "-", "DEL", "END"}
		};
	private String initials;
	private int letterRow;
	private int letterCol;
	private String[][] leaderboard;
	private int stage;
	private Pacman pacman;
	private ArrayList<Ghost> ghosts;
	private ArrayList<Coin> coins;
	private ArrayList<PowerCoin> powerCoins;
	private ArrayList<Wall> walls;
	private Fruit fruit;
	private Map map;
	private Resources resources;
	
	private int markerIndex;
	private int modeTimer;
	private int highlight;
	
	private int homeTimer;
	private Label copyright = new Label("\u00a9");
	
	private int gameTimer;
	private int pacmanDeathTimer;
	
	private int ghostDeathTimer;
	private boolean ghostEaten;
	private int ghostsFrightenedTimer;
	private boolean ghostsFrightened;
	private int ghostsEaten;
	
	private int stageOverTimer;
	private boolean stageOver;
	
	private boolean lifeLost;
	
	private int gameOverTimer;
	private boolean gameOver;
	
	private int ghostHomeX;
	private int ghostHomeY;
	
	private int scoreX;
	private int scoreY;
	
	public PacmanGame() {
		super("Pacman", WIDTH + 16, HEIGHT + 38);
		resources = new Resources();
		resources.load();
		map = new Map();
		resources.createFile();
		highScore = resources.readScore(0);
		resetMode();
	}
	
	public void resetMode() {
		gameState = MODE;
		markerIndex = SINGLEPLAYER;
		modeTimer = 0;
		pacman = new Pacman();
		ghosts = new ArrayList<>();
		coins = new ArrayList<>();
		powerCoins = new ArrayList<>();
		resources.createFile();
		highScore = resources.readScore(0);
		for (int i = 0; i < 5; i++)
			coins.add(new Coin((i * INTERVAL) + 190, 318));
		powerCoins.add(new PowerCoin(298, 318));
		ghosts.add(new Ghost(Ghost.RED));
		ghosts.add(new Ghost(Ghost.PINK));
		ghosts.add(new Ghost(Ghost.BLUE));
		ghosts.add(new Ghost(Ghost.ORANGE));
	}
	
	/**
	 * Resets the title screen, causing the animation to loop.
	 */
	public void resetSinglePlayer() {
		gameState = SINGLEPLAYER;
		homeTimer = 0;
		ghostEaten = false;
		ghostDeathTimer = 0;
		ghostsEaten = 0;
		pacman = new Pacman();
		ghosts = new ArrayList<>();
		powerCoins = new ArrayList<>();
		powerCoins.add(new PowerCoin(70, 398));
		pacman.setLoc(570, 390);
		pacman.setDirection(Pacman.LEFT);
		ghosts.add(new Ghost(Ghost.RED));
		ghosts.add(new Ghost(Ghost.PINK));
		ghosts.add(new Ghost(Ghost.BLUE));
		ghosts.add(new Ghost(Ghost.ORANGE));
		for (int i = 0; i < ghosts.size(); i++) {
			Ghost g = ghosts.get(i);
			g.setLoc((i * 40) + 630, 390);
			g.setDirection(Ghost.LEFT);
		}
	}
	
	/**
	 * Resets the game.
	 */
	public void resetSinglePlayerGame() {
		gameState = SINGLEPLAYER_GAME;
		resetTimers();
		gameTimer = 0;
		lifeLost = true;
		score = 0;
		stage = 0;
		pacman = new Pacman();
		ghosts = new ArrayList<>();
		coins = new ArrayList<>();
		powerCoins = new ArrayList<>();
		fruit = new Fruit();
		walls = new ArrayList<>();
		pacman.setNextDirection(Pacman.LEFT);
		ghosts.add(new Ghost(Ghost.RED));
		ghosts.add(new Ghost(Ghost.BLUE));
		ghosts.add(new Ghost(Ghost.PINK));
		ghosts.add(new Ghost(Ghost.ORANGE));
		int ghostIndex = 0;
		for (int y = 0; y < map.getMap().length; y++) {
			for (int x = 0; x < map.getMap()[0].length; x++) {
				switch (map.getMap()[y][x]) {
				case Map.COIN:
					coins.add(new Coin(x * INTERVAL, y * INTERVAL + PADDING_TOP));
					break;
				case Map.WALL:
					walls.add(new Wall(x * INTERVAL, y * INTERVAL + PADDING_TOP));
					break;
				case Map.GATE:
					Wall wall = new Wall(x * INTERVAL, y * INTERVAL + PADDING_TOP);
					wall.setGate(true);
					walls.add(wall);
					break;
				case Map.POWER_COIN:
					powerCoins.add(new PowerCoin(x * INTERVAL, y * INTERVAL + PADDING_TOP));
					break;
				case Map.GHOST_HOME:
					ghostHomeX = x * INTERVAL;
					ghostHomeY = y * INTERVAL - 8 + PADDING_TOP;
				case Map.GHOST_SPAWN:
					ghosts.get(ghostIndex).setLoc(x * INTERVAL, y * INTERVAL - 8 + PADDING_TOP);
					ghostIndex++;
					break;
				case Map.PACMAN_SPAWN:
					pacman.setLoc(x * INTERVAL, y * INTERVAL - 8 + PADDING_TOP);
					fruit.setLoc(x * INTERVAL, y * INTERVAL - 8 + PADDING_TOP);
				}
			}
		}
		ghosts.get(0).setDirection(Ghost.LEFT);
		ghosts.get(1).setDirection(Ghost.UP);
		ghosts.get(2).setDirection(Ghost.DOWN);
		ghosts.get(3).setDirection(Ghost.UP);
		for (int i = 1; i < ghosts.size(); i++) {
			ghosts.get(i).setCaged(true);
			ghosts.get(i).setSpeed(2);
		}
		
		resources.playAudio(Resources.START);
	}
	
	public void resetSinglePlayerStage() {
		resetTimers();
		gameTimer = 120;
		ghosts = new ArrayList<>();
		coins = new ArrayList<>();
		powerCoins = new ArrayList<>();
		walls = new ArrayList<>();
		pacman.setNextDirection(Pacman.LEFT);
		ghosts.add(new Ghost(Ghost.RED));
		ghosts.add(new Ghost(Ghost.BLUE));
		ghosts.add(new Ghost(Ghost.PINK));
		ghosts.add(new Ghost(Ghost.ORANGE));
		int ghostIndex = 0;
		for (int y = 0; y < map.getMap().length; y++) {
			for (int x = 0; x < map.getMap()[0].length; x++) {
				switch (map.getMap()[y][x]) {
				case Map.COIN:
					coins.add(new Coin(x * INTERVAL, y * INTERVAL + PADDING_TOP));
					break;
				case Map.WALL:
					walls.add(new Wall(x * INTERVAL, y * INTERVAL + PADDING_TOP));
					break;
				case Map.GATE:
					Wall wall = new Wall(x * INTERVAL, y * INTERVAL + PADDING_TOP);
					wall.setGate(true);
					walls.add(wall);
					break;
				case Map.POWER_COIN:
					powerCoins.add(new PowerCoin(x * INTERVAL, y * INTERVAL + PADDING_TOP));
					break;
				case Map.GHOST_HOME:
					ghostHomeX = x * INTERVAL;
					ghostHomeY = y * INTERVAL - 8 + PADDING_TOP;
				case Map.GHOST_SPAWN:
					ghosts.get(ghostIndex).setLoc(x * INTERVAL, y * INTERVAL - 8 + PADDING_TOP);
					ghostIndex++;
					break;
				case Map.PACMAN_SPAWN:
					pacman.setLoc(x * INTERVAL, y * INTERVAL - 8 + PADDING_TOP);
				}
			}
		}
		ghosts.get(0).setDirection(Ghost.LEFT);
		ghosts.get(1).setDirection(Ghost.UP);
		ghosts.get(2).setDirection(Ghost.DOWN);
		ghosts.get(3).setDirection(Ghost.UP);
		for (int i = 1; i < ghosts.size(); i++) {
			ghosts.get(i).setCaged(true);
			ghosts.get(i).setSpeed(2);
		}
	}
	
	public void resetSinglePlayerDeath() {
		resetTimers();
		gameTimer = 120;
		ghosts = new ArrayList<>();
		lifeLost = true;
		pacman.setNextDirection(Pacman.LEFT);
		ghosts.add(new Ghost(Ghost.RED));
		ghosts.add(new Ghost(Ghost.BLUE));
		ghosts.add(new Ghost(Ghost.PINK));
		ghosts.add(new Ghost(Ghost.ORANGE));
		int ghostIndex = 0;
		for (int y = 0; y < map.getMap().length; y++) {
			for (int x = 0; x < map.getMap()[0].length; x++) {
				switch (map.getMap()[y][x]) {
				case Map.GHOST_HOME:
					ghostHomeX = x * INTERVAL;
					ghostHomeY = y * INTERVAL - 8 + PADDING_TOP;
				case Map.GHOST_SPAWN:
					ghosts.get(ghostIndex).setLoc(x * INTERVAL, y * INTERVAL - 8 + PADDING_TOP);
					ghosts.get(ghostIndex).setFrightened(false);
					ghostIndex++;
					break;
				case Map.PACMAN_SPAWN:
					pacman.setLoc(x * INTERVAL, y * INTERVAL - 8 + PADDING_TOP);
				}
			}
		}
		ghosts.get(0).setDirection(Ghost.LEFT);
		ghosts.get(1).setDirection(Ghost.UP);
		ghosts.get(2).setDirection(Ghost.DOWN);
		ghosts.get(3).setDirection(Ghost.UP);
		for (int i = 1; i < ghosts.size(); i++) {
			ghosts.get(i).setCaged(true);
			ghosts.get(i).setSpeed(2);
		}
	}
	
	public void resetTimers() {
		stageOverTimer = 0;
		stageOver = false;
		gameTimer = 0;
		gameOverTimer = 0;
		gameOver = false;
		ghostEaten = false;
		ghostDeathTimer = 0;
		ghostsFrightened = false;
		ghostsFrightenedTimer = 0;
		ghostsEaten = 0;
	}
	
	/**
	 * Method is called when a PowerCoin is eaten and starts a timer for the time
	 * that the ghosts stay frightened.
	 */
	public void startFrightenedPhase() {
		ghostsFrightenedTimer = 0;
		ghostsEaten = 0;
		ghostsFrightened = true;
		for (Ghost ghost : ghosts) {
			if (!ghost.getDead()) {
				if (!ghost.getFrightened())
					ghost.setDirection(ghost.getOppositeDirection());
				ghost.setSpeed(2);
				ghost.setFrightened(true);
			}
		}
	}
	
	public void resetLeaderboard() {
		gameState = LEADERBOARD;
		leaderboard = new String[11][3];
		leaderboard = resources.getLeaderboard();
	}
	
	protected void renderFrame(Graphics g) {
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		g.setFont(Resources.FONT_ARCADE);
		
		switch (gameState) {
		
		case MODE:
			
			g.drawImage(Resources.TITLE, 5, 80, 552, 144, this);
			if (modeTimer > 40) {
				g.setColor(Color.WHITE);
				g.drawString("1 PLAYER", 200, 440);
			}
			if (modeTimer > 80)
				g.drawString("2 PLAYERS", 200, 480);
			if (modeTimer > 120)
				g.drawString("LEADERBOARD", 200, 520);
			if (modeTimer > 160) {
				g.setColor(Resources.PINK);
				drawCenter(copyright.getText() + " 2022 JIANING WANG", 630, g);
				if (modeTimer > 200) {
					if (frameNumber % 20 < 10)
						g.drawImage(Resources.MARKER, 150, (markerIndex * 40) + 380, 20, 20, this);
				} else
					g.drawImage(Resources.MARKER, 150, (markerIndex * 40) + 380, 20, 20, this);
			}
			if (modeTimer > 200) {
				switch (markerIndex) {
				case SINGLEPLAYER:
					
					for (int i = 0; i < coins.size(); i++) {
						coins.get(i).setX((i * INTERVAL) + 190);
						coins.get(i).draw(g);
					}
					
					g.setColor(Resources.COIN_COLOR);
					for (Coin c : coins)
						c.draw(g);
					
					pacman.setDirection(Pacman.RIGHT);
					pacman.setLoc(150, 310);
					
					if (frameNumber % 3 == 0)
						pacman.animate();
					pacman.draw(g, this);
					
					for (int i = 0; i < 3; i++) {
						Ghost ghost = ghosts.get(i);
						ghost.setLoc((i * 40) + 295, 310);
						ghost.setFrightened(true);
						if (frameNumber % 6 == 0)
							ghost.animate();
						ghost.draw(g, this);
					}
					
					break;
					
				case MULTIPLAYER:
					
					for (int i = 0; i < 3; i++) {
						coins.get(i).setX((i * INTERVAL) + 320);
						coins.get(i).draw(g);
					}
					
					if (frameNumber % 20 < 10)
						powerCoins.get(0).draw(g, this);
					
					pacman.setDirection(Pacman.LEFT);
					pacman.setLoc(380, 310);
					
					if (frameNumber % 3 == 0)
						pacman.animate();
					pacman.draw(g, this);
					
					Ghost ghost = ghosts.get(0);
					ghost.setDirection(Ghost.RIGHT);
					ghost.setLoc(150, 310);
					ghost.setFrightened(false);
					if (frameNumber % 6 == 0)
						ghost.animate();
					ghost.draw(g, this);
					
					break;
					
				case LEADERBOARD:
					
					g.drawImage(Resources.PODIUM, 220, 270, 120, 120, this);
					
					pacman.setDirection(Pacman.LEFT);
					pacman.setLoc(260, 270);
					
					if (frameNumber % 3 == 0)
						pacman.animate();
					pacman.draw(g, this);
					
					ghosts.get(2).setLoc(220, 293);
					ghosts.get(2).setDirection(Ghost.RIGHT);
					ghosts.get(1).setLoc(300, 318);
					
					for (int i = 1; i < 3; i++) {
						Ghost gh = ghosts.get(i);
						gh.setFrightened(false);
						if (frameNumber % 6 == 0)
							gh.animate();
						gh.draw(g, this);
						
					}
				}
			}
			
			if (modeTimer <= 201)
				modeTimer++;
			
			break;
		
		case SINGLEPLAYER:
			
			if (homeTimer > 20) {
				g.setColor(Color.WHITE);
				g.drawString("1UP", 60, 20);
				drawRight("" + score, 140, 40, g);
				drawCenter("HIGH SCORE", 20, g);
				drawRight("" + highScore, 340, 40, g);
				g.drawString("2UP", 440, 20);
				g.drawString("CHARACTER / NICKNAME", 140, 120);
			}
			if (homeTimer > 60)
				g.drawImage(Resources.GHOST_RED_RIGHT_2, 70, 130, 40, 40, this);
			if (homeTimer > 100) {
				g.setColor(Resources.RED);
				g.drawString("-SHADOW", 140, 160);
			}
			if (homeTimer > 120)
				g.drawString("\"BLINKY\"", 360, 160);
			
			if (homeTimer > 140)
				g.drawImage(Resources.GHOST_PINK_RIGHT_2, 70, 185, 40, 40, this);
			if (homeTimer > 180) {
				g.setColor(Resources.PINK);
				g.drawString("-SPEEDY", 140, 215);
			}
			if (homeTimer > 200)
				g.drawString("\"PINKY\"", 360, 215);
			if (homeTimer > 220)
				g.drawImage(Resources.GHOST_BLUE_RIGHT_2, 70, 240, 40, 40, this);
			if (homeTimer > 260 ) {
				g.setColor(Resources.BLUE);
				g.drawString("-BASHFUL", 140, 270);
			}
			if (homeTimer > 280)
				g.drawString("\"INKY\"", 360, 270);
			if (homeTimer > 300)
				g.drawImage(Resources.GHOST_ORANGE_RIGHT_2, 70, 295, 40, 40, this);
			if (homeTimer > 340) {
				g.setColor(Resources.ORANGE);
				g.drawString("-POKEY", 140, 325);
			}
			if (homeTimer > 360)
				g.drawString("\"CLYDE\"", 360, 325);
			
			if (homeTimer > 400) {
				g.setColor(Resources.COIN_COLOR);
				g.fillRect(205, 492, 5, 5);
				if (homeTimer > 480) {
					if (frameNumber % 20 < 10)
						g.drawImage(Resources.COIN_POWER, 197, 522, 20, 20, this);
				} else
					g.drawImage(Resources.COIN_POWER, 197, 522, 20, 20, this);
				
				g.setColor(Color.WHITE);
				g.drawString("10", 240, 500);
				g.drawString("50", 240, 540);
				g.setFont(Resources.FONT_SPACEBIT);
				g.drawString("PTS", 300, 500);
				g.drawString("PTS", 300, 540);
				g.setFont(Resources.FONT_ARCADE);
			}
			
			if (homeTimer > 440) {
				if (homeTimer > 480) {
					if (pacman.getDirection() == Pacman.LEFT)
						if (frameNumber % 20 < 10)
							powerCoins.get(0).draw(g, this);
				} else
					powerCoins.get(0).draw(g, this);
				g.setColor(Resources.PINK);
				drawCenter(copyright.getText() + " 2022 JIANING WANG", 630, g);
			}
			
			if (homeTimer > 480) {
				
				if (pacman.getRect().intersects(powerCoins.get(0).getRect())) {
					pacman.setDirection(Pacman.RIGHT);
					for (Ghost ghost : ghosts) {
						ghost.setDirection(Ghost.RIGHT);
						ghost.setSpeed(2);
						ghost.setFrightened(true);
					}
				}
				
				for (Ghost ghost : ghosts) {
					if (ghost.getFrightened() && ghost.getVisible())
						if (pacman.getRect().intersects(ghost.getRect())) {
							scoreX = ghost.getX();
							scoreY = ghost.getY();
							ghostEaten = true;
							ghostsEaten++;
							ghost.setVisible(false);
							for (Ghost gh : ghosts)
								gh.setFrozen(true);
							pacman.setFrozen(true);
							pacman.setVisible(false);
						}
					ghost.move();
					if (frameNumber % 6 == 0)
						ghost.animate();
					ghost.draw(g, this);
				}
				
				if (ghostEaten) {
					if (ghostDeathTimer < 40) {
						switch (ghostsEaten) {
						case 1:
							g.drawImage(Resources.SCORE_200, scoreX, scoreY, 40, 40, this);
							break;
						case 2:
							g.drawImage(Resources.SCORE_400, scoreX, scoreY, 40, 40, this);
							break;
						case 3:
							g.drawImage(Resources.SCORE_800, scoreX, scoreY, 40, 40, this);
							break;
						case 4:
							g.drawImage(Resources.SCORE_1600, scoreX, scoreY, 40, 40, this);
						}
						ghostDeathTimer++;
					} else if (ghostDeathTimer == 40) {
						ghostEaten = false;
						ghostDeathTimer = 0;
						for (Ghost ghost : ghosts)
							ghost.setFrozen(false);
						pacman.setFrozen(false);
						pacman.setVisible(true);
					}
				}
				
				if (frameNumber % 3 == 0)
					pacman.animate();
				pacman.draw(g, this);
				pacman.move();
				
				if (pacman.getX() > 600 && pacman.getDirection() == Pacman.RIGHT) {
					resetSinglePlayer();
					return;
				}
			}
			
			homeTimer++;
			
			break;
			
		case SINGLEPLAYER_START:
			
			g.setColor(Color.WHITE);
			g.drawString("1UP", 60, 20);
			drawRight("" + score, 140, 40, g);
			drawCenter("HIGH SCORE", 20, g);
			drawRight("" + highScore, 340, 40, g);
			g.drawString("2UP", 440, 20);
			
			g.setColor(Resources.ORANGE);
			drawCenter("PRESS SPACE TO START", 320, g);
			
			g.setColor(Resources.BLUE);
			drawCenter("1 PLAYER ONLY", 400, g);
			
//			g.setColor(Resources.COIN_COLOR);
//			g.drawString("BONUS PAC-MAN FOR 10000", 25, 480);
//			g.setFont(Resources.FONT_SPACEBIT);
//			g.drawString("PTS", 500, 480);
			
			g.setFont(Resources.FONT_ARCADE);
			g.setColor(Resources.PINK);
			drawCenter(copyright.getText() + " 2022 JIANING WANG", 560, g);
			
			break;
			
		case SINGLEPLAYER_GAME:
			
			if (stageOverTimer >= 80)
				map.drawFlash(g, this);
			else
				map.draw(g, this);
			
			Rectangle pacmanHitbox = pacman.getRect();
			
			g.setColor(Resources.COIN_COLOR);
			
			for (int i = 0; i < coins.size(); i++) {
				Coin c = coins.get(i);
				if (pacmanHitbox.intersects(c.getRect())) {
					resources.loopAudio(Resources.EATING);
					coins.remove(i);
					score += 10;
					i--;
					if (coins.size() == 0 && powerCoins.size() == 0) {
						pacman.setFrozen(true);
						for (Ghost ghost : ghosts)
							ghost.setFrozen(true);
						stageOver = true;
					}
				} else
					c.draw(g);
			}
			
			for (int i = 0; i < powerCoins.size(); i++) {
				PowerCoin p = powerCoins.get(i);
				if (pacmanHitbox.intersects(p.getRect())) {
					resources.loopAudio(Resources.EATING);
					powerCoins.remove(i);
					score += 50;
					startFrightenedPhase();
					for (Ghost ghost : ghosts)
						ghost.setFlash(false);
					i--;
					if (coins.size() == 0 && powerCoins.size() == 0) {
						pacman.setFrozen(true);
						for (Ghost ghost : ghosts)
							ghost.setFrozen(true);
						stageOver = true;
					}
				} else {
					if (gameTimer < 240 || gameOver)
						p.draw(g, this);
					else
						if (frameNumber % 20 < 10)
							p.draw(g, this);
				}
			}
			
			if (gameTimer < 120) {
				g.setColor(Resources.BLUE);
				drawCenter("PLAYER ONE", 300, g);
			} else if (gameTimer == 120) {
				if (lifeLost) {
					pacman.decreaseLives();
					lifeLost = false;
				}
				pacmanDeathTimer = 0;
				pacman.setDead(false);
				pacman.setVisible(true);
				for (Ghost ghost : ghosts)
					ghost.setVisible(true);
			}
			if (gameTimer < 240) {
				g.setColor(Resources.YELLOW);
				drawCenter("READY!", 418, g);
				
				pacman.setFrozen(true);
				for (Ghost ghost : ghosts)
					ghost.setFrozen(true);
				
			} else if (gameTimer == 240) {
				pacman.setFrozen(false);
				for (Ghost ghost : ghosts)
					ghost.setFrozen(false);
			}
			
			if (gameTimer > 120) {
				
				if (gameOver) {
					Resources.GHOST_MOVE.stop();
					Resources.GHOST_FRIGHTENED.stop();
					Resources.GHOST_RETURN.stop();
					if (gameOverTimer < 100) {
						g.setColor(new Color(255, 0, 0));
						drawCenter("GAME  OVER", 418, g);
						gameOverTimer++;
					}
					if (gameOverTimer == 100) {
						resources.createFile();
						int position = resources.leaderboardPosition(score);
						if (position != -1) {
							initials = "";
							letterRow = 0;
							letterCol = 0;
							gameState = KEYBOARD;
							return;
						}
						resetMode();
						return;
					}
				} else if (stageOver) {
					Resources.GHOST_MOVE.stop();
					Resources.GHOST_FRIGHTENED.stop();
					Resources.GHOST_RETURN.stop();
					 if (stageOverTimer == 200) {
						 stage++;
						 resetSinglePlayerStage();
						 return;
					 }
					 else if (stageOverTimer > 80) {
						 for (Ghost ghost : ghosts)
							 ghost.setVisible(false);
						if (frameNumber % 8 == 4)
							map.animate();
					 }
					stageOverTimer++;
				}
				
				if (pacman.getDead()) {
					if (pacmanDeathTimer < 40);
					else if (pacmanDeathTimer == 40) {
						for (Ghost ghost : ghosts)
							ghost.setVisible(false);
						Resources.GHOST_FRIGHTENED.stop();
						Resources.GHOST_MOVE.stop();
						resources.playAudio(Resources.DEATH);
					} else if (pacmanDeathTimer == 180) {
						if (pacman.getLives() <= 0) {
							gameOver = true;
							gameOverTimer = 0;
						}
						else {
							resetSinglePlayerDeath();
							return;
						}
					}
					else
						if (frameNumber % 6 == 0)
							pacman.animateDeath();
					pacmanDeathTimer++;
				}
				
				if (pacman.getX() < -20)
					pacman.setX(540);
				else if (pacman.getX() > 540)
					pacman.setX(-20);
				if (pacman.getY() < 40) 
					pacman.setY(660);
				else if (pacman.getY() > 660)
					pacman.setY(40);
	
				boolean intersectsWall = false;
				boolean intersectsNextWall = false;
				
				for (Wall w : walls) {
					if (pacman.getNextRect(pacman.getNextDirection()).intersects(w.getRect()))
						intersectsNextWall = true;
					if (pacman.getNextRect(pacman.getDirection()).intersects(w.getRect()))
						intersectsWall = true;
				}
				
				if (!intersectsNextWall)
					pacman.setDirection(pacman.getNextDirection());
				
				if (!intersectsWall) {
					pacman.setBlocked(false);
					pacman.move();
				} else
					pacman.setBlocked(true);
				
				if (!pacman.getDead())
					if (frameNumber % 3 == 0)
						pacman.animate();
				
				pacman.draw(g, this);
				
				boolean ghostDead = false;
				for (Ghost ghost : ghosts)
					if (ghost.getDead())
						ghostDead = true;
				
				if (ghostDead) {
					Resources.GHOST_MOVE.stop();
					Resources.GHOST_FRIGHTENED.stop();
					resources.loopReturn();
				}
				
				if (ghostsFrightened && !ghostEaten && !ghostDead) {
					if (ghostsFrightenedTimer < 600 && !pacman.getFrozen()) {
						resources.loopFrightened();
						Resources.GHOST_MOVE.stop();
						Resources.GHOST_RETURN.stop();
						if (ghostsFrightenedTimer > 450)
							if (frameNumber % 12 == 6)
								for (Ghost ghost : ghosts)
									if (ghost.getFrightened())
										ghost.setFlash(!ghost.getFlash());
						ghostsFrightenedTimer++;
					} else if (ghostsFrightenedTimer == 600) {
						Resources.GHOST_FRIGHTENED.stop();
						Resources.GHOST_RETURN.stop();
						for (Ghost gh : ghosts) {
							gh.setFlash(false);
							gh.setFrightened(false);
							gh.setDead(false);
							if (gh.getX() % 4 != 0 || gh.getY() % 4 != 0)
								gh.setSpeed(2);
						}
						ghostsFrightened = false;
					}
				} else {
					if (!ghostEaten && !pacman.getFrozen() && gameTimer > 240 && !ghostDead) {
						Resources.GHOST_FRIGHTENED.stop();
						resources.loopMove();
					}
				}
				
				if (ghostEaten) {
					if (ghostDeathTimer < 40) {
						switch (ghostsEaten) {
						case 1:
							g.drawImage(Resources.SCORE_200, scoreX, scoreY, 40, 40, this);
							break;
						case 2:
							g.drawImage(Resources.SCORE_400, scoreX, scoreY, 40, 40, this);
							break;
						case 3:
							g.drawImage(Resources.SCORE_800, scoreX, scoreY, 40, 40, this);
							break;
						case 4:
							g.drawImage(Resources.SCORE_1600, scoreX, scoreY, 40, 40, this);
						}
						ghostDeathTimer++;
					} else if (ghostDeathTimer == 40) {
						ghostEaten = false;
						for (Ghost ghost : ghosts) {
							ghost.setVisible(true);
							ghost.setFrozen(false);
							if (ghost.getDead())
								if (ghost.getX() % 4 != 0 || ghost.getY() % 4 != 0)
									ghost.setSpeed(2);
						}
						pacman.setFrozen(false);
						pacman.setVisible(true);
						ghostDeathTimer++;
					} else if (ghostDeathTimer == 41) {
						ghostDeathTimer = 0;
					}
				}
				
				if (gameTimer > 800) {
					if (ghosts.get(2).getCaged())
						if (ghosts.get(2).getY() == 272)
							ghosts.get(2).setCaged(false);
						else
							ghosts.get(2).setDirection(Ghost.UP);
				}
				if (gameTimer > 1400) {
					if (ghosts.get(1).getCaged()) {
						if (ghosts.get(1).getX() == ghostHomeX) {
							if (ghosts.get(1).getY() == 272)
								ghosts.get(1).setCaged(false);
							else
								ghosts.get(1).setDirection(Ghost.UP);
						} else
							ghosts.get(1).setDirection(Ghost.RIGHT);
					}
				}
				if (gameTimer > 2000) {
					if (ghosts.get(3).getCaged()) {
						if (ghosts.get(3).getX() == ghostHomeX) {
							if (ghosts.get(3).getY() == 272)
								ghosts.get(3).setCaged(false);
							else
								ghosts.get(3).setDirection(Ghost.UP);
						} else
							ghosts.get(3).setDirection(Ghost.LEFT);
					}
				}
				
				for (Ghost ghost : ghosts) {
					
					if (!ghost.getFrightened() && !ghost.getCaged())
						if (ghost.getSpeed() == 2 && (ghost.getX() % 4 == 0 && ghost.getY() % 4 == 0))
							ghost.setSpeed(4);
					
					if (ghost.getX() < -20)
						ghost.setX(540);
					else if (ghost.getX() > 540)
						ghost.setX(-20);
					
					if (ghost.getCaged()) {
						
						for (Wall w : walls) {
							if (!w.getGate() || gameTimer < 800)
								if (ghost.getNextRect(ghost.getDirection()).intersects(w.getRect()))
									ghost.setDirection(ghost.getOppositeDirection());
						}
						
					} else {
						if (!ghost.getFrozen()) {
							
							if (ghost.getDead())
								if (ghost.getX() == ghostHomeX && ghost.getY() == 272)
									ghost.setDead(false);
							
							boolean canMoveForward = true;
							boolean canMoveRight = true;
							boolean canMoveLeft = true;
							ArrayList<Integer> possibleMoves = new ArrayList<>();
							
							for (Wall w : walls) {
								
								Rectangle wallHitbox = w.getRect();
									
								if (ghost.getNextRect(ghost.getDirection()).intersects(wallHitbox))
									if (!ghost.getDead() || !w.getGate())
										canMoveForward = false;
								if (ghost.getNextRect(ghost.getAdjacentRightDirection()).intersects(wallHitbox))
									if (!ghost.getDead() || !w.getGate())
										canMoveRight = false;
								if (ghost.getNextRect(ghost.getAdjacentLeftDirection()).intersects(wallHitbox))
									if (!ghost.getDead() || !w.getGate())
										canMoveLeft = false;
							}
							
							if (canMoveForward)
								possibleMoves.add(ghost.getDirection());
							if (canMoveRight)
								possibleMoves.add(ghost.getAdjacentRightDirection());
							if (canMoveLeft)
								possibleMoves.add(ghost.getAdjacentLeftDirection());
							
							if ((canMoveRight || canMoveLeft) || !canMoveForward) {
								if (ghost.getDead()) {
									ghost.setDirection(ghost.getDirectionTowards(ghostHomeX, 272, possibleMoves));
								} else if (ghost.getFrightened()) {
									ghost.setDirection(ghost.getDirectionAway(pacman.getX(), pacman.getY(), possibleMoves));
								} else {
	//								ghost.setDirection(possibleMoves.get((int)(Math.random() * possibleMoves.size())));
									ghost.setDirection(ghost.getDirectionTowards(pacman.getX(), pacman.getY(), possibleMoves));
								}
							}
						}
					}
						
					if (pacmanHitbox.intersects(ghost.getRect())) {
						if (ghost.getFrightened()) {
							scoreX = ghost.getX();
							scoreY = ghost.getY();
							ghostEaten = true;
							ghostsEaten++;
							ghost.death();
							resources.playAudio(Resources.GHOST_DEATH);
							for (Ghost gh : ghosts)
								gh.setFrozen(true);
							pacman.setFrozen(true);
							pacman.setVisible(false);
							score += 100 * Math.pow(2, ghostsEaten);
						} else if (!ghost.getDead() && !pacman.getDead()) {
							for (Ghost gh : ghosts)
								gh.setFrozen(true);
							pacman.setFrozen(true);
							pacman.death();
						}
					}
					if (frameNumber % 6 == 0)
						ghost.animate();
					
					ghost.move();
					ghost.draw(g, this);
				}	
			}
				
			g.setColor(Color.WHITE);
				
			if (frameNumber % 22 < 11)
				g.drawString("1UP", 60, 20);
			drawRight("" + score, 140, 40, g);
			drawCenter("HIGH SCORE", 20, g);
			drawRight("" + highScore, 340, 40, g);
			
			for (int i = 0; i < pacman.getLives(); i++)
				g.drawImage(Resources.PACMAN_LIFE, (i * 40) + 40, 680, 40, 40, this);
			
			gameTimer++;
			
			break;
			
		case LEADERBOARD:
			
			g.setColor(Color.WHITE);
			
			drawCenter("HIGH SCORES", 100, g);
			drawRight("RANK", 150, 210, g);
			drawRight("SCORE", 280, 210, g);
			drawRight("STAGE", 400, 210, g);
			drawRight("NAME", 490, 210, g);
			
			if (highlight == 0)
				g.setColor(Resources.YELLOW);
			drawRight("1ST", 150, 260, g);
			g.setColor(Color.WHITE);
			if (highlight == 1)
				g.setColor(Resources.YELLOW);
			drawRight("2ND", 150, 300, g);
			g.setColor(Color.WHITE);
			if (highlight == 2)
				g.setColor(Resources.YELLOW);
			drawRight("3RD", 150, 340, g);
			
			for (int i = 3; i < leaderboard.length; i++) {
				if (highlight == i)
					g.setColor(Resources.YELLOW);
				else
					g.setColor(Color.WHITE);
				drawRight((i + 1) + "TH", 150, (i * 40) + 260, g);
			}
			
			for (int i = 0; i < leaderboard.length; i++) {
				if (highlight == i)
					g.setColor(Resources.YELLOW);
				else
					g.setColor(Color.WHITE);
				for (int j = 0; j < leaderboard[0].length; j++) {
					drawRight(leaderboard[i][j], (j * 100) + 280, (i * 40) + 260, g);
				}
			}
			
			break;
			
		case KEYBOARD:
			
			g.setColor(Resources.BLUE);
			drawCenter("PLAYER ONE", 140, g);
			drawCenter("ENTER YOUR INITIALS", 160, g);
			
			g.setColor(Resources.YELLOW);
			g.fillRect((WIDTH / 2) - 40, 300, 20, 4);
			g.fillRect((WIDTH / 2) - 10, 300, 20, 4);
			g.fillRect((WIDTH / 2) + 20, 300, 20, 4);
			
			g.setColor(Color.WHITE);
			char[] initialsArray = initials.toCharArray();
			for (int i = 0; i < initialsArray.length; i++)
				g.drawString("" + initialsArray[i], (i * 30) + (WIDTH / 2) - 40, 295);
			
			for (int r = 0; r < keyboard.length; r++) {
				for (int c = 0; c < keyboard[0].length; c++) {
					if (r == letterRow && c == letterCol && frameNumber % 20 < 10) {
						g.setColor(Resources.RED);
						g.fillRect((c * 40) + 90, (r * 40) + 445, 20, 4);
						g.setColor(Color.WHITE);
					}
					if (keyboard[r][c].equals("DEL"))
						g.drawImage(Resources.BACKSPACE, (c * 40) + 88, (r * 40) + 418, 24, 24, this);
					else if (keyboard[r][c].equals("END"))
						g.drawImage(Resources.RETURN, (c * 40) + 88, (r * 40) + 418, 24, 24, this);
					else
						g.drawString(keyboard[r][c], (c * 40) + 90, (r * 40) + 440);
				}
			}
			
			drawCenter("STAGE", 600, g);
			drawCenter("" + stage, 640, g);
			
			g.drawString("1UP", 60, 20);
			drawRight("" + score, 140, 40, g);
			drawCenter("HIGH SCORE", 20, g);
			drawRight("" + highScore, 340, 40, g);
		}
	}
	
	public void drawCenter(String text, int y, Graphics g) {
		int width = text.length() * 20;
		g.drawString(text, (WIDTH - width) / 2, y);
	}
	
	public void drawRight(String text, int x, int y, Graphics g) {
		int leftX = x - (text.length() * 20);
//		if (text.equals("0")) {
//			text = "00";
//			leftX -= 20;
//		}
		g.drawString(text, leftX, y);
	}

	private int currentKey = 0;
	private final int[] konamiCode = {
			KeyEvent.VK_UP,
			KeyEvent.VK_UP,
			KeyEvent.VK_DOWN,
			KeyEvent.VK_DOWN,
			KeyEvent.VK_LEFT,
			KeyEvent.VK_RIGHT,
			KeyEvent.VK_LEFT,
			KeyEvent.VK_RIGHT,
			KeyEvent.VK_B,
			KeyEvent.VK_A
		};

	public void keyPressed(KeyEvent e) {

		int v = e.getKeyCode();

		if (v == konamiCode[currentKey]) {
			currentKey++;
			if (currentKey == konamiCode.length) {
				currentKey = 0;
				System.out.println("KONAMI CODE");
			}
		} else if (!(currentKey == 2 && v == KeyEvent.VK_UP))
			currentKey = 0;

		switch (gameState) {
		case MODE:
			switch (v) {
			case KeyEvent.VK_UP:
				if (markerIndex > 1)
					markerIndex--;
				else
					markerIndex = LEADERBOARD;
				break;
			case KeyEvent.VK_DOWN:
				if (markerIndex < 3)
					markerIndex++;
				else
					markerIndex = SINGLEPLAYER;
				break;
			case KeyEvent.VK_SPACE:
				switch (markerIndex) {
				case SINGLEPLAYER:
					resetSinglePlayer();
					break;
				case MULTIPLAYER:
					
					break;
				case LEADERBOARD:
					resetLeaderboard();
				}
			}
			break;
		case SINGLEPLAYER:
			if (v == KeyEvent.VK_SPACE)
				gameState = SINGLEPLAYER_START;
			break;
		case SINGLEPLAYER_START:
			if (v == KeyEvent.VK_SPACE)
				resetSinglePlayerGame();
			break;
		case SINGLEPLAYER_GAME:
			switch (v) {
			case KeyEvent.VK_W:
			case KeyEvent.VK_UP:
				pacman.setNextDirection(Pacman.UP);
				break;
			case KeyEvent.VK_D:
			case KeyEvent.VK_RIGHT:
				pacman.setNextDirection(Pacman.RIGHT);
				break;
			case KeyEvent.VK_S:
			case KeyEvent.VK_DOWN:
				pacman.setNextDirection(Pacman.DOWN);
				break;
			case KeyEvent.VK_A:
			case KeyEvent.VK_LEFT:
				pacman.setNextDirection(Pacman.LEFT);
			}
			break;
		case LEADERBOARD:
			switch (v) {
			case KeyEvent.VK_UP:
				if (highlight > 0)
					highlight--;
				else highlight = 10;
				break;
			case KeyEvent.VK_DOWN:
				if (highlight < 10)
					highlight++;
				else highlight = 0;
				break;
			case KeyEvent.VK_SPACE:
				resetMode();
				break;
			}
			break;
		case KEYBOARD:
			if (initials.length() < 3) {
				switch (v) {
				case KeyEvent.VK_A:
					initials += "A";
					break;
				case KeyEvent.VK_B:
					initials += "B";
					break;
				case KeyEvent.VK_C:
					initials += "C";
					break;
				case KeyEvent.VK_D:
					initials += "D";
					break;
				case KeyEvent.VK_E:
					initials += "E";
					break;
				case KeyEvent.VK_F:
					initials += "F";
					break;
				case KeyEvent.VK_G:
					initials += "G";
					break;
				case KeyEvent.VK_H:
					initials += "H";
					break;
				case KeyEvent.VK_I:
					initials += "I";
					break;
				case KeyEvent.VK_J:
					initials += "J";
					break;
				case KeyEvent.VK_K:
					initials += "K";
					break;
				case KeyEvent.VK_L:
					initials += "L";
					break;
				case KeyEvent.VK_M:
					initials += "M";
					break;
				case KeyEvent.VK_N:
					initials += "N";
					break;
				case KeyEvent.VK_O:
					initials += "O";
					break;
				case KeyEvent.VK_P:
					initials += "P";
					break;
				case KeyEvent.VK_Q:
					initials += "Q";
					break;
				case KeyEvent.VK_R:
					initials += "R";
					break;
				case KeyEvent.VK_S:
					initials += "S";
					break;
				case KeyEvent.VK_T:
					initials += "T";
					break;
				case KeyEvent.VK_U:
					initials += "U";
					break;
				case KeyEvent.VK_V:
					initials += "V";
					break;
				case KeyEvent.VK_W:
					initials += "W";
					break;
				case KeyEvent.VK_X:
					initials += "X";
					break;
				case KeyEvent.VK_Y:
					initials += "Y";
					break;
				case KeyEvent.VK_Z:
					initials += "Z";
					break;
				case KeyEvent.VK_PERIOD:
					initials += ".";
					break;
				case KeyEvent.VK_MINUS:
					initials += "-";
					break;
				}
			}
			
			switch (v) {
			case KeyEvent.VK_UP:
				if (letterRow < 1)
					letterRow = keyboard.length - 1;
				else
					letterRow--;
				break;
			case KeyEvent.VK_RIGHT:
				if (letterCol > keyboard[0].length - 2)
					letterCol = 0;
				else
					letterCol++;
				break;
			case KeyEvent.VK_DOWN:
				if (letterRow > keyboard.length - 2)
					letterRow = 0;
				else
					letterRow++;
				break;
			case KeyEvent.VK_LEFT:
				if (letterCol < 1)
					letterCol = keyboard[0].length - 1;
				else
					letterCol--;
				break;
			case KeyEvent.VK_ENTER:
				resources.writeScore(score, stage, initials, resources.leaderboardPosition(score));
				resetMode();
				break;
			case KeyEvent.VK_BACK_SPACE:
				if (initials.length() > 0)
					initials = initials.substring(0, initials.length() - 1);
				break;
			case KeyEvent.VK_SPACE:
				if (keyboard[letterRow][letterCol].equals("DEL")) {
					if (initials.length() > 0)
						initials = initials.substring(0, initials.length() - 1);
				} else if (keyboard[letterRow][letterCol].equals("END")) {
					if (initials.length() > 2) {
						resources.writeScore(score, stage, initials, resources.leaderboardPosition(score));
						resetMode();
					}
				} else if (initials.length() < 3)
					initials += keyboard[letterRow][letterCol];
				break;
			}
			break;
		}
		
		switch (v) {
		case KeyEvent.VK_M:
			pacman.setLoc(mouseX - 20, mouseY - 20);
			break;
		case KeyEvent.VK_1:
			resetMode();
			break;
//		case KeyEvent.VK_2:
//			initials = "";
//			gameState = KEYBOARD;
//			break;
		case KeyEvent.VK_ESCAPE:
			System.exit(0);
		}
	}
}