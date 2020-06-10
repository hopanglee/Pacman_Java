import java.awt.event.KeyListener;

public class GameScene extends Scene {
	
	private GameBoard gameBoard = new GameBoard();
	private Pacman pacman;

	public GameScene(KeyListener input) {
		super(input);
		setSubtitle("Game");
		addObjectsFromGameBoard(gameBoard);
	}
	
	public void addObjectsFromGameBoard(GameBoard gb) {
		addObject(gameBoard.pacman);
		addObjects(gameBoard.bigCoins);
		addObjects(gameBoard.coins);
		addObjects(gameBoard.ghosts);
	}
}
