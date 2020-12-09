package game;

import java.awt.image.BufferedImage;

/**
 * @author a50605
 *
 */
public class Player extends GameChara {
	int HP;

	public Player(int x, int y, int hp, BufferedImage img) {
		super(x, y, 60, 30, img, 0, 0, 92, 52, 1);
		HP = hp;
	}

	public void move() {
	}


	public void move(boolean up, boolean down, boolean left, boolean right) {
		if (up == true)
			chara_y = chara_y - 8;
		if (down == true)
			chara_y = chara_y + 8;
		if (left == true)
			chara_x = chara_x - 8;
		if (right == true)
			chara_x = chara_x + 8;

		if (chara_y < 0)
			chara_y = 0;
		if (chara_x < 0)
			chara_x = 0;
		if (chara_y > initSceen.SCREEN_H - 48)
			chara_y = initSceen.SCREEN_H - 48;
		if (chara_x > initSceen.SCREEN_W - 48)
			chara_x = initSceen.SCREEN_W - 48;
	}

	public boolean die() {
		if (HP == 0) {
			return true;
		} else {
			HP--;
			return false;
		}
	}

}
