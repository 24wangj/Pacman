	package pacman;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

public class Pacman {

	private final int WIDTH = 40;
	private final int HEIGHT = 40;
	private final int HITBOX_WIDTH = 20;
	private final int HITBOX_HEIGHT = 20;
	private final int OFFSET = 8;

	private int x;
	private int y;
	private int direction;
	private int nextDirection;
	private int speed;
	private int lives;
	private boolean frozen;
	private boolean dead;
	private boolean blocked;
	private boolean invisible;

	public static final int NONE = -1;
	public static final int UP = 0;
	public static final int RIGHT = 90;
	public static final int DOWN = 180;
	public static final int LEFT = 270;
	
	private final Image[] PACMAN_UP = {
			Resources.PACMAN,
			Resources.PACMAN_UP_1,
			Resources.PACMAN_UP_2,
			Resources.PACMAN_UP_1
	};
	private final Image[] PACMAN_RIGHT = {
			Resources.PACMAN,
			Resources.PACMAN_RIGHT_1,
			Resources.PACMAN_RIGHT_2,
			Resources.PACMAN_RIGHT_1
	};
	private final Image[] PACMAN_DOWN = {
			Resources.PACMAN,
			Resources.PACMAN_DOWN_1,
			Resources.PACMAN_DOWN_2,
			Resources.PACMAN_DOWN_1
	};
	private final Image[] PACMAN_LEFT = {
			Resources.PACMAN,
			Resources.PACMAN_LEFT_1,
			Resources.PACMAN_LEFT_2,
			Resources.PACMAN_LEFT_1
	};
	private final Image[] PACMAN_DEATH = {
			Resources.PACMAN_DEATH_1,
			Resources.PACMAN_DEATH_2,
			Resources.PACMAN_DEATH_3,
			Resources.PACMAN_DEATH_4,
			Resources.PACMAN_DEATH_5,
			Resources.PACMAN_DEATH_6,
			Resources.PACMAN_DEATH_7,
			Resources.PACMAN_DEATH_8,
			Resources.PACMAN_DEATH_9,
			Resources.PACMAN_DEATH_10,
			Resources.PACMAN_DEATH_11,
			Resources.PACMAN_DEATH_11
	};
	private int currentImage;
	private int currentDeathImage;

	//Accessors
    //-------------------------------------------------------
	public int getX() { return x; }
	public int getY() { return y; }
	public int getDirection() { return direction; }
	public int getNextDirection() { return nextDirection; }
	public int getLives() { return lives; }
	public boolean getFrozen() { return frozen; }
	public boolean getDead() { return dead; }

	//Modifiers
    //-------------------------------------------------------
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setDirection(int direction) { this.direction = direction; }
    public void setNextDirection(int nextDirection ) { this.nextDirection = nextDirection; }
    public void increaseLives() { this.lives++; }
    public void decreaseLives() { this.lives--; }
    public void setFrozen(boolean frozen) { this.frozen = frozen; }
    public void setDead(boolean dead) { this.dead = dead; }
    public void setBlocked(boolean blocked) { this.blocked = blocked; }
    public void setVisible(boolean visible) { this.invisible = !visible; }
	
    /*
     * Returns the hitbox of pacman.
     */
    public Rectangle getRect() { return new Rectangle(x + OFFSET, y + OFFSET, HITBOX_WIDTH, HITBOX_HEIGHT); }
    /*
     * Returns the extended hitbox of pacman according to its next position.
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
    
    public Pacman() {
    	currentImage = 0;
    	lives = 5;
    	speed = 4;
    }
    
    /*
     * Sets both x and y of pacman.
     */
    public void setLoc(int x, int y) {
    	this.x = x;
    	this.y = y;
    }
    
    /*
     * Moves pacman based on the direction it is facing.
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
     * Updates the state of pacman and resets the death animation.
     */
    public void death() {
    	dead = true;
    	currentDeathImage = 0;
    }
    
    /*
     * Animates pacman's eating animation.
     */
    public void animate() {
    	if (!blocked)
    		currentImage = (currentImage + 1) % PACMAN_UP.length;
    }
    
    /*
     * Animates pacman's death animation.
     */
    public void animateDeath() {
    	if (currentDeathImage < PACMAN_DEATH.length)
    		currentDeathImage++;
    	if (currentDeathImage >= PACMAN_DEATH.length)
    		invisible = true;	
    }
    
    /*
     * Draws the hitbox of pacman for testing purposes.
     */
    public void drawHitbox(Graphics g) {
    	g.drawRect(x + OFFSET, y + OFFSET, HITBOX_WIDTH, HITBOX_HEIGHT);
    }    
    
    /*
     * Draws the next hitbox of pacman for testing purposes.
     */
    public void drawNextHitbox(Graphics g, int direction) {
    	switch (direction) {
		case UP:
			g.drawRect(x + OFFSET, y + OFFSET - speed, HITBOX_WIDTH, HITBOX_HEIGHT + speed);
			break;
		case RIGHT:
			g.drawRect(x + OFFSET, y + OFFSET, HITBOX_WIDTH + speed, HITBOX_HEIGHT);
			break;
		case DOWN:
			g.drawRect(x + OFFSET, y + OFFSET, HITBOX_WIDTH, HITBOX_HEIGHT + speed);
			break;
		case LEFT:
			g.drawRect(x + OFFSET - speed, y + OFFSET, HITBOX_WIDTH + speed, HITBOX_HEIGHT);
    	}
    }
    
    /*
     * Draws pacman based on its state.
     */
	public void draw(Graphics g, ImageObserver io) {
		if (invisible) {
			return;
		} else if (dead) {
			g.drawImage(PACMAN_DEATH[currentDeathImage], x, y, WIDTH, HEIGHT, io);
		} else if (frozen) {
			g.drawImage(Resources.PACMAN, x, y, WIDTH, HEIGHT, io);
		} else {
			switch (direction) {
			case UP:
				g.drawImage(PACMAN_UP[currentImage], x, y, WIDTH, HEIGHT, io);
				break;
			case RIGHT:
				g.drawImage(PACMAN_RIGHT[currentImage], x, y, WIDTH, HEIGHT, io);
				break;
			case DOWN:
				g.drawImage(PACMAN_DOWN[currentImage], x, y, WIDTH, HEIGHT, io);
				break;
			case LEFT:
				g.drawImage(PACMAN_LEFT[currentImage], x, y, WIDTH, HEIGHT, io);
				break;
			default:
				g.drawImage(Resources.PACMAN, x, y, WIDTH, HEIGHT, io);
			}
		}
	}
}
