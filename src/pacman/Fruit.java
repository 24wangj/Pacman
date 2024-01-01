package pacman;

import java.awt.Graphics;
import java.awt.image.ImageObserver;

public class Fruit {

	private final int WIDTH = 40;
	private final int HEIGHT = 40;
	
	private int x;
	private int y;
	private int type;
	
	public static final int CHERRY = 0;
	public static final int STRAWBERRY = 1;
	public static final int ORANGE = 2;
	public static final int APPLE = 3;
	public static final int MELON = 4;
	public static final int GALAXIAN_STARSHIP = 5;
	public static final int BELL = 6;
	public static final int KEY = 7;
	
	public Fruit() {
		type = CHERRY;
	}
	
	public void setLoc(int x, int y) {
    	this.x = x;
    	this.y = y;
    }
	
	public void draw(Graphics g, ImageObserver io) {
		switch (type) {
		case CHERRY:
			g.drawImage(Resources.CHERRY, x, y, WIDTH, HEIGHT, io);
			break;
		case STRAWBERRY:
			g.drawImage(Resources.STRAWBERRY, x, y, WIDTH, HEIGHT, io);
			break;
		case ORANGE:
			g.drawImage(Resources.ORANGE_FRUIT, x, y, WIDTH, HEIGHT, io);
			break;
		case APPLE:
			g.drawImage(Resources.APPLE, x, y, WIDTH, HEIGHT, io);
			break;
		case MELON:
			g.drawImage(Resources.MELON, x, y, WIDTH, HEIGHT, io);
			break;
		case GALAXIAN_STARSHIP:
			g.drawImage(Resources.GALAXIAN_STARSHIP, x, y, WIDTH, HEIGHT, io);
			break;
		case BELL:
			g.drawImage(Resources.BELL, x, y, WIDTH, HEIGHT, io);
			break;
		case KEY:
			g.drawImage(Resources.KEY, x, y, WIDTH, HEIGHT, io);
		}
	}
}
