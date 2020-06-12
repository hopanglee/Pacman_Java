import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.event.KeyEvent;

import javax.imageio.ImageIO;

public class Pacman extends GameObject {

	public int score = 0; // 나중에 game클라스나 gameboard클라스의 score에 대체
	int combo = 1; // 유령을 연속으로 잡을시 점수의 배수
	int ghostAteTimer = 3 * 60;
	int comboTime = 150; //(2.5초 * 60)
	public boolean ghostAte = false; // 팩맨이 유령들을 방금 먹었는지

	public Vector2 direction; // 팩맨의 이동 방향
	public Node currentNode, previousNode, targetNode; // 팩맨이 현재 위치하는 노드, 이전에 위치했던 노드, 가려는 노드

	private int speed = 3; // 팩맨 이동속도

	// 스프라이트 관련
	private static BufferedImage[] pacmanSprite;
	private int imageIndex = 0; // 팩맨 이미지 index (0: 오른쪽, 4: 위쪽, 8: 왼쪽, 12: 아래쪽)
	private int animation_index = 0; // 팩맨 애니메이션 index (0,1,2,1)

	private Vector2 nextDirection; // 방향키를 눌러서 팩맨이 움직일 방향

	private MusicPlayer gameOverBgm = new MusicPlayer("/sound/pacman_death.wav");
	private MusicPlayer gameClearBgm = new MusicPlayer("/sound/pacman_intermission.wav");

	private GameBoard board;

