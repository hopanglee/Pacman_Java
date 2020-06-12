import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.image.*;
import java.util.*;
import javax.swing.JFrame;

public class Game extends JFrame implements Runnable {

	public static final int WIDTH = 672;
	public static final int HEIGHT = 812;
	public static final String TITLE = "PACMAN";

	private int FPS = 60;

	KeyListener keyInput = new Input();
	Scene scene;
	Thread thread;


	
	private static enum SceneState {
		GAME, MENU
	}

	private SceneState SCENESTATE;

	public Game() {
		setIgnoreRepaint(true);
		setBackground(Color.BLACK);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		requestFocus();

		SCENESTATE = SceneState.GAME;
	}

	public synchronized void start() {

		thread = new Thread(this);
		thread.start();
		

	}

	public synchronized void stop() {
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
		
	}

	public void run() {
		init();

		BufferStrategy buffer = scene.getBufferStrategy();
		Graphics g = buffer.getDrawGraphics();

		double frameUpdateTime = 1000000000 / FPS;

		long previousTime = System.nanoTime();
		long lag = 0;

		while (true) {
			long currentTime = System.nanoTime();
			long elapsedTime = currentTime - previousTime;
			previousTime = currentTime;
			lag += elapsedTime;

			// fixed time update
			while (lag >= frameUpdateTime) {
				Input.poll();
				scene.update();
				lag -= frameUpdateTime;
			}

			switch (scene.getRunningState()) {
			case RESTART:
				System.out.println("Restart!!");
				remove(scene);
				scene = null;
				init();
				buffer = scene.getBufferStrategy();
				g = buffer.getDrawGraphics();
				previousTime = System.nanoTime();
			case EXIT:
				switch (SCENESTATE) {
				case GAME:
					SCENESTATE = SceneState.GAME;
					remove(scene);
					scene = null;
					init();
					buffer = scene.getBufferStrategy();
					g = buffer.getDrawGraphics();
					previousTime = System.nanoTime();
					break;
				case MENU:
					stop();
					break;
				}
			default:
				break;
			}
			scene.render();
		}
	}

	public void init() {
		Input.init();
		setBackground(Color.BLACK);

		Dimension dimension = new Dimension(Game.WIDTH, Game.HEIGHT);

		switch (SCENESTATE) {
		case GAME:
			scene = new GameScene(keyInput);
			break;
		case MENU:
			scene = new MenuScene(keyInput);
			break;
		}
		scene.setPreferredSize(dimension);
		scene.setMinimumSize(dimension);
		scene.setMaximumSize(dimension);

		setTitle(Game.TITLE + " - " + scene.getSubtitle());

		add(scene);
		setResizable(false);
		pack();
		scene.start();
		setVisible(true);
		scene.requestFocus();

	}

	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}
}
