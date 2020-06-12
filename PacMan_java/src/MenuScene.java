import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MenuScene extends Scene {

	private int menuIndex = 0;
	private static final String[] menuStrings = new String[] { "START", "CREDIT", "EXIT" };
	
	private static BufferedImage PacmanLogo;

	public MenuScene(KeyListener input) {
		super(input);
		setSubtitle("Start Menu");
		try {
			Image img = ImageIO.read(getClass().getResource("/image/Pacman_Logo.png"));
			img = img.getScaledInstance(540, 175, Image.SCALE_AREA_AVERAGING);
			PacmanLogo = new BufferedImage(540, 175, BufferedImage.TYPE_3BYTE_BGR);
			Graphics g = PacmanLogo.getGraphics();
			g.drawImage(img, 0, 0, this);
			g.dispose();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
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
					setRunningState(RunningState.RESTART);
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
			super.render();
			graphics.drawImage(PacmanLogo, Game.WIDTH/2 - PacmanLogo.getWidth()/2, 100, null);
			/*
			for (int i = 0; i < 3; i++) {
				drawStringOnCenter(graphics, )
			}*/
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
