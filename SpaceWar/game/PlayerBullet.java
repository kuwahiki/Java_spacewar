package game;

import java.awt.image.BufferedImage;

/**
 * @author a50605
 *
 */
public class PlayerBullet extends GameChara {

	public PlayerBullet(int x, int y, BufferedImage img) {
		super(x, y, 12, 12, img, 0, 52+64, 16, 12,1);
	}

	public void move() {
		chara_x = chara_x + 15;
	}

}
