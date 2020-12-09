package game;

import java.awt.image.BufferedImage;

/**
 * @author a50605
 *
 */
public class EnemyBullet extends GameChara {
	int dx, dy;
	boolean boss;

	public EnemyBullet(int x, int y, int jx, int jy, int a, BufferedImage img) {
		super(x, y, 12, 12, img, 16 * a, 52 + 64, 16, 12, 1);
		double d = Math.sqrt((jx - x) * (jx - x) + (jy - y) * (jy - y));
		if (d != 0) {
			dx = (int) Math.round((jx - x) / d * 6.0);
			dy = (int) Math.round((jy - y) / d * 6.0);
		}
	}

	public void move() {
		chara_x = chara_x + dx;
		chara_y = chara_y + dy;
	}
}
