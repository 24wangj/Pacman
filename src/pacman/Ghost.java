package pacman;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

public class Ghost {

	private final int WIDTH = 40;
	private final int HEIGHT = 40;
	private final int HITBOX_WIDTH = 20;
	private final int HITBOX_HEIGHT = 20;
	private final int OFFSET = 8;

	private int x;
	private int y;
	private int direction;
	private boolean frightened;
	private int speed;
	private int color;
	private boolean frozen;
	private boolean dead;
	private boolean invisible;
	private boolean caged;
	private boolean flash;
	
	public static final int NONE = -1;
	public static final int UP = 0;
	public static final int RIGHT = 90;
	public static final int DOWN = 180;
	public static final int LEFT = 270;
	
	public static final int RED = 0;
	public static final int PINK = 1;
	public static final int BLUE = 2;
	public static final int ORANGE = 3;
	
	private final Image[] GHOST_RED_UP = { Resources.GHOST_RED_UP_1, Resources.GHOST_RED_UP_2 };
	private final Image[] GHOST_RED_RIGHT = { Resources.GHOST_RED_RIGHT_1, Resources.GHOST_RED_RIGHT_2 };
	private final Image[] GHOST_RED_DOWN = { Resources.GHOST_RED_DOWN_1, Resources.GHOST_RED_DOWN_2 };
	private final Image[] GHOST_RED_LEFT = { Resources.GHOST_RED_LEFT_1, Resources.GHOST_RED_LEFT_2 };
	private final Image[] GHOST_PINK_UP = { Resources.GHOST_PINK_UP_1, Resources.GHOST_PINK_UP_2 };
	private final Image[] GHOST_PINK_RIGHT = { Resources.GHOST_PINK_RIGHT_1, Resources.GHOST_PINK_RIGHT_2 };
	private final Image[] GHOST_PINK_DOWN = { Resources.GHOST_PINK_DOWN_1, Resources.GHOST_PINK_DOWN_2 };
	private final Image[] GHOST_PINK_LEFT = { Resources.GHOST_PINK_LEFT_1, Resources.GHOST_PINK_LEFT_2 };
	private final Image[] GHOST_BLUE_UP = { Resources.GHOST_BLUE_UP_1, Resources.GHOST_BLUE_UP_2 };
	private final Image[] GHOST_BLUE_RIGHT = { Resources.GHOST_BLUE_RIGHT_1, Resources.GHOST_BLUE_RIGHT_2 };
	private final Image[] GHOST_BLUE_DOWN = { Resources.GHOST_BLUE_DOWN_1, Resources.GHOST_BLUE_DOWN_2 };
	private final Image[] GHOST_BLUE_LEFT = { Resources.GHOST_BLUE_LEFT_1, Resources.GHOST_BLUE_LEFT_2 };
	private final Image[] GHOST_YELLOW_UP = { Resources.GHOST_ORANGE_UP_1, Resources.GHOST_ORANGE_UP_2 };
	private final Image[] GHOST_YELLOW_RIGHT = { Resources.GHOST_ORANGE_RIGHT_1, Resources.GHOST_ORANGE_RIGHT_2 };
	private final Image[] GHOST_YELLOW_DOWN = { Resources.GHOST_ORANGE_DOWN_1, Resources.GHOST_ORANGE_DOWN_2 };
	private final Image[] GHOST_YELLOW_LEFT = { Resources.GHOST_ORANGE_LEFT_1, Resources.GHOST_ORANGE_LEFT_2 };
	private final Image[] GHOST_FRIGHTENED = { Resources.GHOST_FRIGHTENED_1, Resources.GHOST_FRIGHTENED_2 };
	private final Image[] GHOST_FRIGHTENED_FLASH = { Resources.GHOST_FRIGHTENED_FLASH_1, Resources.GHOST_FRIGHTENED_FLASH_2 };
	private int currentImage;
	
	//Accessors
    //-------------------------------------------------------
	public int getX() { return x; }
	public int getY() { return y; }
	public int getDirection() { return direction; }
	public boolean getFrozen() { return frozen; }
	public boolean getDead() { return dead; }
	public int getSpeed() { return speed; }
	public boolean getFrightened() { return frightened; }
	public boolean getVisible() { return !invisible; }
	public boolean getCaged() { return caged; }
	public boolean getFlash() { return flash; }

