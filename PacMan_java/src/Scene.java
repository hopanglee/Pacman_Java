import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;

public class Scene extends Canvas {
	private List<RenderableObject> objs = new ArrayList<RenderableObject>();
	private String subtitle = "";
	private Graphics graphics;
	
	public static enum RunningState {
		RUNNING,
		PAUSED,
		EXIT
	}
	private RunningState RUNNINGSTATE = RunningState.RUNNING;
	
	public Scene(KeyListener Input) {
		setIgnoreRepaint(true);
		addKeyListener(Input);
	}
	
	public void addObject(RenderableObject o) {
		objs.add(o);
	}
	public void addObjects(List<? extends RenderableObject> l) {
		objs.addAll(l);
	}
	
	public void setSubtitle(String s) {
		subtitle = s;
	}
	public String getSubtitle() {
		return subtitle;
	}

	public RunningState getRunningState() {
		return RUNNINGSTATE;
	}
	public void setRunningState(RunningState rstate) {
		RUNNINGSTATE = rstate;
	}
	
	public void start() {
		BufferStrategy buffer = getBufferStrategy();
		if (buffer == null) {
			createBufferStrategy(2);
		}
		
		for (RenderableObject o : objs) {
			o.start();
		}
	}
	
	public void update() {
		for (RenderableObject o : objs) {
			o.update();
		}
	}
	
	public void render() {
		BufferStrategy buffer = getBufferStrategy();
		graphics = buffer.getDrawGraphics();
		graphics.setColor(Color.black);
		graphics.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		
		for (RenderableObject o : objs) {
			o.render(graphics);
		}
		
		graphics.dispose();
		buffer.show();
	}

}
