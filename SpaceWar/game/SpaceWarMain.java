package game;

import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author a50605
 * @version 1.4
 */
public class SpaceWarMain extends runGame {
	// コンストラクタ
	public SpaceWarMain() {
		super(SCREEN_W, SCREEN_H, "宇宙シューティング");

		try {
			charaimage = ImageIO.read(getClass().getResource("gamechara.png"));
			backimage = ImageIO.read(getClass().getResource("backimage.jpg"));
			startimage = ImageIO.read(getClass().getResource("title.jpg"));
			clearimage = ImageIO.read(getClass().getResource("gameclear.jpg"));
			gameoverimage = ImageIO.read(getClass().getResourceAsStream("gameover.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		seClip1 = otoYomikomi("shoot_player.wav");
		seClip2 = otoYomikomi("shoot_enemy.wav");
		seClip3 = otoYomikomi("sound_gameover.wav");
		seClip4 = otoYomikomi("sound_gameclear.wav");
		seClip5 = otoYomikomi("explosion_enemy.wav");
		seClip6 = otoYomikomi("explosion_boss.wav");
		midiYomikomi("BGM_gameplay.mid");

		goStartGamen();
	}

	public static void main(String[] args) {

		@SuppressWarnings("unused")
		SpaceWarMain usm = new SpaceWarMain();
	}
}
