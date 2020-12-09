package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Iterator;

import javax.sound.sampled.Clip;

/**
 * @author a50605
 *
 */
public abstract class runGame extends initSceen {

	public static final int JIKAN_SEIGEN = 35000; // クリアまでの時間
	public static final int TEKI_RATE = 20;
	public static final String[] TEKIPATTERN = { "-8,0,10,0,0,30,8,0,-10", // 雑魚1
			"-10,0,-5", // 雑魚2
			"-8,0,10,0,0,-60" // ボス
	};

	BufferedImage backimage;
	BufferedImage startimage;
	BufferedImage clearimage;
	BufferedImage gameoverimage;
	int firerate = 0;
	Clip seClip1; // 自機弾発射音
	Clip seClip2; // 敵弾発射音
	Clip seClip5; // 雑魚撃破音
	Clip seClip6; // ボス撃破音
	boolean bossbattle = false;
	boolean enemyhit = false;
	boolean frist = true;
	mylogger logger = new mylogger();
	boolean gameclear = false;
	int Walltime = 3;
	int changtime = 0;

	public runGame(int arg0, int arg1, String arg2) {
		super(arg0, arg1, arg2);
	}

	public void runStartGamen(Graphics g) {
		g.drawImage(startimage, 0, 0, frame1);
	}

