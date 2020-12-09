package game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

/**
 * @author a50605
 *
 */
public abstract class GameChara {
	public static final int AREA_X1 = -64;
	public static final int AREA_Y1 = -64;
	public static final int AREA_X2 = initSceen.SCREEN_W + 64;
	public static final int AREA_Y2 = initSceen.SCREEN_H + 64;

	public int chara_x, chara_y;
	int image_x, image_y, image_w, image_h;
	int hitbox_w, hitbox_h;
	int numsceen;
	int sceen = 0;
	BufferedImage image1;

	GameChara(int x, int y, int aw, int ah, BufferedImage img, int ix, int iy, int iw, int ih, int sceens) {
		chara_x = x;
		chara_y = y;
		hitbox_w = aw;
		hitbox_h = ah;
		image1 = img;
		image_x = ix;
		image_y = iy;
		image_w = iw;
		image_h = ih;
		numsceen = sceens;
	}

	public void draw(Graphics g, ImageObserver io) {
		if (numsceen == 1) {
			// 画像にモーションがない場合
			g.drawImage(image1, chara_x, chara_y, chara_x + image_w, chara_y + image_h, image_x, image_y,
					image_x + image_w, image_y + image_h, io);
		} else if (sceen < numsceen) {
			// 画像にモーションがある場合
			g.drawImage(image1, chara_x, chara_y, chara_x + image_w, chara_y + image_h, (image_x * sceen), image_y,
					(image_x * sceen) + image_w, image_y + image_h, io);
			sceen++;
		} else {
			sceen = 0;
		}
		move();
	}

	public abstract void move();

	public int getx1() {
		return chara_x + (image_w - hitbox_w) / 2;
	}

	public int gety1() {
		return chara_y + (image_h - hitbox_h) / 2;
	}

	public int getx2() {
		return chara_x + (image_w + hitbox_w) / 2;
	}

	public int gety2() {
		return chara_y + (image_h + hitbox_h) / 2;
	}

	public boolean isHit(GameChara aite) {
		if (aite.getx2() > getx1() && getx2() > aite.getx1() && aite.gety2() > gety1() && gety2() > aite.gety1()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isOutside() {
		if (chara_x < AREA_X1 || chara_y < AREA_Y1 || chara_x + image_w > AREA_X2 || chara_y + image_w > AREA_Y2) {
			return true;
		} else {
			return false;
		}
	}
}
