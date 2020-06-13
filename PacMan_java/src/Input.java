import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// 2019311680 ±èÁ¤¿ø ÀÛ¼º
public class Input implements KeyListener {

	private static final int KEY_COUNT = 256;

	private enum KeyState {
		RELEASED, // Not down
		PRESSED, // Down, but not the first time
		ONCE // Down for the first time
	}

	// Current state of the keyboard
	private static boolean[] currentKeys = null;

	// Polled keyboard state
	private static KeyState[] keys = null;

	public Input() {
		currentKeys = new boolean[KEY_COUNT];
		keys = new KeyState[KEY_COUNT];
		for (int i = 0; i < KEY_COUNT; ++i) {
			keys[i] = KeyState.RELEASED;
		}
	}

	public static synchronized void init() {
		for (int i = 0; i < KEY_COUNT; ++i) {
			currentKeys[i] = false;
			keys[i] = KeyState.RELEASED;
		}
	}

	public static synchronized void poll() {
		for (int i = 0; i < KEY_COUNT; ++i) {
			if (currentKeys[i]) {
				if (keys[i] == KeyState.RELEASED)
					keys[i] = KeyState.ONCE;
				else
					keys[i] = KeyState.PRESSED;
			} else {
				keys[i] = KeyState.RELEASED;
			}
		}
	}

	public static boolean getKey(int keyCode) {
		return keys[keyCode] == KeyState.ONCE || keys[keyCode] == KeyState.PRESSED;
	}

	public static boolean getKeyDown(int keyCode) {
		return keys[keyCode] == KeyState.ONCE;
	}

	public static boolean getAnyKeyDown() {
		for (int i = 0; i < KEY_COUNT; i++) {
			if (keys[i] == KeyState.ONCE)
				return true;
		}
		return false;
	}

	public synchronized void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode >= 0 && keyCode < KEY_COUNT) {
			currentKeys[keyCode] = true;
		}
	}

	public synchronized void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode >= 0 && keyCode < KEY_COUNT) {
			currentKeys[keyCode] = false;
		}
	}

	public void keyTyped(KeyEvent e) {
		
	}
}
