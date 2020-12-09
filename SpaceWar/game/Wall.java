package game;

import java.awt.image.BufferedImage;

public class Wall extends GameChara {

	Wall(int x, int y, BufferedImage img) {
		super(x, y, 40, 20, img, 0, 52 + 64 + 14 + 4, 40, 20, 1);
	}

	@Override
	public void move() {
		chara_x -= 10;
	}

}
