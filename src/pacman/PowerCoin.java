package pacman;

import java.awt.Graphics;
import java.awt.image.ImageObserver;

public class PowerCoin extends Coin {

	private final int WIDTH = 20;
	private final int HEIGHT = 20;
	
	public PowerCoin(int x, int y) {
		super(x, y);
	}

	/*
	 * Draws powercoin.
	 */
	public void draw(Graphics g, ImageObserver io) {
		g.drawImage(Resources.COIN_POWER, x, y, WIDTH, HEIGHT, io);
	}
}
