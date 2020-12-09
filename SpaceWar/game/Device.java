package game;

import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 * @author a50605
 *
 */
public class Device extends GameChara {
	Player player1;
	int delay;
	double angle1;
	double rangle; // ラジアン角
	Point center; // 回転の中心座標
	double x = 0.0, y = 0.0;

	public Device(int x, int y, double angle, BufferedImage img, Player player) {
		super(player.chara_x + player.hitbox_w / 2, player.chara_y + player.hitbox_h / 2, 0, 0, img, 20, 20, 10, 10, 1);
		center = new Point(player.chara_x + player.hitbox_w / 2, player.chara_y + player.hitbox_h / 2);
		player1 = player;
		angle1 = angle;
	}

	@Override
	public void move() {
		// 回転の動き
		moveRotation();
		chara_x = center.x + (int) x;
		chara_y = center.y + (int) y;
		angle1 += 5;
		if (angle1 == 360)
			angle1 = 0;
	}

	private void moveRotation() {
		rangle = Math.toRadians(angle1);
		x = 46 * Math.sin(rangle);
		y = 46 * Math.cos(rangle);
		Math.round(x);
		Math.round(y);
	}

	public void move(boolean up, boolean down, boolean left, boolean right) {

		if (up == true)
			center.y = center.y - 8;
		if (down == true)
			center.y = center.y + 8;
		if (left == true)
			center.x = center.x - 8;
		if (right == true)
			center.x = center.x + 8;

	}
}