	public void runStageStart(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, SCREEN_W, SCREEN_H);
		g.setColor(Color.CYAN);
		g.setFont(new Font("SansSerif", Font.BOLD, 60));
		drawStringCenter("GAME START", 280, g);
		logger.GameStart();
	}

	public void runGameClear(Graphics g) {
		g.drawImage(clearimage, 0, 0, frame1);
		g.setColor(Color.CYAN);
		g.setFont(new Font("SansSerif", Font.PLAIN, 16));
		drawStringCenter("SCORE:" + score, 300, g);
		logger.GameClear(frist);
		Walltime = 3;
		changtime = 0;
		bossbattle = false;
		frist = true;
	}

	@SuppressWarnings("unchecked")
	public void runGameMain(Graphics g) {
		midiseq.start();
		g.drawImage(backimage, 0, 0, frame1);

		long jikansa = System.currentTimeMillis() - starttime;

		// 自機の動き
		player.move(upkey, downkey, leftkey, rightkey);
		device[0].move(upkey, downkey, leftkey, rightkey);
		device[1].move(upkey, downkey, leftkey, rightkey);

		// 自機弾発射
		if (shiftkey == true && firerate == 0) {
			playerbullets.add(new PlayerBullet(player.chara_x + 52, player.chara_y + 20, charaimage));
			playerbullets.add(new PlayerBullet(device[0].chara_x + 5, device[0].chara_y + 5, charaimage));
			playerbullets.add(new PlayerBullet(device[1].chara_x + 5, device[1].chara_y + 5, charaimage));
			firerate = 10;
			Clip clip = seClip1;
			playmidi(clip, 20);
		}
		if (firerate > 0)
			firerate = firerate - 1;
		
		//敵の出現
		if (jikansa > JIKAN_SEIGEN && !bossbattle) {
			// ボス出現
			enemy.add(new Enemy(SCREEN_W, SCREEN_H / 2, 60, charaimage, 2, TEKIPATTERN[2]));
			logger.ApeeraBoss();
			ebulletrate = 7;
			bossbattle = true;
		} else if (jikansa < JIKAN_SEIGEN - 5000 && enemy.size() < enemynumber && Math.random() * TEKI_RATE < 1) {
			// 雑魚出現
			int ptnum = (int) (Math.random() * 2);
			int ty = (int) (Math.random() * 23) * 18;
			enemy.add(new Enemy(SCREEN_W, ty, 2, charaimage, ptnum, TEKIPATTERN[ptnum]));
			logger.ApperaEnemy(ptnum);
		}
		
		// 壁の生成
		if (frist) {
			for (int i = 680; i > -40; i -= 40) {
				// ゲーム開始時に生成
				Wall.add(new Wall(i, 20, charaimage));
				Wall.add(new Wall(i, 440, charaimage));
			}
		}
		if (Walltime == 0) {
			// 追加の壁
			Wall.add(new Wall(640, 20, charaimage));
			Wall.add(new Wall(640, 460, charaimage));
			if (jikansa > JIKAN_SEIGEN - 20000 && jikansa < JIKAN_SEIGEN - 5000) {
				// 途中から生成される壁
				for (int i = 0; i < 4; i++) {
					Wall.add(new Wall(640, 20 * (i + 1), charaimage));
				}
				Wall.add(new Wall(640, 440, charaimage));
			}
			Walltime = 3;
		} else {
			Walltime--;
		}
		// 敵弾発射
		@SuppressWarnings("rawtypes")
		Iterator it = enemy.iterator();
		while (it.hasNext() == true) {
			Enemy tk = (Enemy) it.next();
			if (Math.random() * ebulletrate < 1) {
				if (bossbattle) {
					enemybullets.add(new EnemyBullet(tk.chara_x - 8, tk.chara_y + 10, player.chara_x, player.chara_y, 2,
							charaimage));
					enemybullets.add(new EnemyBullet(tk.chara_x - 8, tk.chara_y + 54, player.chara_x, player.chara_y, 2,
							charaimage));
				} else {
					enemybullets.add(new EnemyBullet(tk.chara_x - 8, tk.chara_y + 24, player.chara_x, player.chara_y, 1,
							charaimage));
				}
				seClip2.stop();
				seClip2.setFramePosition(0);
				seClip2.start();
			}
		}
		// 表示
		player.draw(g, frame1);
		device[0].draw(g, frame1);
		device[1].draw(g, frame1);
		// 壁の表示
		it = Wall.iterator();
		while (it.hasNext() == true) {
			Wall jt = (Wall) it.next();
			jt.draw(g, frame1);
			if (jt.isOutside() == true)
				it.remove();
		}
		// 自機弾の表示
		it = playerbullets.iterator();
		while (it.hasNext() == true) {
			PlayerBullet jt = (PlayerBullet) it.next();
			jt.draw(g, frame1);
			if (jt.isOutside() == true)
				it.remove();
		}
		// 敵の表示
		it = enemy.iterator();
		while (it.hasNext() == true) {
			Enemy tk = (Enemy) it.next();
			tk.draw(g, frame1);
			if (tk.isOutside() == true)
				it.remove();
		}
		// 敵の表示
		it = enemybullets.iterator();
		while (it.hasNext() == true) {
			EnemyBullet tm = (EnemyBullet) it.next();
			tm.draw(g, frame1);
			if (tm.isOutside() == true)
				it.remove();
		}
		// 爆発の表示
		it = explosion.iterator();
		while (it.hasNext() == true) {
			Explosion bh = (Explosion) it.next();
			bh.draw(g, frame1);
			if (bh.isOutside() == true) {
				logger.OutsideEnemy();
				it.remove();
			}
		}

		// 当たり判定（敵と自機弾）
		it = enemy.iterator();
		while (it.hasNext() == true) {
			Enemy tk = (Enemy) it.next();
			@SuppressWarnings("rawtypes")
			Iterator it2 = playerbullets.iterator();
			while (it2.hasNext() == true) {
				PlayerBullet jt = (PlayerBullet) it2.next();
				if (tk.isHit(jt) == true) {
					it2.remove();
					if (tk.die() == true) {
						it.remove();
						explosion.add(new Explosion(tk.chara_x, tk.chara_y, charaimage));
						if (bossbattle == true) {
							gameclear = true;
							score = score + 1000;
							seClip6.stop();
							seClip6.setFramePosition(0);
							seClip6.start();
							logger.DieBoss();
						} else {
							score = score + 100;
							seClip5.stop();
							seClip5.setFramePosition(0);
							seClip5.start();
							logger.DieEnemy(tk.isHit(jt));
						}
						break;
					}
				}
			}
		}
		// 当たり判定（敵と自機）
		it = enemy.iterator();
		while (it.hasNext() == true) {
			Enemy tk = (Enemy) it.next();
			if (tk.isHit(player)) {
				bossbattle = false;
				if (player.die()) {
					goGameOver();
				}
				explosion.add(new Explosion(tk.chara_x, tk.chara_y, charaimage));
				if (bossbattle == true) {
					gameclear = true;
					seClip6.stop();
					seClip6.setFramePosition(0);
					seClip6.start();
				} else {
					seClip5.stop();
					seClip5.setFramePosition(0);
					seClip5.start();
					it.remove();
				}
				break;
			}
		}
		// 当たり判定（敵弾と自機）
		it = enemybullets.iterator();
		while (it.hasNext() == true) {
			EnemyBullet tt = (EnemyBullet) it.next();
			if (tt.isHit(player)) {
				if (player.die()) {
					bossbattle = false;
					goGameOver();
				}
				it.remove();
				break;
			}
		}
		// 当たり判定（自機と壁）
		it = Wall.iterator();
		while (it.hasNext() == true) {
			Wall tk = (Wall) it.next();
			if (tk.isHit(player)) {
				goGameOver();
				it.remove();
				break;
			}
		}
		// ステージクリア
		if (gameclear == true) {
			if (changtime == 100) {
				goStageClear();
			}
			changtime++;
		}

		// スコア表示
		g.setColor(Color.YELLOW);
		g.fillRect(120, 4, (int) ((JIKAN_SEIGEN - jikansa) / 60), 4);
		if (jikansa < JIKAN_SEIGEN) {
			score += 10;
		}
		g.setColor(Color.WHITE);
		g.setFont(new Font("SansSerif", Font.PLAIN, 10));
		g.drawString("SC:" + score + " HI:" + highscore, 2, 10);

		if (frist == true) {
			frist = false;
		}
	}

	public void runGameOver(Graphics g) {
		frist = true;
		g.drawImage(gameoverimage, 0, 0, frame1);
		g.setColor(Color.RED);
		g.setFont(new Font("SansSerif", Font.BOLD, 80));
		drawStringCenter("GAMEOVER", 220, g);
		g.setFont(new Font("SansSerif", Font.PLAIN, 24));
		drawStringCenter("PUSH  R  KEY", 300, g);
		logger.GameOver();
	}

}