import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.image.*;
import java.util.*;
import javax.swing.JFrame;

public class Game extends JFrame implements Runnable{
	
	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;
	public static final String TITLE = "PACMAN";
	
	private int FPS = 60;

	KeyListener keyInput = new Input();
	
	Scene scene;
	Thread thread;
	
	private static enum SceneState {
		GAME,
		MENU
	}
	private SceneState SCENESTATE;
	
	public Game() {
		setIgnoreRepaint(true);
		setBackground(Color.BLACK);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		start(new GameScene(keyInput));
	}
	
	public synchronized void start(Scene s) {
		Dimension dimension = new Dimension(Game.WIDTH, Game.HEIGHT);
		
		scene = s;
		scene.setPreferredSize(dimension);
		scene.setMinimumSize(dimension);
		scene.setMaximumSize(dimension);
		
		setTitle(Game.TITLE + " - " + scene.getSubtitle());
		
		add(scene);
		setResizable(false);
		pack();
		scene.start();
		setVisible(true);
		
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop() {
	    try {
	    	thread.join();
	    } catch (InterruptedException e) {
	    	e.printStackTrace();
	    } finally {
	    	
	    }
	}
	
	public void run() {
		scene.requestFocus();
		BufferStrategy buffer = scene.getBufferStrategy();
		Graphics g = buffer.getDrawGraphics();
		
		double frameUpdateTime = 1000000000 / FPS;
		
		long previousTime = System.nanoTime();
		long lag = 0;
		
		while(scene.getRunningState() != Scene.RunningState.EXIT) {
			long currentTime = System.nanoTime();
			long elapsedTime = currentTime - previousTime;
			previousTime = currentTime;
			lag += elapsedTime;
			Input.poll();
			
			// fixed time update
			while (lag >= frameUpdateTime) {
				scene.update();
				lag -= frameUpdateTime;
			}
			scene.render();
			g.dispose();
			buffer.show();
		}
		
		stop();
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		
	}
}
