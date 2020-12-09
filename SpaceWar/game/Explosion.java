package game;

import java.awt.image.BufferedImage;

public class Explosion extends GameChara {
	int waittime;

	public Explosion(int x, int y, BufferedImage img) {
		super(x, y, 0, 0, img, 64, 52 + 64 + 14 + 25, 64, 64, 16);
		waittime = 10;
	}

	public boolean isOutside() {
		if (waittime < 0) {
			return true;
		} else {
			return false;
		}
	}

	public void move() {
		waittime = waittime - 1;
	}
}
