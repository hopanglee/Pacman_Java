import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

// 2019311737 이진영 작성
public class Coin extends GameObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int scale = GameBoard.SCALE;

	private static BufferedImage coinSprite;

	public Coin(int x, int y) {
		// TODO Auto-generated constructor stub
		this.x = x * scale;
		this.y = y * scale;
		try {
			coinSprite = ImageIO.read(getClass().getResource("/image/smallcoin.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setBounds(this.x, this.y, 27, 27);
	}

	// 팩맨과 충돌시 제거되고 스코어 오름
	public void remove() {

	}

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(coinSprite, x, y, null);
	}
}
