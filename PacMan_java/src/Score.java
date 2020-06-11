import java.awt.Graphics;

public class Score extends UiObject{
	
	private static Integer score;
	

	public Score() {
		
	}

	@Override
	public void start() {
		
	}

	@Override
	public void update() {
		
	}

	@Override
	public void render(Graphics g) {
		g.drawString("Score   " + score.toString(), x, y);
	}

}
