import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;



public class GameScene extends Scene {

	private GameBoard gameBoard = new GameBoard();

	private int pauseMenuIndex = 0;
	private static final String[] pauseMenuStrings = new String[] { "Resume", "Restart", "Back to Main Menu" };

	private static final int READYTIME = 240;
	private int readyTime = 0;
	
	private MusicPlayer bgmPlayer = new MusicPlayer("/sound/PACMAN_THEME_REMIX.wav");
	private MusicPlayer wakawaka = new MusicPlayer("/sound/pacman_chomp.wav");

	public GameScene(KeyListener input) {
		super(input);
		setSubtitle("Game");
		addObject(new Map());
		addObjectsFromGameBoard(gameBoard);
		EventQueue.popAllEvents();

		bgmPlayer.Play();
	}

	public void addObjectsFromGameBoard(GameBoard gb) {
		addObject(gb.pacman);
		addObjects(gb.bigCoins);
		addObjects(gb.coins);
		addObjects(gb.ghosts);
	}

	@Override
	public void update() {
		switch (getRunningState()) {
		case RUNNING:
			if (Input.getKeyDown(KeyEvent.VK_ESCAPE)) {
				EventQueue.pushEvent(GameEvent.EventType.GamePaused, null);
				return;
			}
			if (readyTime < READYTIME) {
				++readyTime;
				return;
			}
			super.update();
			for (int i = 0; i < EventQueue.size(); i++) {
				GameEvent e = EventQueue.peekEvent();
				switch (e.getEvent()) {
				case RemoveObject:
					EventQueue.popEvent();
					RenderableObject o = e.getObject();
					System.out.println(o.toString());
					removeObject(o);
					if (o instanceof BigCoin) {
						gameBoard.bigCoins.remove(o);
					} else if (o instanceof Coin) {
						gameBoard.coins.remove(o);
						wakawaka.Play();
					}
					o = null;
					break;
				case GameClear:
					bgmPlayer.Stop();
				case GameOver:
					bgmPlayer.Stop();
				case GamePaused:
					bgmPlayer.Stop();
					setRunningState(RunningState.PAUSED);
					return;
				}
			}
			break;
		case PAUSED:

			GameEvent e = EventQueue.peekEvent();
			switch (e.getEvent()) {
			case GameClear:
			case GameOver:
				if (Input.getKeyDown(KeyEvent.VK_R)) {
					setRunningState(RunningState.RESTART);
				} else if (Input.getKeyDown(KeyEvent.VK_ENTER)) {
					setRunningState(RunningState.EXIT);
				}
				break;
			case GamePaused:
				if (Input.getKeyDown(KeyEvent.VK_R)) {
					EventQueue.popEvent();
					setRunningState(RunningState.RESTART);
				} else if (Input.getKeyDown(KeyEvent.VK_DOWN)) {
					if (pauseMenuIndex == 2) {
						pauseMenuIndex = 0;
					} else {
						++pauseMenuIndex;
					}
				} else if (Input.getKeyDown(KeyEvent.VK_UP)) {
					if (pauseMenuIndex == 0) {
						pauseMenuIndex = 2;
					} else {
						--pauseMenuIndex;
					}
				} else if (Input.getKeyDown(KeyEvent.VK_ENTER)) {
					switch (pauseMenuIndex) {
					case 0:
						setRunningState(RunningState.RUNNING);
						break;
					case 1:
						setRunningState(RunningState.RESTART);
						break;
					case 2:
						setRunningState(RunningState.EXIT);
						break;
					}
				} else if (Input.getKeyDown(KeyEvent.VK_ESCAPE)) {
					EventQueue.popEvent();
					setRunningState(RunningState.RUNNING);
				}
				break;
			default:
				System.err.println("Error: Event not available (" + e.getEvent().toString() + ")");
				break;
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
			if (readyTime < READYTIME) {
				graphics.setColor(Color.yellow);
				graphics.setFont(new Font("Press Start 2P", Font.BOLD, 30));
				drawStringOnCenter(graphics, "READY", 8 * GameBoard.SCALE, 14 * GameBoard.SCALE,
						5 * GameBoard.SCALE, 1 * GameBoard.SCALE, StringAlignment.Center);
			}
			break;
		case PAUSED:
			super.render();
			GameEvent e = EventQueue.peekEvent();
			switch (e.getEvent()) {
			case GameClear:
				// TODO: 클리어 화면 렌더링
				graphics.setColor(Color.yellow);
				graphics.setFont(new Font("Press Start 2P", Font.BOLD, 30));
				drawStringOnCenter(graphics, "CLEAR", 8 * GameBoard.SCALE, 14 * GameBoard.SCALE,
						5 * GameBoard.SCALE, 1 * GameBoard.SCALE, StringAlignment.Center);
				break;
			case GameOver:
				// TODO: 게임오버 화면 렌더링
				graphics.setColor(Color.red);
				graphics.setFont(new Font("Press Start 2P", Font.BOLD, 25));
				drawStringOnCenter(graphics, "GAME OVER", 8 * GameBoard.SCALE, 14 * GameBoard.SCALE,
						5 * GameBoard.SCALE, 1 * GameBoard.SCALE, StringAlignment.Center);
				break;
			case GamePaused:
				// TODO: 게임 퍼즈 메뉴 렌더링
				int centerX = Game.WIDTH / 2;
				int centerY = Game.HEIGHT / 2;
				graphics.setColor(Color.black);
				graphics.fillRect(centerX - 250, centerY - 150, 500, 300);
				graphics.setFont(new Font(Font.DIALOG, Font.BOLD, 30));
				for (int i = 0; i < 3; i++) {
					if (i == pauseMenuIndex) {
						graphics.setColor(Color.WHITE);
						graphics.fillRect(centerX - 210, centerY + 100 * (i - 1) - 38, 420, 50);
						graphics.setColor(Color.black);
						graphics.drawString(pauseMenuStrings[i], centerX - 200, centerY + 100 * (i - 1));
					} else {
						graphics.setColor(Color.WHITE);
						graphics.drawString(pauseMenuStrings[i], centerX - 200, centerY + 100 * (i - 1));
					}
				}
				break;
			default:
				System.err.println("Error: Event not available (" + e.getEvent().toString() + ")");
				break;
			}
			break;
		default:
			break;
		}

		graphics.dispose();
		buffer.show();
	}
}
