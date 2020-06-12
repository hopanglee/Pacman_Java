import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.util.ArrayList;

public class Score extends UiObject {

	private static Integer score;

	public Score(int x, int y, int score) {
		// TODO Auto-generated constructor stub

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
