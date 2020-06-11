import java.awt.event.KeyListener;

public class GameScene extends Scene {
	
	private GameBoard gameBoard = new GameBoard();

	public GameScene(KeyListener input) {
		super(input);
		setSubtitle("Game");
		addObject(new Map());
		addObjectsFromGameBoard(gameBoard);
	}
	
	public void addObjectsFromGameBoard(GameBoard gb) {
		addObject(gameBoard.pacman);
		addObjects(gameBoard.bigCoins);
		addObjects(gameBoard.coins);
		addObjects(gameBoard.ghosts);
	}
}
