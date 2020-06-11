
public class GameEvent {
	
	public static enum EventType {
		RemoveObject,
		GameOver,
		GameClear,
		GamePaused
	}
	private EventType event;
	private RenderableObject object;
	
	public GameEvent() {
		
	}
	public GameEvent(EventType e, RenderableObject o) {
		setEvent(e);
		setObject(o);
	}

	public EventType getEvent() {
		return event;
	}
	public void setEvent(EventType event) {
		this.event = event;
	}

	public RenderableObject getObject() {
		return object;
	}
	public void setObject(RenderableObject object) {
		this.object = object;
	}

}