	//Modifiers
    //-------------------------------------------------------
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setDirection(int direction) { this.direction = direction; }
    public void setFrozen(boolean frozen) { this.frozen = frozen; }
    public void setDead(boolean dead) { this.dead = dead; }
    public void setSpeed(int speed) { this.speed = speed; }
    public void setFrightened(boolean frightened) { this.frightened = frightened; }
    public void setVisible(boolean visible) { this.invisible = !visible; }
    public void setCaged(boolean caged) { this.caged = caged; }
    public void setFlash(boolean flash) { this.flash = flash; }
	
    /*
     * Returns the hitbox of ghost.
     */
    public Rectangle getRect() { return new Rectangle(x + OFFSET, y + OFFSET, HITBOX_WIDTH, HITBOX_HEIGHT); }
    /*
     * Returns the extended hitbox of ghost according to its next position.
     */
    public Rectangle getNextRect(int direction) {
    	switch (direction) {
    		case UP:
    			return new Rectangle(x + OFFSET, y + OFFSET - speed, HITBOX_WIDTH, HITBOX_HEIGHT + speed);
    		case RIGHT:
    			return new Rectangle(x + OFFSET, y + OFFSET, HITBOX_WIDTH + speed, HITBOX_HEIGHT);
    		case DOWN:
    			return new Rectangle(x + OFFSET, y + OFFSET, HITBOX_WIDTH, HITBOX_HEIGHT + speed);
    		case LEFT:
    			return new Rectangle(x + OFFSET - speed, y + OFFSET, HITBOX_WIDTH + speed, HITBOX_HEIGHT);
    	}
    	return getRect();
    }
    
    public Ghost(int color) {
    	this.color = color;
    	currentImage = 0;
    	speed = 4;
    }
    
    /*
     * Sets both x and y of ghost.
     */
    public void setLoc(int x, int y) {
    	this.x = x;
    	this.y = y;
    }
    
    /*
     * Returns the opposite direction of ghost.
     */
    public int getOppositeDirection() {
    	return (direction + 180) % 360;
    }
    
    /*
     * Returns the direction right of ghost.
     */
    public int getAdjacentRightDirection() {
    	return (direction + 90) % 360;
    }
    
    /*
     * Returns the diretcion left of ghost.
     */
    public int getAdjacentLeftDirection() {
    	return (direction + 270) % 360;
    }
    
    /*
     * Returns the next x coordinate of ghost.
     */
    public int getNextX(int direction) {
    	int nextX = x;
    	switch (direction) {
    	case RIGHT:
    		nextX += speed;
    		break;
    	case LEFT:
    		nextX -= speed;
    	}
    	return nextX;
    }
    
    /*
     * Returns the next y coordinate of ghost.
     */
    public int getNextY(int direction) {
    	int nextY = y;
    	switch (direction) {
    	case UP:
    		nextY -= speed;
    		break;
    	case DOWN:
    		nextY += speed;
    	}
    	return nextY;
    }
    
    /*
     * Chooses the shortest path to the target location.
     */
    public int getDirectionTowards(int x, int y, ArrayList<Integer> possibleMoves) {
    	double shortestDistance = 999;
    	int direction = possibleMoves.get(0);
    	for (Integer i : possibleMoves) {
    		double distance = Math.sqrt(Math.pow(getNextX(i) - x, 2) + Math.pow(getNextY(i) - y, 2));
    		if (distance < shortestDistance) {
    			shortestDistance = distance;
    			direction = i;
    		}
    	}
    	return direction;
    }
    
    /*
     * Chooses the longest path to the target location.
     */
    public int getDirectionAway(int x, int y, ArrayList<Integer> possibleMoves) {
    	double furthestDistance = 0;
    	int direction = possibleMoves.get(0);
    	for (Integer i : possibleMoves) {
    		double distance = Math.sqrt(Math.pow(getNextX(i) - x, 2) + Math.pow(getNextY(i) - y, 2));
    		if (distance > furthestDistance) {
    			furthestDistance = distance;
    			direction = i;
    		}
    	}
    	return direction;
    }
    
    /*
     * Moves ghost based on the direction it is facing.
     */
    public void move() {
    	if (!frozen) {
    		switch (direction) {
        	case UP:
        	   	y -= speed;
        	   	break;
        	case RIGHT:
        		x += speed;
            	break;
        	case DOWN:
            	y += speed;
            	break;
        	case LEFT:
        		x -= speed;
        	}
    	}
    }
    
