import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

public class MenuScene extends Scene {

	private int menuIndex = 0;
	private static final String[] menuStrings = new String[] { "START", "CREDIT", "EXIT" };

	public MenuScene(KeyListener input) {
		super(input);
		setSubtitle("Start Menu");
		EventQueue.popAllEvents();
	}

	@Override
	public void update() {
		switch (getRunningState()) {
		case RUNNING: // Menu
			if (Input.getKeyDown(KeyEvent.VK_DOWN)) {
				if (menuIndex == 2) {
					menuIndex = 0;
				} else {
					++menuIndex;
				}
			} else if (Input.getKeyDown(KeyEvent.VK_UP)) {
				if (menuIndex == 0) {
					menuIndex = 2;
				} else {
					--menuIndex;
				}
			} else if (Input.getKeyDown(KeyEvent.VK_ENTER)) {
				switch (menuIndex) {
				case 0:
					setRunningState(RunningState.RUNNING);
					break;
				case 1:
					setRunningState(RunningState.PAUSED);
					break;
				case 2:
					setRunningState(RunningState.EXIT);
					break;
				}
			}
			break;
		case PAUSED: // Credit
			break;
		default:
			break;
		}
	}
	
	@Override
	public void render() {
		BufferStrategy buffer = getBufferStrategy();
		Graphics graphics = buffer.getDrawGraphics();
		switch (getRunningState()) {
		case RUNNING:
			break;
		case PAUSED:
			break;
		default:
			break;
		}

		graphics.dispose();
		buffer.show();
	}
}
