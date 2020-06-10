import java.awt.event.KeyListener;

public class GameScene extends Scene {
	//Pacman pacman = new Pacman(null);
	//Ghost ghost = new Ghost(null, pacman, null, null);

	public GameScene(KeyListener input) {
		super(input);
		setSubtitle("Game");
		
		// Add Object to Scene as below example
		//addObject(pacman);
		//addObject(ghost);
	}
}
