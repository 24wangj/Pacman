package pacman;

import java.awt.Graphics;
import java.awt.Rectangle;

public class Wall {

	private final int WIDTH = 20;
	private final int HEIGHT = 20;
	
	private int x;
	private int y;
	private boolean gate;
	
	//Accessors
    //-------------------------------------------------------
	public int getX() { return x; }
	public int getY() { return y; }
	public boolean getGate() { return gate; }
	
	//Modifiers
    //-------------------------------------------------------
	public void setGate(boolean gate) { this.gate = gate; }
	
	public Wall(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/*
	 * Returns the hitbox of wall.
	 */
	public Rectangle getRect() { return new Rectangle(x, y, WIDTH, HEIGHT); }
	
	/*
	 * Draws the hitbox of wall for testing purposes.
	 */
	public void drawHitbox(Graphics g) {
    	g.drawRect(x, y, WIDTH, HEIGHT);
    }
}
