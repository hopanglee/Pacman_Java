import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;

//2019311680 ±èÁ¤¿ø ÀÛ¼º
public class Scene extends Canvas {
	private List<RenderableObject> objs = new ArrayList<RenderableObject>();
	private String subtitle = "";
	private Graphics graphics;

	public static enum RunningState {
		RUNNING, PAUSED, RESTART, EXIT
	}

	private RunningState RUNNINGSTATE = RunningState.RUNNING;

	public Scene(KeyListener Input) {
		setIgnoreRepaint(true);
		addKeyListener(Input);
		setRunningState(RunningState.RUNNING);
	}

	public void addObject(RenderableObject o) {
		objs.add(o);
	}

	public void addObjects(List<? extends RenderableObject> l) {
		objs.addAll(l);
	}

	public void removeObject(RenderableObject o) {
		objs.remove(o);
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
	}

	public static enum StringAlignment {
		Normal, HorizontalCenter, VerticalCenter, Center
	}

	public void drawStringOnCenter(Graphics g, String str, int x, int y, int w, int h, StringAlignment sa) {
		Font f = g.getFont();
		FontMetrics metrics = g.getFontMetrics(f);
		int width = metrics.stringWidth(str) / 2;
		int height = metrics.getHeight() / 2 - metrics.getAscent();
		int centerX = x + w / 2, centerY = y + h / 2;
		switch (sa) {
		case Normal:
			g.drawString(str, x, y);
			break;
		case HorizontalCenter:
			g.drawString(str, centerX - width, y);
			break;
		case VerticalCenter:
			g.drawString(str, x, centerY - height);
			break;
		case Center:
			g.drawString(str, centerX - width, centerY - height);
			break;
		default:
			break;
		}
	}

}
