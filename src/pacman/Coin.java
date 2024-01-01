package pacman;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Coin {

	private final int WIDTH = 5;
	private final int HEIGHT = 5;
	private final int OFFSET = 8;
	
	protected int x;
	protected int y;
	
	//Accessors
    //-------------------------------------------------------
	public int getX() { return x; }
	public int getY() { return y; }
	
	//Modifiers
    //-------------------------------------------------------
	public void setX(int x) { this.x = x; }
	
	public Coin(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/*
	 * Returns the hitbox of coin.
	 */
	public Rectangle getRect() { return new Rectangle(x, y, WIDTH, HEIGHT); }

	/*
	 * Draws coin.
	 */
	public void draw(Graphics g) {
		g.fillRect(x + OFFSET, y + OFFSET, WIDTH, HEIGHT);
	}
}
