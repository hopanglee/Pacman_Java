import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

// 2019311737 이진영 작성
public class Ghost extends GameObject {
	private BufferedImage[] ghostSprite;
	/*
	 * 유령 이미지 index (0: 오른쪽, 1: 위쪽, 2: 왼쪽, 3: 아래쪽, frightened : 4, Consumed: 5)
	 */
	private int imageIndex = 0;


	/*
	 * 난이도마다 탐지 시간과 추격 시간 scatter : 탐지, chase : 추격 시간이 갈수록 탐지시간이 짧아지고 추격시간이 늘어남
	 */
	private float modeChangeTimer = 0; // 모드가 바뀔 시간을 잼 -> 필요시 변환
	private float frightenedModeTimer = 0; // frightenMode를 유지할 시간을 잼 -> 필요시 변환
	public int frightenedModeDuration = 9 * 60; // frigtenedMode를 유지할 시간

	private float blinkTimer = 0;
	public int startBlinkingAt = 6 * 60; // frightened 모드가 거의 끝나감을 표시하기 시작하는 시간(안해도됨)

	private boolean frightenedModelsWhite = false; 

	public float scatterModeTimer1 = 4 * 60; // 60 = 60fps때문
	public float chaseModeTimer1 = 4 * 60;
	public float scatterModeTimer2 = 8 * 60;
	public float chaseModeTimer2 = 9 * 60;
	public float scatterModeTimer3 = 7 * 60;
	public float chaseModeTimer3 = 10 * 60;
	public float scatterModeTimer4 = 6 * 60;
	public float chaseModeTimer4 = 11 * 60;
	public float scatterModeTimer5 = 5 * 60;
	public float chaseModeTimer5 = 12 * 60;

	Node ghostHouse; // 맵 가운데 집
	Node currentPosition;
	Pacman pacMan;
	Node currentNode, previousNode, targetNode, homeNode;
	int movingSpeed = 3;
	int frightenedMovingSpeed = 2;
	int previousMovingSpeed;
	int consumedMoveSpeed = 5;
	int normalMoveSpeed = 3;

	GameBoard board;
	Vector2 direction, nextDirection;

	boolean rightDir;

	boolean isActive = false; // 현재 유령이 활성화가 됬는지

	public Mode currentMode = Mode.Scatter; // 유령의 현재 모드
	Mode previousMode;
	private int modeChangeIteration = 1;

	public GhostType ghostType = GhostType.Red;

	public int[] nodeX = new int[510];
	public int[] nodeY = new int[510];
	public int nodeCount = 0;

	private int boardWidth = 21;
	private int boardHeight = 24;
	public float[][] minDistance = new float[boardWidth][boardHeight];

	private int scale = GameBoard.SCALE;

	int ghostReleaseTimer = 0;
	int ghostReleaseDuration = 1 * 60;
	boolean isInGhostHouse = true;

	// Score UI 관련
	int ghostScore;
	int scoreTimer = 0;
	int scoreDuration = 1 * 60;
	int scoreX, scoreY;
	boolean scoreUi = false;

	public enum Mode {
		Scatter, Chase, Consumed, frighted
	}

	public enum GhostType {
		Red, Pink, Blue, Orange
	}

