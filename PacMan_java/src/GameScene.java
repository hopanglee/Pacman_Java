import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.Queue;

public class GameScene extends Scene {
	private Queue<RenderableObject> removeRequests = new LinkedList<RenderableObject>();
	
	private GameBoard gameBoard = new GameBoard(removeRequests);

	public GameScene(KeyListener input) {
		super(input);
		setSubtitle("Game");
		addObject(new Map());
		addObjectsFromGameBoard(gameBoard);
	}
	
	public void addObjectsFromGameBoard(GameBoard gb) {
		addObject(gb.pacman);
		addObjects(gb.bigCoins);
		addObjects(gb.coins);
		addObjects(gb.ghosts);
	}
	
	@Override
	public void update() {
		super.update();
		removeObjectsFromRequests(removeRequests);
	}
	
	public void removeObjectsFromRequests(Queue<RenderableObject> requests) {
		for (int i = 0; i < requests.size(); i++) {
			RenderableObject o = requests.peek();
			System.out.println(o.toString());
			requests.remove();
			removeObject(o);
			if (o instanceof BigCoin) {
				gameBoard.bigCoins.remove(o);
			} else if (o instanceof Coin) {
				gameBoard.coins.remove(o);
			}
			o = null;
		}
	}
}