    /*
     * Sets the state of ghost and increases the speed.
     */
    public void death() {
    	frightened = false;
    	dead = true;
		invisible = true;
		speed = 4;
    }
    
    /*
     * Animates ghost's move animation.
     */
    public void animate() {
    	if (!frozen)
    		currentImage = (currentImage + 1) % GHOST_RED_RIGHT.length;
    }
    
    /*
     * Draws ghost based on its state.
     */
    public void draw(Graphics g, ImageObserver io) {
    	if (invisible) {
    		return;
    	} else if (dead) {
    		switch (direction) {
			case UP:
				g.drawImage(Resources.GHOST_EYES_UP, x, y, WIDTH, HEIGHT, io);
				break;
			case RIGHT:
				g.drawImage(Resources.GHOST_EYES_RIGHT, x, y, WIDTH, HEIGHT, io);
				break;
			case DOWN:
				g.drawImage(Resources.GHOST_EYES_DOWN, x, y, WIDTH, HEIGHT, io);
				break;
			case LEFT:
				g.drawImage(Resources.GHOST_EYES_LEFT, x, y, WIDTH, HEIGHT, io);
    		}
    	} else if (flash) {
    		g.drawImage(GHOST_FRIGHTENED_FLASH[currentImage], x, y, WIDTH, HEIGHT, io);
    	} else if (frightened) {
    		g.drawImage(GHOST_FRIGHTENED[currentImage], x, y, WIDTH, HEIGHT, io);
    	} else {
    		switch (color) {
    			case RED:
    				switch (direction) {
    					case UP:
    						g.drawImage(GHOST_RED_UP[currentImage], x, y, WIDTH, HEIGHT, io);
    						break;
    					case RIGHT:
    						g.drawImage(GHOST_RED_RIGHT[currentImage], x, y, WIDTH, HEIGHT, io);
    						break;
    					case DOWN:
    						g.drawImage(GHOST_RED_DOWN[currentImage], x, y, WIDTH, HEIGHT, io);
    						break;
    					case LEFT:
    						g.drawImage(GHOST_RED_LEFT[currentImage], x, y, WIDTH, HEIGHT, io);
    				}
    				break;
    			case PINK:
    				switch (direction) {
					case UP:
						g.drawImage(GHOST_PINK_UP[currentImage], x, y, WIDTH, HEIGHT, io);
						break;
					case RIGHT:
						g.drawImage(GHOST_PINK_RIGHT[currentImage], x, y, WIDTH, HEIGHT, io);
						break;
					case DOWN:
						g.drawImage(GHOST_PINK_DOWN[currentImage], x, y, WIDTH, HEIGHT, io);
						break;
					case LEFT:
						g.drawImage(GHOST_PINK_LEFT[currentImage], x, y, WIDTH, HEIGHT, io);
    				}
    				break;
    			case BLUE:
    				switch (direction) {
					case UP:
						g.drawImage(GHOST_BLUE_UP[currentImage], x, y, WIDTH, HEIGHT, io);
						break;
					case RIGHT:
						g.drawImage(GHOST_BLUE_RIGHT[currentImage], x, y, WIDTH, HEIGHT, io);
						break;
					case DOWN:
						g.drawImage(GHOST_BLUE_DOWN[currentImage], x, y, WIDTH, HEIGHT, io);
						break;
					case LEFT:
						g.drawImage(GHOST_BLUE_LEFT[currentImage], x, y, WIDTH, HEIGHT, io);
    				}
    				break;
    			case ORANGE:
    				switch (direction) {
					case UP:
						g.drawImage(GHOST_YELLOW_UP[currentImage], x, y, WIDTH, HEIGHT, io);
						break;
					case RIGHT:
						g.drawImage(GHOST_YELLOW_RIGHT[currentImage], x, y, WIDTH, HEIGHT, io);
						break;
					case DOWN:
						g.drawImage(GHOST_YELLOW_DOWN[currentImage], x, y, WIDTH, HEIGHT, io);
						break;
					case LEFT:
						g.drawImage(GHOST_YELLOW_LEFT[currentImage], x, y, WIDTH, HEIGHT, io);
				}
    		}
    	}
    }
}