	public Ghost(Node currentNode, Pacman pacMan, GameBoard board, GhostType ghostType) {
		this.currentNode = currentNode; // Node[31]
		this.pacMan = pacMan;
		this.board = board;
		this.ghostType = ghostType;

		ghostSprite = new BufferedImage[7];
		try {
			switch (ghostType) {
			case Red:
				ghostSprite[0] = ImageIO.read(getClass().getResource("/image/ghost_red_right.png")); // 우
				ghostSprite[1] = ImageIO.read(getClass().getResource("/image/ghost_red_up.png")); // 상
				ghostSprite[2] = ImageIO.read(getClass().getResource("/image/ghost_red.png")); // 좌
				ghostSprite[3] = ImageIO.read(getClass().getResource("/image/ghost_red_down.png")); // 하
				// System.out.println("red");
				break;

			case Blue:
				ghostSprite[0] = ImageIO.read(getClass().getResource("/image/ghost_blue_right.png"));
				ghostSprite[1] = ImageIO.read(getClass().getResource("/image/ghost_blue_up.png"));
				ghostSprite[2] = ImageIO.read(getClass().getResource("/image/ghost_blue.png"));
				ghostSprite[3] = ImageIO.read(getClass().getResource("/image/ghost_blue_down.png"));
				// System.out.println("blue");
				break;

			case Orange:
				ghostSprite[0] = ImageIO.read(getClass().getResource("/image/ghost_orange_right.png"));
				ghostSprite[1] = ImageIO.read(getClass().getResource("/image/ghost_orange_up.png"));
				ghostSprite[2] = ImageIO.read(getClass().getResource("/image/ghost_orange.png"));
				ghostSprite[3] = ImageIO.read(getClass().getResource("/image/ghost_orange_down.png"));
				// System.out.println("orange");
				break;

			case Pink:
				ghostSprite[0] = ImageIO.read(getClass().getResource("/image/ghost_pink_right.png"));
				ghostSprite[1] = ImageIO.read(getClass().getResource("/image/ghost_pink_up.png"));
				ghostSprite[2] = ImageIO.read(getClass().getResource("/image/ghost_pink.png"));
				ghostSprite[3] = ImageIO.read(getClass().getResource("/image/ghost_pink_down.png"));
				// System.out.println("pink");
				break;
			}

			ghostSprite[4] = ImageIO.read(getClass().getResource("/image/ghost_frighten.png"));
			ghostSprite[5] = ImageIO.read(getClass().getResource("/image/ghost_white.png"));
			ghostSprite[6] = ImageIO.read(getClass().getResource("/image/ghost_consumed.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start() {
		switch (ghostType) {
		case Red:
			// System.out.println("red");
			homeNode = board.nodes[71];
			ghostReleaseDuration = 0;
			break;

		case Blue:
			// System.out.println("blue");
			homeNode = board.nodes[72];
			ghostReleaseDuration = 1 * 60;
			break;

		case Orange:
			// System.out.println("orange");
			homeNode = board.nodes[73];
			ghostReleaseDuration = 2 * 60;
			break;

		case Pink:
			// System.out.println("pink");
			homeNode = board.nodes[70];
			ghostReleaseDuration = 3 * 60;
			break;
		}

		ghostHouse = board.nodes[31];

		x = currentNode.x;
		y = currentNode.y;

		setBounds(x, y, 25, 25);

		nodeX = board.nodeX;
		nodeY = board.nodeY;
		nodeCount = board.nodes.length;
		for (int i = 0; i < nodeCount; i++) {
			SetDijkstra(nodeX[i], nodeY[i]);
		}

		// 유령 집에 있으면 초기 방향 위로 설정
		direction = Vector2.Up;
		targetNode = currentNode.neighbors[0];

		/*
		 * if(isInGhostHouse) { direction = Vector2.Up; targetNode =
		 * currentNode.neighbors[0]; } else { // 유령 집에 없을 때 초기 방향 설정 direction =
		 * Vector2.Right; targetNode = ChooseNextNode(); }
		 */

		previousNode = currentNode;
	}

	@Override
	public void update() {
		ModeUpdate();
		Move();
		ReleaseGhost();
		if (scoreUi) {
			scoreTimer++;

			if (scoreTimer > scoreDuration) {
				scoreUi = false;
			}
		}
	}

	void SetDijkstra(int x, int y) {
		float[][] distance = new float[boardWidth][boardHeight];
		int[][] check = new int[boardWidth][boardHeight];
		int checkN = 0;

		for (int i = 0; i < boardWidth; i++) {
			for (int j = 0; j < boardHeight; j++) {
				distance[i][j] = -1;
			}
		}
		for (int i = 0; i < nodeCount; i++) {
			distance[nodeX[i]][nodeY[i]] = 9999f;
		}
		distance[x][y] = 0;
		while (true) {
			if (nodeCount + 1 == checkN)
				break;
			float min = 10000;
			int visitX = 0, visitY = 0;
			for (int i = 0; i < boardWidth; i++) {
				for (int j = 0; j < boardHeight; j++) {
					if (min > distance[i][j] && distance[i][j] != -1 && check[i][j] == 0) {
						min = distance[i][j];
						visitX = i;
						visitY = j;
					}
				}
			}

			Node visitNode = board.board[visitX][visitY];
			for (int i = 0; i < visitNode.neighbors.length; i++) {
				if (distance[visitNode.neighbors[i].x / scale][visitNode.neighbors[i].y
						/ scale] > distance[visitX][visitY] + GetDistance(visitNode, visitNode.neighbors[i])) {
					distance[visitNode.neighbors[i].x / scale][visitNode.neighbors[i].y
							/ scale] = distance[visitX][visitY] + GetDistance(visitNode, visitNode.neighbors[i]);
				}
			}
			check[visitX][visitY] = 1;
			checkN++;
		}
		minDistance[x][y] = distance[ghostHouse.x / scale][ghostHouse.y / scale];
	}

	float GetDistance(GameObject a, GameObject b) {
		float dx = a.x - b.x;
		float dy = a.y - b.y;
		float dis = dx * dx + dy * dy;
		return (float) Math.sqrt(dis);
	}

	Node GetTargetTile() {
		Node targetTile = null;
		if (ghostType == GhostType.Red) {
			targetTile = GetRedGhostTargetTile();
		} else if (ghostType == GhostType.Pink) {
			targetTile = GetPinkyGhostTargetTile();
		} else if (ghostType == GhostType.Blue) {
			targetTile = GetBlueGhostTargetTile();
		} else if (ghostType == GhostType.Orange) {
			targetTile = GetOrangeGhostTargetTile();
		}

		return targetTile;
	}

	Node ChooseNextNode() {
		// 현재 유령 모드에 따라서 쫓아갈지 홈노드로 갈지 결정
		Node targetTile = null; // -> 바꿈
		if (currentMode == Mode.Chase) {
			targetTile = GetTargetTile();
		} else if (currentMode == Mode.Scatter) {
			targetTile = homeNode;
		} else if (currentMode == Mode.frighted) {
			targetTile = GetRandomTile();
		} else if (currentMode == Mode.Consumed) {
			targetTile = ghostHouse;
		}

		Node moveToNode = null;
		Node[] foundNodes = new Node[4];
		Vector2[] foundNodesDirection = new Vector2[4];

		int nodeCounter = 0, ch = 0, inverseDirection = 0;

		if (currentMode == Mode.Consumed) {
			for (int i = 0; i < currentNode.neighbors.length; i++) {
				foundNodes[nodeCounter] = currentNode.neighbors[i];
				foundNodesDirection[nodeCounter] = currentNode.validDirection[i];
				nodeCounter++;
				ch = 1;
			}
		} else {
			for (int i = 0; i < currentNode.neighbors.length; i++) {
				Vector2 minusDirection = Vector2.Right; // 팩맨의 현재 가는 방향의 반대방향
				switch (direction) {
				case Up:
					minusDirection = Vector2.Down;
					break;

				case Down:
					minusDirection = Vector2.Up;
					break;

				case Left:
					minusDirection = Vector2.Right;
					break;

				case Right:
					minusDirection = Vector2.Left;
					break;
				case Zero:
					minusDirection = Vector2.Zero;
					break;
				}
				if (currentNode.validDirection[i] != minusDirection) {
					foundNodes[nodeCounter] = currentNode.neighbors[i];
					foundNodesDirection[nodeCounter] = currentNode.validDirection[i];
					nodeCounter++;
					ch = 1;
				} else if (currentNode.validDirection[i] == minusDirection) {
					inverseDirection = i;
				}
			}

		}
		if (ch == 0) {
			foundNodes[nodeCounter] = currentNode.neighbors[inverseDirection];
			foundNodesDirection[nodeCounter] = currentNode.validDirection[inverseDirection];
			nodeCounter++;
		}
		if (nodeCounter == 1) {
			moveToNode = foundNodes[0];
			direction = foundNodesDirection[0];
		} else if (nodeCounter > 1) {
			if (currentMode == Mode.Consumed) {
				float distance = 999999f;
				for (int i = 0; i < foundNodes.length; i++) {
					if (foundNodes[i] != null) {
						if (minDistance[foundNodes[i].x / scale][foundNodes[i].y / scale]
								+ GetDistance(this, foundNodes[i]) < distance) {
							moveToNode = foundNodes[i];
							direction = foundNodesDirection[i];
							distance = minDistance[foundNodes[i].x / scale][foundNodes[i].y / scale]
									+ GetDistance(this, foundNodes[i]);
						}
					}
				}
			} else {
				float leastDistance = 10000f;
				for (int i = 0; i < foundNodes.length; i++) {
					if (foundNodes[i] != null) {
						// if(foundNodes[i] == null) System.out.println("foundNodes["+ i +"] is null");
						float distance = GetDistance(foundNodes[i], targetTile);
						if (distance < leastDistance) {
							leastDistance = distance;
							moveToNode = foundNodes[i];
							direction = foundNodesDirection[i];
						}
					}
				}
			}
		}
		return moveToNode;
	}

	Node GetRedGhostTargetTile() {
		// 팩맨의 포지션을 알고 팩맨의 현재 노드를 바로 따라가는 AI
		Node targetTile = new Node(pacMan.x, pacMan.y);

		return targetTile;
	}

	Node GetPinkyGhostTargetTile() {
		// 팩맨이 가는 방향으로 팩맨의 타겟노드로 가는 AI
		Vector2 pacManOrientation = pacMan.direction;
		int targetx = 0, targety = 0;
		switch (pacManOrientation) {
		case Up:
			targety = -4 * GameBoard.SCALE;
			break;

		case Down:
			targety = 4 * GameBoard.SCALE;
			break;

		case Left:
			targetx = -4 * GameBoard.SCALE;
			break;

		case Right:
			targetx = 4 * GameBoard.SCALE;
			break;

		case Zero:
			break;
		}

		Node targetTile = new Node(pacMan.x + targetx, pacMan.y + targety);

		return targetTile;
	}

	Node GetBlueGhostTargetTile() {
		// 팩맨이 가는 방향으로 팩맨의 4타일 뒤인 곳으로 가는 AI
		Vector2 pacManOrientation = pacMan.direction;
		int targetx = 0, targety = 0;
		switch (pacManOrientation) {
		case Up:
			targety = +4 * GameBoard.SCALE;
			break;

		case Down:
			targety = -4 * GameBoard.SCALE;
			break;

		case Left:
			targetx = +4 * GameBoard.SCALE;
			break;

		case Right:
			targetx = -4 * GameBoard.SCALE;
			break;

		case Zero:
			break;
		}

		Node targetTile = new Node(pacMan.x + targetx, pacMan.y + targety);

		return targetTile;
	}

	Node GetOrangeGhostTargetTile() {
		Vector2 pacManOrientation = pacMan.direction;
		int targetx = 0, targety = 0;
		switch (pacManOrientation) {
		case Up:
			targety = -4 * GameBoard.SCALE;
			break;

		case Down:
			targety = +4 * GameBoard.SCALE;
			break;

		case Left:
			targetx = -4 * GameBoard.SCALE;
			break;

		case Right:
			targetx = 4 * GameBoard.SCALE;
			break;

		case Zero:
			break;
		}

		Node targetTile = new Node(x + targetx, y + targety);

		return targetTile;
	}

	Node GetRandomTile() {
		Random rand = new Random();
		rand.setSeed(System.currentTimeMillis());
		int targetx = rand.nextInt(boardWidth * scale);
		int targety = rand.nextInt(boardHeight * scale);

		Node targetTile = new Node(targetx, targety);

		return targetTile;
	}

	void ModeUpdate() {
		if (currentMode != Mode.frighted && !isInGhostHouse) {
			modeChangeTimer++;
			if (modeChangeIteration == 1) {
				if (currentMode == Mode.Scatter && modeChangeTimer > scatterModeTimer1) {
					ChangeMode(Mode.Chase);
					modeChangeTimer = 0;
				}
				if (currentMode == Mode.Chase && modeChangeTimer > chaseModeTimer1) {
					ChangeMode(Mode.Scatter);
					modeChangeTimer = 0;
					modeChangeIteration = 2;
				}
			} else if (modeChangeIteration == 2) {
				if (currentMode == Mode.Scatter && modeChangeTimer > scatterModeTimer2) {
					ChangeMode(Mode.Chase);
					modeChangeTimer = 0;
				}
				if (currentMode == Mode.Chase && modeChangeTimer > chaseModeTimer2) {
					ChangeMode(Mode.Scatter);
					modeChangeTimer = 0;
					modeChangeIteration = 3;
				}
			} else if (modeChangeIteration == 3) {
				if (currentMode == Mode.Scatter && modeChangeTimer > scatterModeTimer3) {
					ChangeMode(Mode.Chase);
					modeChangeTimer = 0;
				}
				if (currentMode == Mode.Chase && modeChangeTimer > chaseModeTimer3) {
					ChangeMode(Mode.Scatter);
					modeChangeTimer = 0;
					modeChangeIteration = 4;
				}
			} else if (modeChangeIteration == 4) {
				if (currentMode == Mode.Scatter && modeChangeTimer > scatterModeTimer4) {
					ChangeMode(Mode.Chase);
					modeChangeTimer = 0;
				}
				if (currentMode == Mode.Chase && modeChangeTimer > chaseModeTimer4) {
					ChangeMode(Mode.Scatter);
					modeChangeTimer = 0;
					modeChangeIteration = 5;
				}
			} else if (modeChangeIteration == 5) {
				if (currentMode == Mode.Scatter && modeChangeTimer > scatterModeTimer5) {
					ChangeMode(Mode.Chase);
					modeChangeTimer = 0;
				}
				if (currentMode == Mode.Chase && modeChangeTimer > chaseModeTimer5) {
					ChangeMode(Mode.Scatter);
					modeChangeTimer = 0;
					modeChangeIteration = 5;
				}
			}
		} else if (currentMode == Mode.frighted) {
			frightenedModeTimer++;

			if (frightenedModeTimer >= frightenedModeDuration) {
				frightenedModeTimer = 0;
				ChangeMode(previousMode);
			}

			// frightenedMode가 거의 끝나갈때
			if (frightenedModeTimer >= startBlinkingAt) {
				blinkTimer++;
				if (blinkTimer >= 6f) { // 0.1 * 60
					blinkTimer = 0f;

					// 깜빡거리기 표현
					if (frightenedModelsWhite) {
						// 검정색됬다가
						imageIndex = 4;
						frightenedModelsWhite = false;
					} else {
						// 하얀색됬다가
						imageIndex = 5;
						frightenedModelsWhite = true;
					}
				}
			}
		}
	}

	void ChangeMode(Mode m) {
		if (currentMode == Mode.frighted) {
			movingSpeed = previousMovingSpeed;
		}
		if (m == Mode.frighted) {
			previousMovingSpeed = movingSpeed;
			movingSpeed = frightenedMovingSpeed;

			imageIndex = 4;
		}
		if (currentMode != m) {
			previousMode = currentMode;
			currentMode = m;
		}

		// 스프라이트 컨트롤러
	}

	void Move() {
		if (currentNode != targetNode && targetNode != null && !isInGhostHouse) {
			if (OverShotTarget()) {
				currentNode = targetNode;
				x = currentNode.x;
				y = currentNode.y;

				if (currentNode.otherNode != null) {
					currentNode = currentNode.otherNode;
					x = currentNode.x;
					y = currentNode.y;
				}
				targetNode = ChooseNextNode();
				previousNode = currentNode;

				CheckIsInGhostHouse();
				currentNode = null;
			} else {
				switch (direction) {
				case Up:
					if (currentMode != Mode.frighted && currentMode != Mode.Consumed)
						imageIndex = 1;
					y -= movingSpeed;
					break;

				case Down:
					if (currentMode != Mode.frighted && currentMode != Mode.Consumed)
						imageIndex = 3;
					y += movingSpeed;
					break;

				case Left:
					if (currentMode != Mode.frighted && currentMode != Mode.Consumed)
						imageIndex = 2;
					x -= movingSpeed;
					break;

				case Right:
					if (currentMode != Mode.frighted && currentMode != Mode.Consumed)
						imageIndex = 0;
					x += movingSpeed;
					break;

				case Zero:
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

	void CheckIsInGhostHouse() {
		if (currentMode == Mode.Consumed) {
			if (currentNode == ghostHouse) { // 현재노드가 집노드(맵 중앙)라면 세팅 리셋
				movingSpeed = normalMoveSpeed;
				direction = Vector2.Up;
				targetNode = ChooseNextNode();
				previousNode = currentNode;
				currentMode = Mode.Chase;
				// System.out.println("집 도착!");
				// 스프라이트 변경
			}
		}
	}

	// 팩맨과 충돌했을 때 죽음(consumed상태로 변경) (유령이 도망상태인 경우)
	public void Consumed(int score) {
		imageIndex = 6;

		currentMode = Mode.Consumed;
		previousMovingSpeed = movingSpeed;
		movingSpeed = consumedMoveSpeed;

		Node temp;

		if (currentNode == null && !(x == previousNode.x && y == previousNode.y)) {
			if (minDistance[previousNode.x / scale][previousNode.y / scale]
					+ GetDistance(this, previousNode) < minDistance[targetNode.x / scale][targetNode.y / scale]
							+ GetDistance(this, targetNode)) {
				if (x < previousNode.x) {
					// ghost.......previousNode
					direction = Vector2.Right;
				} else if (x > previousNode.x) {
					// previousNode.......ghost
					direction = Vector2.Left;
				} else if (y < previousNode.y) {
					/*
					 * ghost . . . previousNode
					 */
					direction = Vector2.Down;
				} else if (y > previousNode.y) {
					/*
					 * previousNode . . . ghost
					 */
					direction = Vector2.Up;
				}

				// targetNode와 previousNode를 swap
				temp = targetNode;
				targetNode = previousNode;
				previousNode = temp;
			}
		}

		ghostScore = score;
		scoreX = x;
		scoreY = y;
		scoreUi = true;
		scoreTimer = 0;
		// consumed 스프라이트로 변경
	}

	// frightenedMode 시작
	public void StartFrightenedMode() {
		if (currentMode != Mode.Consumed) {
			frightenedModeTimer = 0;
			ChangeMode(Mode.frighted);
		}
	}
	/*
	 * void ReleaseGhosts() {
	 * 
	 * }
	 */

	// 그리기 함수
	@Override
	public void render(Graphics g) {
		g.drawImage(ghostSprite[imageIndex], x, y, null);

		if (scoreUi) {
			g.setColor(Color.WHITE);
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 15));
			g.drawString(String.valueOf(ghostScore), scoreX, scoreY);
		}
	}

	void ReleaseGhost() {
		ghostReleaseTimer++;
		if (ghostReleaseTimer > ghostReleaseDuration) {
			isInGhostHouse = false;
		}
	}
}
