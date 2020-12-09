package game;

import java.awt.event.KeyEvent;

/**
 * @author a50605
 *
 */
public abstract class SetKey extends GameHoneGumi {

	protected boolean upkey;
	protected boolean downkey;
	protected boolean rightkey;
	protected boolean leftkey;
	protected boolean shiftkey;

	public SetKey(int arg0, int arg1, String arg2) {
		super(arg0, arg1, arg2);
	}

	public void keyPressedGameMain(int key) {
		if (key == KeyEvent.VK_UP)
			upkey = true;
		if (key == KeyEvent.VK_DOWN)
			downkey = true;
		if (key == KeyEvent.VK_LEFT)
			leftkey = true;
		if (key == KeyEvent.VK_RIGHT)
			rightkey = true;
		if (key == KeyEvent.VK_SHIFT)
			shiftkey = true;
	}

	public void keyReleasedGameMain(int key) {
		if (key == KeyEvent.VK_UP)
			upkey = false;
		if (key == KeyEvent.VK_DOWN)
			downkey = false;
		if (key == KeyEvent.VK_LEFT)
			leftkey = false;
		if (key == KeyEvent.VK_RIGHT)
			rightkey = false;
		if (key == KeyEvent.VK_SHIFT)
			shiftkey = false;
	}

}