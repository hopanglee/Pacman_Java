import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Map extends UiObject{
	private BufferedImage[] wallSprite;
	
	public Map() {
		wallSprite = new BufferedImage[21];
		try {
			wallSprite[0] = ImageIO.read(getClass().getResource("/wall0.png"));
			wallSprite[1] = ImageIO.read(getClass().getResource("/wall1.png"));
			wallSprite[2] = ImageIO.read(getClass().getResource("/wall2.png"));
			wallSprite[3] = ImageIO.read(getClass().getResource("/wall3.png"));
			wallSprite[4] = ImageIO.read(getClass().getResource("/wall4.png"));
			wallSprite[5] = ImageIO.read(getClass().getResource("/wall5.png"));
			wallSprite[6] = ImageIO.read(getClass().getResource("/wall6.png"));
			wallSprite[7] = ImageIO.read(getClass().getResource("/wall7.png"));
			wallSprite[8] = ImageIO.read(getClass().getResource("/wall8.png"));
			wallSprite[9] = ImageIO.read(getClass().getResource("/wall9.png"));
			wallSprite[10] = ImageIO.read(getClass().getResource("/wall10.png"));
			wallSprite[11] = ImageIO.read(getClass().getResource("/wall11.png"));
			wallSprite[12] = ImageIO.read(getClass().getResource("/wall12.png"));
			wallSprite[13] = ImageIO.read(getClass().getResource("/wall13.png"));
			wallSprite[14] = ImageIO.read(getClass().getResource("/wall14.png"));
			wallSprite[15] = ImageIO.read(getClass().getResource("/wall15.png"));
			wallSprite[16] = ImageIO.read(getClass().getResource("/wall16.png"));
			wallSprite[17] = ImageIO.read(getClass().getResource("/wall17.png"));
			wallSprite[18] = ImageIO.read(getClass().getResource("/wall18.png"));
			wallSprite[19] = ImageIO.read(getClass().getResource("/wall19.png"));
			wallSprite[20] = ImageIO.read(getClass().getResource("/wall20.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
		
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
		/*
		g.setColor(Color.blue);
		g.fillRect(35, 35, 602, 26);
		g.fillRect(35, 35, 26, 250);
		g.fillRect(35, 259, 122, 26);
		g.fillRect(131, 259, 26, 90);
		g.fillRect(35, 323, 122, 26);
		g.fillRect(35, 387, 122, 26);
		g.fillRect(131, 387, 26, 90);
		g.fillRect(35, 451, 122, 26);
		g.fillRect(35, 451, 26, 282);
		g.fillRect(35, 579, 58, 26);
		g.fillRect(35, 707, 602, 26);
		g.fillRect(611, 451, 26, 282);
		g.fillRect(579, 579, 58, 26);
		g.fillRect(515, 451, 122, 26);
		g.fillRect(515, 387, 26, 90);
		g.fillRect(515, 387, 122, 26);
		g.fillRect(515, 323, 122, 26);
		g.fillRect(515, 259, 26, 90);
		g.fillRect(515, 259, 122, 26);
		g.fillRect(611, 35, 26, 250);
		g.fillRect(99, 99, 58, 58);
		g.fillRect(195, 99, 90, 58);
		g.fillRect(323, 60, 26, 99);
		g.fillRect(387, 99, 90, 58);
		g.fillRect(515, 99, 58, 58);
		g.fillRect(99, 195, 58, 26);
		g.fillRect(195, 195, 26, 154);
		g.fillRect(259, 195, 154, 26);
		g.fillRect(451, 195, 26, 154);
		g.fillRect(515, 195, 58, 26);
		g.fillRect(195, 259, 90, 26);
		g.fillRect(323, 259, 26, 26);
		g.fillRect(387, 259, 90, 26);
		g.fillRect(99, 195, 58, 26);
		g.fillRect(259, 323, 58, 26);
		g.fillRect(259, 323, 26, 90);
		g.fillRect(259, 387, 154, 26);
		g.fillRect(387, 323, 26, 90);
		g.fillRect(355, 323, 58, 26);
		g.fillRect(195, 387, 26, 90);
		g.fillRect(451, 387, 26, 90);
		g.fillRect(259, 451, 154, 26);
		g.fillRect(323, 451, 26, 90);
		g.fillRect(99, 515, 58, 26);
		g.fillRect(195, 515, 90, 26);
		g.fillRect(387, 515, 90, 26);
		g.fillRect(515, 515, 58, 26);
		g.fillRect(195, 579, 26, 90);
		g.fillRect(259, 579, 154, 26);
		g.fillRect(451, 579, 26, 90);
		g.fillRect(323, 579, 26, 90);
		g.fillRect(99, 643, 186, 26);
		g.fillRect(387, 643, 186, 26);
		g.fillRect(131, 515, 26, 90);
		g.fillRect(515, 515, 26, 90);
		*/
		
		g.drawImage(wallSprite[11], 1 * GameBoard.SCALE, 1 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 2 * GameBoard.SCALE, 1 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 3 * GameBoard.SCALE, 1 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 4 * GameBoard.SCALE, 1 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 5 * GameBoard.SCALE, 1 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 6 * GameBoard.SCALE, 1 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 7 * GameBoard.SCALE, 1 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 8 * GameBoard.SCALE, 1 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 9 * GameBoard.SCALE, 1 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[7], 10 * GameBoard.SCALE, 1 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 11 * GameBoard.SCALE, 1 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 12 * GameBoard.SCALE, 1 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 13 * GameBoard.SCALE, 1 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 14 * GameBoard.SCALE, 1 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 15 * GameBoard.SCALE, 1 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 16 * GameBoard.SCALE, 1 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 17 * GameBoard.SCALE, 1 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 18 * GameBoard.SCALE, 1 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[10], 19 * GameBoard.SCALE, 1 * GameBoard.SCALE, null);
		
		g.drawImage(wallSprite[0], 1 * GameBoard.SCALE, 2 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[0], 10 * GameBoard.SCALE, 2 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[0], 19 * GameBoard.SCALE, 2 * GameBoard.SCALE, null);
		
		g.drawImage(wallSprite[0], 1 * GameBoard.SCALE, 3 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[15], 3 * GameBoard.SCALE, 3 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[14], 4 * GameBoard.SCALE, 3 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[15], 6 * GameBoard.SCALE, 3 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[18], 7 * GameBoard.SCALE, 3 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[14], 8 * GameBoard.SCALE, 3 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[0], 10 * GameBoard.SCALE, 3 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[15], 12 * GameBoard.SCALE, 3 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[18], 13 * GameBoard.SCALE, 3 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[14], 14 * GameBoard.SCALE, 3 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[15], 16 * GameBoard.SCALE, 3 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[14], 17 * GameBoard.SCALE, 3 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[0], 19 * GameBoard.SCALE, 3 * GameBoard.SCALE, null);
		
		g.drawImage(wallSprite[0], 1 * GameBoard.SCALE, 4 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[16], 3 * GameBoard.SCALE, 4 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[17], 4 * GameBoard.SCALE, 4 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[16], 6 * GameBoard.SCALE, 4 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[19], 7 * GameBoard.SCALE, 4 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[17], 8 * GameBoard.SCALE, 4 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[5], 10 * GameBoard.SCALE, 4 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[16], 12 * GameBoard.SCALE, 4 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[19], 13 * GameBoard.SCALE, 4 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[17], 14 * GameBoard.SCALE, 4 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[16], 16 * GameBoard.SCALE, 4 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[17], 17 * GameBoard.SCALE, 4 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[0], 19 * GameBoard.SCALE, 4 * GameBoard.SCALE, null);
		
		g.drawImage(wallSprite[0], 1 * GameBoard.SCALE, 5 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[0], 19 * GameBoard.SCALE, 5 * GameBoard.SCALE, null);
	
		g.drawImage(wallSprite[0], 1 * GameBoard.SCALE, 6 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[4], 3 * GameBoard.SCALE, 6 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[2], 4 * GameBoard.SCALE, 6 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[3], 6 * GameBoard.SCALE, 6 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[4], 8 * GameBoard.SCALE, 6 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 9 * GameBoard.SCALE, 6 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 10 * GameBoard.SCALE, 6 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 11 * GameBoard.SCALE, 6 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[2], 12 * GameBoard.SCALE, 6 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[3], 14 * GameBoard.SCALE, 6 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[4], 16 * GameBoard.SCALE, 6 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[2], 17 * GameBoard.SCALE, 6 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[0], 19 * GameBoard.SCALE, 6 * GameBoard.SCALE, null);
		
		g.drawImage(wallSprite[0], 1 * GameBoard.SCALE, 7 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[0], 6 * GameBoard.SCALE, 7 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[0], 14 * GameBoard.SCALE, 7 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[0], 19 * GameBoard.SCALE, 7 * GameBoard.SCALE, null);
		
		g.drawImage(wallSprite[12], 1 * GameBoard.SCALE, 8 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 2 * GameBoard.SCALE, 8 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 3 * GameBoard.SCALE, 8 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[10], 4 * GameBoard.SCALE, 8 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[8], 6 * GameBoard.SCALE, 8 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 7 * GameBoard.SCALE, 8 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[2], 8 * GameBoard.SCALE, 8 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[20], 10 * GameBoard.SCALE, 8 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[4], 12 * GameBoard.SCALE, 8 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 13 * GameBoard.SCALE, 8 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[6], 14 * GameBoard.SCALE, 8 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[11], 16 * GameBoard.SCALE, 8 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 17 * GameBoard.SCALE, 8 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 18 * GameBoard.SCALE, 8 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[13], 19 * GameBoard.SCALE, 8 * GameBoard.SCALE, null);
		
		g.drawImage(wallSprite[0], 4 * GameBoard.SCALE, 9 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[0], 6 * GameBoard.SCALE, 9 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[0], 14 * GameBoard.SCALE, 9 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[0], 16 * GameBoard.SCALE, 9 * GameBoard.SCALE, null);
		
		g.drawImage(wallSprite[1], 1 * GameBoard.SCALE, 10 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 2 * GameBoard.SCALE, 10 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 3 * GameBoard.SCALE, 10 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[13], 4 * GameBoard.SCALE, 10 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[5], 6 * GameBoard.SCALE, 10 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[11], 8 * GameBoard.SCALE, 10 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[2], 9 * GameBoard.SCALE, 10 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[4], 11 * GameBoard.SCALE, 10 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[10], 12 * GameBoard.SCALE, 10 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[5], 14 * GameBoard.SCALE, 10 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[12], 16 * GameBoard.SCALE, 10 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 17 * GameBoard.SCALE, 10 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 18 * GameBoard.SCALE, 10 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 19 * GameBoard.SCALE, 10 * GameBoard.SCALE, null);
		
		g.drawImage(wallSprite[0], 8 * GameBoard.SCALE, 11 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[0], 12 * GameBoard.SCALE, 11 * GameBoard.SCALE, null);
		
		g.drawImage(wallSprite[1], 1 * GameBoard.SCALE, 12 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 2 * GameBoard.SCALE, 12 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 3 * GameBoard.SCALE, 12 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[10], 4 * GameBoard.SCALE, 12 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[3], 6 * GameBoard.SCALE, 12 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[12], 8 * GameBoard.SCALE, 12 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 9 * GameBoard.SCALE, 12 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 10 * GameBoard.SCALE, 12 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 11 * GameBoard.SCALE, 12 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[13], 12 * GameBoard.SCALE, 12 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[3], 14 * GameBoard.SCALE, 12 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[11], 16 * GameBoard.SCALE, 12 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 17 * GameBoard.SCALE, 12 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 18 * GameBoard.SCALE, 12 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 19 * GameBoard.SCALE, 12 * GameBoard.SCALE, null);
		
		g.drawImage(wallSprite[0], 4 * GameBoard.SCALE, 13 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[0], 6 * GameBoard.SCALE, 13 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[0], 14 * GameBoard.SCALE, 13 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[0], 16 * GameBoard.SCALE, 13 * GameBoard.SCALE, null);
		
		g.drawImage(wallSprite[11], 1 * GameBoard.SCALE, 14 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 2 * GameBoard.SCALE, 14 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 3 * GameBoard.SCALE, 14 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[13], 4 * GameBoard.SCALE, 14 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[5], 6 * GameBoard.SCALE, 14 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[4], 8 * GameBoard.SCALE, 14 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 9 * GameBoard.SCALE, 14 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[7], 10 * GameBoard.SCALE, 14 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 11 * GameBoard.SCALE, 14 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[2], 12 * GameBoard.SCALE, 14 * GameBoard.SCALE, null);		
		g.drawImage(wallSprite[5], 14 * GameBoard.SCALE, 14 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[12], 16 * GameBoard.SCALE, 14 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 17 * GameBoard.SCALE, 14 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 18 * GameBoard.SCALE, 14 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[10], 19 * GameBoard.SCALE, 14 * GameBoard.SCALE, null);
		
		g.drawImage(wallSprite[0], 1 * GameBoard.SCALE, 15 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[0], 10 * GameBoard.SCALE, 15 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[0], 19 * GameBoard.SCALE, 15 * GameBoard.SCALE, null);
		
		g.drawImage(wallSprite[0], 1 * GameBoard.SCALE, 16 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[4], 3 * GameBoard.SCALE, 16 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[10], 4 * GameBoard.SCALE, 16 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[4], 6 * GameBoard.SCALE, 16 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 7 * GameBoard.SCALE, 16 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[2], 8 * GameBoard.SCALE, 16 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[5], 10 * GameBoard.SCALE, 16 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[4], 12 * GameBoard.SCALE, 16 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 13 * GameBoard.SCALE, 16 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[2], 14 * GameBoard.SCALE, 16 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[11], 16 * GameBoard.SCALE, 16 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[2], 17 * GameBoard.SCALE, 16 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[0], 19 * GameBoard.SCALE, 16 * GameBoard.SCALE, null);
		
		g.drawImage(wallSprite[0], 1 * GameBoard.SCALE, 17 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[0], 4 * GameBoard.SCALE, 17 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[0], 16 * GameBoard.SCALE, 17 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[0], 19 * GameBoard.SCALE, 17 * GameBoard.SCALE, null);
		
		g.drawImage(wallSprite[8], 1 * GameBoard.SCALE, 18 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[2], 2 * GameBoard.SCALE, 18 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[5], 4 * GameBoard.SCALE, 18 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[3], 6 * GameBoard.SCALE, 18 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[4], 8 * GameBoard.SCALE, 18 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 9 * GameBoard.SCALE, 18 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[7], 10 * GameBoard.SCALE, 18 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 11 * GameBoard.SCALE, 18 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[2], 12 * GameBoard.SCALE, 18 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[3], 14 * GameBoard.SCALE, 18 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[5], 16 * GameBoard.SCALE, 18 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[4], 18 * GameBoard.SCALE, 18 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[6], 19 * GameBoard.SCALE, 18 * GameBoard.SCALE, null);
		
		g.drawImage(wallSprite[0], 1 * GameBoard.SCALE, 19 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[0], 6 * GameBoard.SCALE, 19 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[0], 10 * GameBoard.SCALE, 19 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[0], 14 * GameBoard.SCALE, 19 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[0], 19 * GameBoard.SCALE, 19 * GameBoard.SCALE, null);
		
		g.drawImage(wallSprite[0], 1 * GameBoard.SCALE, 20 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[4], 3 * GameBoard.SCALE, 20 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 4 * GameBoard.SCALE, 20 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 5 * GameBoard.SCALE, 20 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[9], 6 * GameBoard.SCALE, 20 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 7 * GameBoard.SCALE, 20 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[2], 8 * GameBoard.SCALE, 20 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[5], 10 * GameBoard.SCALE, 20 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[4], 12 * GameBoard.SCALE, 20 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 13 * GameBoard.SCALE, 20 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[9], 14 * GameBoard.SCALE, 20 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 15 * GameBoard.SCALE, 20 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 16 * GameBoard.SCALE, 20 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[2], 17 * GameBoard.SCALE, 20 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[0], 19 * GameBoard.SCALE, 20 * GameBoard.SCALE, null);
		
		g.drawImage(wallSprite[0], 1 * GameBoard.SCALE, 21 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[0], 19 * GameBoard.SCALE, 21 * GameBoard.SCALE, null);
		
		g.drawImage(wallSprite[12], 1 * GameBoard.SCALE, 22 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 2 * GameBoard.SCALE, 22 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 3 * GameBoard.SCALE, 22 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 4 * GameBoard.SCALE, 22 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 5 * GameBoard.SCALE, 22 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 6 * GameBoard.SCALE, 22 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 7 * GameBoard.SCALE, 22 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 8 * GameBoard.SCALE, 22 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 9 * GameBoard.SCALE, 22 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 10 * GameBoard.SCALE, 22 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 11 * GameBoard.SCALE, 22 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 12 * GameBoard.SCALE, 22 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 13 * GameBoard.SCALE, 22 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 14 * GameBoard.SCALE, 22 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 15 * GameBoard.SCALE, 22 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 16 * GameBoard.SCALE, 22 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 17 * GameBoard.SCALE, 22 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[1], 18 * GameBoard.SCALE, 22 * GameBoard.SCALE, null);
		g.drawImage(wallSprite[13], 19 * GameBoard.SCALE, 22 * GameBoard.SCALE, null);
		
		
		g.setColor(Color.white);
		g.fillRect(317, 333, 38, 3);
	}

}
