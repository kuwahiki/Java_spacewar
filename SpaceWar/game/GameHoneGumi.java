package game;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

public abstract class GameHoneGumi {
	public static final int GS_STARTGAMEN = 0;
	public static final int GS_STAGESTART = 1;
	public static final int GS_STAGECLEAR = 2;
	public static final int GS_GAMEOVER = 3;
	public static final int GS_GAMEMAIN = 4;

	private int gamestate;
	public JFrame frame1;
	public BufferStrategy bstrategy;
	public Sequencer midiseq = null;
	private int waittimer;
	private int miditime;

	public GameHoneGumi(int w, int h, String title) {
		frame1 = new JFrame(title);
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.setBackground(Color.WHITE);
		frame1.setResizable(false);

		frame1.setVisible(true);
		Insets insets = frame1.getInsets();
		frame1.setSize(w + insets.left + insets.right, h + insets.top + insets.bottom);
		frame1.setLocationRelativeTo(null);

		frame1.createBufferStrategy(2);
		bstrategy = frame1.getBufferStrategy();
		frame1.setIgnoreRepaint(true);

		frame1.addKeyListener(new MyKeyAdapter());
		Timer t = new Timer();
		t.schedule(new MyTimerTask(), 10, 30);
	}

	public void goStartGamen() {
		gamestate = GS_STARTGAMEN;
	}

	public void goStageStart() {
		initStageStart();
		waittimer = 50;
		gamestate = GS_STAGESTART;
	}

	public void goStageClear() {
		initGameClear();
		waittimer = 300;
		gamestate = GS_STAGECLEAR;
	}

	public void goGameMain() {
		gamestate = GS_GAMEMAIN;
	}

	public void goGameOver() {
		initGameOver();
		gamestate = GS_GAMEOVER;
	}

	public abstract void initStageStart();

	public abstract void initGameClear();

	public abstract void initGameOver();

	public abstract void keyPressedGameMain(int keycode);

	public abstract void keyReleasedGameMain(int keycode);

	public void drawStringCenter(String str, int y, Graphics g) {
		int fw = frame1.getWidth() / 2;
		FontMetrics fm = g.getFontMetrics();
		int strw = fm.stringWidth(str) / 2;
		g.drawString(str, fw - strw, y);
	}

	public Clip otoYomikomi(String fname) {
		Clip clip = null;

		try {
			AudioInputStream aistream = AudioSystem.getAudioInputStream(getClass().getResource(fname));
			DataLine.Info info = new DataLine.Info(Clip.class, aistream.getFormat());
			clip = (Clip) AudioSystem.getLine(info);
			clip.open(aistream);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		return clip;
	}

	public void playmidi(Clip clip, int playtime) {
		if (miditime == playtime) {
			clip.setFramePosition(0);
			clip.start();
			miditime = 0;
		} else {
			clip.stop();
			miditime++;
		}

	}

	public void midiYomikomi(String fname) {
		if (midiseq == null) {
			try {
				midiseq = MidiSystem.getSequencer();
				midiseq.open();
			} catch (MidiUnavailableException e1) {
				e1.printStackTrace();
			}
		}
		try {
			Sequence seq = MidiSystem.getSequence(getClass().getResource(fname));
			midiseq.setSequence(seq);
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public abstract void runStartGamen(Graphics g);

	public abstract void runStageStart(Graphics g);

	public abstract void runGameClear(Graphics g);

	public abstract void runGameMain(Graphics g);

	public abstract void runGameOver(Graphics g);

	private class MyKeyAdapter extends KeyAdapter {
		public void keyPressed(KeyEvent ev) {
			if (gamestate == GS_GAMEMAIN) {
				keyPressedGameMain(ev.getKeyCode());
			}
		}

		public void keyReleased(KeyEvent ev) {
			int keycode = ev.getKeyCode();
			switch (gamestate) {
			case GS_GAMEMAIN:
				keyReleasedGameMain(keycode);
				break;
			case GS_STARTGAMEN:
				if (keycode == KeyEvent.VK_P)
					goStageStart();
				break;
			case GS_GAMEOVER:
				if (keycode == KeyEvent.VK_R)
					goStartGamen();
			}
		}
	}

	private class MyTimerTask extends TimerTask {
		public void run() {
			Graphics g = bstrategy.getDrawGraphics();
			if (bstrategy.contentsLost() == false) {
				Insets insets = frame1.getInsets();
				g.translate(insets.left, insets.top);

				switch (gamestate) {
				case GS_STARTGAMEN:
					runStartGamen(g);
					break;
				case GS_STAGESTART:
					runStageStart(g);
					waittimer = waittimer - 1;
					if (waittimer < 0)
						goGameMain();
					break;
				case GS_STAGECLEAR:
					runGameClear(g);
					waittimer = waittimer - 1;
					if (waittimer < 0)
						goStartGamen();
					break;
				case GS_GAMEMAIN:
					runGameMain(g);
					break;
				case GS_GAMEOVER:
					runGameOver(g);
					break;
				}

				bstrategy.show();
				g.dispose();
			}
		}
	}
}