	public Pacman(GameBoard board) {
		this.board = board; // 모든 노드를 담고 있는 게임보드를 받음
		pacmanSprite = new BufferedImage[16];
		try {
			pacmanSprite[0] = ImageIO.read(getClass().getResource("/image/PacMan0.PNG"));
			pacmanSprite[1] = ImageIO.read(getClass().getResource("/image/PacMan1.PNG"));
			pacmanSprite[2] = ImageIO.read(getClass().getResource("/image/PacMan2.PNG"));
			pacmanSprite[3] = ImageIO.read(getClass().getResource("/image/PacMan1.PNG"));
			pacmanSprite[4] = ImageIO.read(getClass().getResource("/image/PacMan3.PNG"));
			pacmanSprite[5] = ImageIO.read(getClass().getResource("/image/PacMan4.PNG"));
			pacmanSprite[6] = ImageIO.read(getClass().getResource("/image/PacMan5.PNG"));
			pacmanSprite[7] = ImageIO.read(getClass().getResource("/image/PacMan4.PNG"));
			pacmanSprite[8] = ImageIO.read(getClass().getResource("/image/PacMan6.PNG"));
			pacmanSprite[9] = ImageIO.read(getClass().getResource("/image/PacMan7.PNG"));
			pacmanSprite[10] = ImageIO.read(getClass().getResource("/image/PacMan8.PNG"));
			pacmanSprite[11] = ImageIO.read(getClass().getResource("/image/PacMan7.PNG"));
			pacmanSprite[12] = ImageIO.read(getClass().getResource("/image/PacMan9.PNG"));
			pacmanSprite[13] = ImageIO.read(getClass().getResource("/image/PacMan10.PNG"));
			pacmanSprite[14] = ImageIO.read(getClass().getResource("/image/PacMan11.PNG"));
			pacmanSprite[15] = ImageIO.read(getClass().getResource("/image/PacMan10.PNG"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start() {
		currentNode = board.nodes[69];
		x = currentNode.x;
		y = currentNode.y;

		setBounds(x, y, 25, 25);

		direction = Vector2.Right;
		changePosition(direction);
	}

	@Override
	public void update() {
		CheckInput(); // 사용자가 어떤 키를 입력했는지 매 프레임마다 체크
		Move(); // 팩맨을 계속 움직임
		ConsumeCoin();
		UpdateOrientation(); // 팩맨이 바라보는 방향의 이미지로 바꿔줌

		// 애니메이션 인덱스 조정
		if (animation_index < 9) {
			animation_index++;
		} else
			animation_index = 0;

		/********************************* 충돌 관련 ************************************/
		// Coin과 충돌시 Coin사라짐
		for (int i = 0; i < board.coins.size(); i++) {
			if (this.intersects(board.coins.get(i))) {
				score += 10;
				EventQueue.pushEvent(GameEvent.EventType.RemoveObject, board.coins.get(i));
				// System.out.println("코인과 충돌");
				break;
			}
		}

		// BigCoin과 충돌시 BigCoin사라짐과 동시에 유령은 도망상태로 전환
		for (int i = 0; i < board.bigCoins.size(); i++) {
			if (this.intersects(board.bigCoins.get(i))) {
				score += 50;
				EventQueue.pushEvent(GameEvent.EventType.RemoveObject, board.bigCoins.get(i));

				board.ghosts.get(0).StartFrightenedMode();
				board.ghosts.get(1).StartFrightenedMode();
				board.ghosts.get(2).StartFrightenedMode();
				board.ghosts.get(3).StartFrightenedMode();

				// System.out.println("빅 코인과 충돌");
				break;
			}
		}

		// 모든 coin을 다 먹음 -> Game Clear
		if (board.coins.size() == 0 && board.bigCoins.size() == 0) {
			EventQueue.pushEvent(GameEvent.EventType.GameClear, null);
			gameClearBgm.play();
			// System.out.println("Game Clear!");
			return;
		}

		for (int i = 0; i < board.ghosts.size(); i++) {
			Ghost temp = board.ghosts.get(i);
			if (this.intersects(temp)) {
				if (temp.currentMode != Ghost.Mode.Consumed) { // 이미 먹은 유령이 아니고
					// System.out.println("유령과 충돌");
					if (temp.currentMode == Ghost.Mode.frighted) { // 겁에 질린 유령이라면 유령이 죽음
						int getScore = 200 * combo;
						score += getScore;
						combo *= 2;
						ghostAteTimer = 0;
						ghostAte = true;
						temp.Consumed(getScore);
					} else { // 아니라면 팩맨이 잡힘
								// Game Over (목숨 3개?)
						EventQueue.pushEvent(GameEvent.EventType.GameOver, null);
						gameOverBgm.play();
						System.out.println("Game Over!");
						return;
					}
				}
			}
		}

		if (ghostAte) {
			ghostAteTimer++;

			if (ghostAteTimer > comboTime) {
				combo = 1;
				ghostAte = false;
			}
		}
	}

	private void CheckInput() {
		if (Input.getKey(KeyEvent.VK_RIGHT)) {
			changePosition(Vector2.Right);
		} else if (Input.getKey(KeyEvent.VK_LEFT)) {
			changePosition(Vector2.Left);
		} else if (Input.getKey(KeyEvent.VK_UP)) {
			changePosition(Vector2.Up);
		} else if (Input.getKey(KeyEvent.VK_DOWN)) {
			changePosition(Vector2.Down);
		}
	}

	Node GetNodePosition() {

		return null;
	}

	// d방향으로 팩맨의 방향을 바꿈(가능할 시)
	void changePosition(Vector2 d) {
		if (direction != d) { // 팩맨이 가고 있는 방향과 다른 방향인지 체크
			nextDirection = d; // 팩맨이 갈 방향은 d
		}
		if (currentNode != null) {
			Node moveToNode = CanMove(d); // d 방향으로 갈 수 있다면 moveToNode에 갈 수 있는 노드가 저장

			if (moveToNode != null) {
				direction = d; // 팩맨의 방향을 d 방향으로 바꿈
				targetNode = moveToNode; // 목표노드 설정
				previousNode = currentNode; // 현재 있던 노드를 이전 노드로 저장
				currentNode = null; // 현재 노드 비움
			}
		}
	}

	// d방향에 갈 수 있는 노드를 리턴
	Node CanMove(Vector2 d) {
		Node moveToNode = null; // 팩맨이 가고자 하는 노드 저장할 변수 초기화

		for (int i = 0; i < currentNode.length; i++) { // 현재 노드에서 이웃한 노드 갯수 만큼 반복
			if (currentNode.validDirection[i] == d) { // 현재 노드에서 갈 수 있는 방향이랑 d의 방향이랑 같은 지 체크
				moveToNode = currentNode.neighbors[i];
				if (moveToNode == board.nodes[31]) {
					moveToNode = null;
				}
				break;
			}
		}

		return moveToNode;
	}

	void Move() {
		if (targetNode != currentNode && targetNode != null) {
			Vector2 inverseDirection = Vector2.Right; // 팩맨의 현재 가는 방향의 반대방향
			switch (direction) {
			case Up:
				inverseDirection = Vector2.Down;
				break;

			case Down:
				inverseDirection = Vector2.Up;
				break;

			case Left:
				inverseDirection = Vector2.Right;
				break;

			case Right:
				inverseDirection = Vector2.Left;
				break;
			case Zero:
				inverseDirection = Vector2.Zero;
				break;
			}
			if (nextDirection == inverseDirection) { // 다음 방향이 현재 가고 있는 방향의 반대 방향인지 체크
				direction = nextDirection; // 반대방향으로 팩맨의 방향을 바꿔줌
				// 팩맨의 이동방향을 반대로 만들었으니 이전 노드와 다음 노드도 반대로 바꿔줌
				Node tmpNode = targetNode;
				targetNode = previousNode;
				previousNode = tmpNode;
			}
			if (OverShotTarget()) { // 타겟을 지나옴
				currentNode = targetNode;
				x = currentNode.x;
				y = currentNode.y;

				if (currentNode.otherNode != null) { // 현재 노드가 포탈이라면 포탈로 이동한 후 현재 노드 변경
					x = currentNode.otherNode.x;
					y = currentNode.otherNode.y;
					currentNode = currentNode.otherNode;
				}

				Node moveToNode = CanMove(nextDirection); // 다음 방향에 갈 수 있는 노드를 정함
				if (moveToNode != null) // 갈 수 있는 노드가 있다면 현재 방향을 다음 방향으로 설정
					direction = nextDirection;
				else
					moveToNode = CanMove(direction); // 없다면 현재방향으로 갈 수 있는 노드를 찾음

				if (moveToNode != null) { // 있다면 타겟노드와 이전 노드를 재 설정
					targetNode = moveToNode;
					previousNode = currentNode;
					currentNode = null;
				} else
					direction = Vector2.Zero; // 없다면 팩맨 정지
			} else {
				switch (direction) {
				case Up:
					imageIndex = 3;
					y -= speed;
					break;
				case Down:
					imageIndex = 9;
					y += speed;
					break;
				case Left:
					imageIndex = 6;
					x -= speed;
					break;
				case Right:
					imageIndex = 0;
					x += speed;
					break;
				default:
					break;
				}
			}
		}
	}

	boolean OverShotTarget() {
		float nodeToTarget = LengthFromNode(targetNode.x, targetNode.y); // 이전 노드부터 타겟노드까지의 길이
		float nodeToSelf = LengthFromNode(x, y); // 이전 노드부터 현재 위치까지의 길이
		return nodeToSelf > nodeToTarget; // 현재 위치가 타겟 노드 위치를 넘었으면 true 아니면 false
	}

	float LengthFromNode(int targetx, int targety) {
		float fx = (float) targetx;
		float fy = (float) targety;

		// 길이: x^2 + y^2값 반환
		return (fx - (float) previousNode.x) * (fx - (float) previousNode.x)
				+ (fy - (float) previousNode.y) * (fy - (float) previousNode.y);
	}

	void ConsumeCoin() { // rectangle을 써서 코인이랑 충돌시로

	}

	void UpdateOrientation() {
		switch (direction) {
		case Up:
			imageIndex = 4; // 이미지를 위를 바라보는 이미지로 바꿈
			break;
		case Down:
			imageIndex = 12; // 이미지를 아래를 바라보는 이미지로 바꿈
			break;
		case Left:
			imageIndex = 8; // 이미지를 왼쪽을 바라보는 이미지로 바꿈
			break;
		case Right:
			imageIndex = 0; // 이미지를 오른쪽을 바라보는 이미지로 바꿈
			break;
		default:
			// Zero의 경우 그냥 이전 모습으로 놨둠
			break;
		}
	}

	// 코인 및 유령 및 빅코인과 충돌시 함수

	// 그리기 함수
	@Override
	public void render(Graphics g) {
		g.drawImage(pacmanSprite[imageIndex + animation_index / 3], x, y, null);

		// Score 텍스트
		// g.setColor(Color.BLACK);
		// g.fillRect(30, 860, 150, 70);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Press Start 2P", Font.BOLD, 28));
		g.drawString(String.valueOf(score), 32, 780);
	}
}
