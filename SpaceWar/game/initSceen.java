package game;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.sound.sampled.Clip;

/**
 * @author a50605
 *
 */
public abstract class initSceen extends SetKey {

	public static final int SCREEN_W = 640;
	public static final int SCREEN_H = 480;
	protected Player player;
	protected Device[] device = new Device[2];
	@SuppressWarnings("rawtypes")
	protected ArrayList playerbullets;
	@SuppressWarnings("rawtypes")
	protected ArrayList enemy;
	@SuppressWarnings("rawtypes")
	protected ArrayList enemybullets;
	@SuppressWarnings("rawtypes")
	protected ArrayList Boss;
	@SuppressWarnings("rawtypes")
	protected ArrayList BossBullet;
	@SuppressWarnings("rawtypes")
	protected ArrayList explosion;
	@SuppressWarnings("rawtypes")
	protected ArrayList Wall;
	protected int enemynumber = 10;
	protected int ebulletrate = 60;
	protected int score;
	protected int highscore;
	BufferedImage charaimage;
	Clip seClip3;
	Clip seClip4;
	int stagenum=1;
	int premain = 3;
	long starttime;

	public initSceen(int arg0, int arg1, String arg2) {
		super(arg0, arg1, arg2);
	}

	public void initStageStart() {
		//自機
		player = new Player(SCREEN_W/2,SCREEN_H/2,3,charaimage);
		//デバイス
		device[0] = new Device(-10,-10,90,charaimage,player);
		device[1] = new Device(-10,62,270,charaimage,player);
		playerbullets = new ArrayList<Object>();
		enemy = new ArrayList<Object>(); 
		enemybullets = new ArrayList<Object>();
		explosion = new ArrayList<Object>();
		Wall = new ArrayList<Object>();
		upkey=false; downkey=false; rightkey=false;
		leftkey=false;
		starttime = System.currentTimeMillis();
		
		midiseq.setTickPosition(0);
	}

	public void initGameClear() {
		midiseq.stop();
		seClip4.setFramePosition(0);
		seClip4.start();
	}

	public void initGameOver() {
		if (score>highscore) highscore = score;
		score = 0;
		midiseq.stop();
		seClip3.setFramePosition(0);
		seClip3.start();
	}

}