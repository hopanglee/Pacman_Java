import java.awt.Color;
import java.awt.Font;
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
	private static final String[] creditStrings = new String[] {
			"Kim Jeong-Won, Programmer & Sound",
			"Game Loop, Scenes, Menu UI, and Other Systems",
			"Lee Jin-Yeong, Programmer & Main Director",
			"Pacman, Ghosts, Game UI, and Other Many Objects",
			"",
			"Images, Sounds from PACMAN(1980)",
			"Pacman Main Theme Remixed by Kim Jeong-Won",
			"",
			"Thank you for playing game!",
			"Press Any Key to Continue"
	};
	
	private static BufferedImage PacmanLogo;

	private MusicPlayer bgmPlayer = new MusicPlayer("/sound/PACMAN_THEME_REMIX.wav");

	public MenuScene(KeyListener input) {
		super(input);
		bgmPlayer.play();
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
			} else if (Input.getKeyDown(KeyEvent.VK_ENTER)
					|| Input.getKeyDown(KeyEvent.VK_SPACE)) {
				switch (menuIndex) {
				case 0: // Start Game
					bgmPlayer.close();
					setRunningState(RunningState.RESTART);
					break;
				case 1: // Credit
					setRunningState(RunningState.PAUSED);
					break;
				case 2: // Exit
					bgmPlayer.close();
					setRunningState(RunningState.EXIT);
					break;
				}
			}
			break;
		case PAUSED: // Credit
			if (Input.getAnyKeyDown()) {
				setRunningState(RunningState.RUNNING);
			}
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
			
			int centerX = Game.WIDTH / 2;
			int centerY = Game.HEIGHT / 2 + 100;
			graphics.setFont(new Font("Thin Pixel-7", Font.BOLD, 50));
			for (int i = 0; i < 3; i++) {
				int x = centerX - 210;
				int y = centerY + 80 * (i - 1) - 38;
				if (i == menuIndex) {
					graphics.setColor(Color.WHITE);
					graphics.fillRect(x, y, 420, 50);
					graphics.setColor(Color.black);
					drawStringOnCenter(graphics, menuStrings[i], x, y, 420, 50, StringAlignment.Center);
				} else {
					graphics.setColor(Color.WHITE);
					drawStringOnCenter(graphics, menuStrings[i], x, y, 420, 50, StringAlignment.Center);
				}
			}
			break;
		case PAUSED:
			super.render();
			int x = Game.WIDTH / 2 - 300;
			graphics.setColor(Color.WHITE);
			for (int i = 0; i < 10; i++) {
				int y = 80 * i;
				switch(i) {
				case 0: case 2: case 4: case 5: case 6: case 7:
					graphics.setFont(new Font("Thin Pixel-7", Font.PLAIN, 40));
					break;
				case 1: case 3:
					graphics.setFont(new Font("Thin Pixel-7", Font.PLAIN, 30));
					break;
				case 8:
					graphics.setFont(new Font("Press Start 2P", Font.BOLD, 22));
					break;
				case 9:
					graphics.setColor(Color.yellow);
					graphics.setFont(new Font("Thin Pixel-7", Font.PLAIN, 40));
					break;
				}
				drawStringOnCenter(graphics, creditStrings[i], x, y, 600, 80, StringAlignment.Center);
			}
			break;
		default:
			break;
		}

		graphics.dispose();
		buffer.show();
	}
}
