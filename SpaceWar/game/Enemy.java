package game;

import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 * @author a50605
 *
 */
public class Enemy extends GameChara {
	PatternReader preader;
	int turn; // 反転
	int HP;

	public Enemy(int x, int y, int hp, BufferedImage img, int ptnum, String patstr) {
		super(x, y, 56, 56, img, ptnum * 64, 56, 64, 64, 1);
		HP = hp;
		preader = new PatternReader(patstr);
		if (chara_y > initSceen.SCREEN_H / 2) {
			turn = -1;
		} else {
			turn = 1;
		}
		if (ptnum == 3) {
			chara_y = initSceen.SCREEN_H / 2;
		}

	}

	public void move() {
		Point movexy = preader.next();
		chara_x = chara_x + movexy.x;
		chara_y = chara_y + movexy.y * turn;
	}

	public boolean die() {
		HP--;
		if (HP == 0) {
			return true;
		} else {
			return false;
		}

	}
}
