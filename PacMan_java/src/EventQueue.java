
import java.util.LinkedList;
import java.util.Queue;

//2019311680 ±èÁ¤¿ø ÀÛ¼º
public class EventQueue {

	private static Queue<GameEvent> events = new LinkedList<GameEvent>();

	public EventQueue() {

	}

	public static GameEvent peekEvent() {
		return events.peek();
	}

	public static void pushEvent(GameEvent e) {
		events.offer(e);
	}

	public static void pushEvent(GameEvent.EventType type, RenderableObject o) {
		events.offer(new GameEvent(type, o));
	}

	public static void popEvent() {
		events.remove();
	}

	public static void popAllEvents() {
		events.clear();
	}

	public static int size() {
		return events.size();
	}
}
