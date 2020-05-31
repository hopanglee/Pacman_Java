import java.awt.Canvas;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Game extends Canvas{
	private ArrayList<RenderableObject> objs = new ArrayList<RenderableObject>();
	
	class KeyInput implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {}
		@Override
		public void keyReleased(KeyEvent e) {}

		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_W:
			case KeyEvent.VK_UP:
				// Pacman goes up
				break;
			case KeyEvent.VK_A:
			case KeyEvent.VK_LEFT:
				// Pacman goes left
				break;
			case KeyEvent.VK_D:
			case KeyEvent.VK_RIGHT:
				// Pacman goes right
				break;
			case KeyEvent.VK_S:
			case KeyEvent.VK_DOWN:
				// Pacman goes down
				break;
			}
		}
	}

	public Game() {
		setBackground(Color.BLACK);
		addKeyListener(new KeyInput());
	}
	
	public void start() {
		for (RenderableObject o : objs) {
			o.start();
		}
	}
	
	public void update() {
		for (RenderableObject o : objs) {
			o.update();
		}
	}
	
	public static void main(String[] args) {
		
	}
}
