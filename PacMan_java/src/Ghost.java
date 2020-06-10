import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;


public class Ghost extends GameObject{
	private static BufferedImage[] ghostSprite;
	/* 
	 * 유령 이미지 index 
	 * (0: 오른쪽, 1: 위쪽, 2: 왼쪽, 3: 아래쪽, frightened : 4, Consumed: 5)
	 */
	private int imageIndex = 1; 
	
	public float ghostReleaseTimer = 0; // 유령이 나오는 시간 -> 사용안할 수도 있음
	public int pinkyReleaseTimer = 5; 
	
	public boolean isInGhostHouse = false; // 유령이 현재 집에 있는지
	
	/* 
	 * 난이도마다 탐지 시간과 추격 시간
	 * scatter : 탐지, chase : 추격
	 * 시간이 갈수록 탐지시간이 짧아지고 추격시간이 늘어남 
	 */
	private float modeChangeTimer = 0; // 모드가 바뀔 시간을 잼 -> 필요시 변환
	private float frightenedModeTimer = 0; //frightenMode를 유지할 시간을 잼 -> 필요시 변환
	public int frightenedModeDuration = 10; // frigtenedMode를 유지할 시간
	
	private float blinkTimer = 0;
	public int startBlinkingAt = 7; // frightened 모드가 거의 끝나감을 표시하기 시작하는 시간(안해도됨)
	
	private boolean frightenedModelsWhite = false;
	
	public float scatterModeTimer1 = 4;
	public float chaseModeTimer1 = 4;
	public float scatterModeTimer2 = 9;
	public float chaseModeTimer2 = 8;
	public float scatterModeTimer3 = 8;
	public float chaseModeTimer3 = 9;
	public float scatterModeTimer4 = 7;
	public float chaseModeTimer4 = 10;
	public float scatterModeTimer5 = 6;
	public float chaseModeTimer5 = 11;
	
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
	
	public enum Mode{
		Scatter,
		Chase,
		Consumed,
		frighted
	}
	
	public enum GhostType{
		Red,
		Pink,
		Blue,
		Orange
	}
	
	public Ghost(Node currentNode, Pacman pacMan, GameBoard board, GhostType ghostType) {
		this.currentNode = currentNode; // Node[31]
		this.pacMan = pacMan;
		this.board = board;
		this.ghostType = ghostType;
		
		ghostSprite = new BufferedImage[4];
		try {
			switch(ghostType) {
			case Red:
				ghostSprite[0] = ImageIO.read(getClass().getResource("/ghost_red.png"));
				ghostSprite[1] = ImageIO.read(getClass().getResource("/ghost_red.png"));
				ghostSprite[2] = ImageIO.read(getClass().getResource("/ghost_red.png"));
				ghostSprite[3] = ImageIO.read(getClass().getResource("/ghost_red.png"));
				break;
				
			case Blue:
				ghostSprite[0] = ImageIO.read(getClass().getResource("/ghost_blue.png"));
				ghostSprite[1] = ImageIO.read(getClass().getResource("/ghost_blue.png"));
				ghostSprite[2] = ImageIO.read(getClass().getResource("/ghost_blue.png"));
				ghostSprite[3] = ImageIO.read(getClass().getResource("/ghost_blue.png"));
				break;
				
			case Orange:
				ghostSprite[0] = ImageIO.read(getClass().getResource("/ghost_orange.png"));
				ghostSprite[1] = ImageIO.read(getClass().getResource("/ghost_orange.png"));
				ghostSprite[2] = ImageIO.read(getClass().getResource("/ghost_orange.png"));
				ghostSprite[3] = ImageIO.read(getClass().getResource("/ghost_orange.png"));
				break;
				
			case Pink:
				ghostSprite[0] = ImageIO.read(getClass().getResource("/ghost_orange.png"));
				ghostSprite[1] = ImageIO.read(getClass().getResource("/ghost_orange.png"));
				ghostSprite[2] = ImageIO.read(getClass().getResource("/ghost_orange.png"));
				ghostSprite[3] = ImageIO.read(getClass().getResource("/ghost_orange.png"));
				break;
			}
			
			ghostSprite[4] = ImageIO.read(getClass().getResource("/ghost_frighten.png"));
			ghostSprite[5] = ImageIO.read(getClass().getResource("/ghost_consumed.png"));
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void start() {
		if(ghostType==GhostType.Red)
        {
            homeNode = board.nodes[71];
        }else if (ghostType == GhostType.Pink)
        {
        	homeNode = board.nodes[70];
        }else  if(ghostType== GhostType.Blue)
        {
        	homeNode = board.nodes[72];
        }
        else if(ghostType== GhostType.Orange)
        {
        	homeNode = board.nodes[73];
        }
		
		nodeX = board.nodeX;
		nodeY = board.nodeY;
		nodeCount = board.nodes.length;
		for(int i = 0; i < nodeCount; i++) {
			SetDijkstra(nodeX[i], nodeY[i]);
		}
		
		if(isInGhostHouse) {
			// 유령 집에 있으면 초기 방향 위로 설정
			direction = Vector2.Up;
			targetNode = currentNode.neighbors[0];
		}
		else {
			// 유령 집에 없을 때 초기 방향 설정
			direction = Vector2.Right;
			targetNode = ChooseNextNode();
		}
		previousNode = currentNode;
	}
	
	@Override
	public void update() {
		ModeUpdate();
		Move();
		CheckIsInGhostHouse();
	}
	
	void SetDijkstra(int x, int y) {
		float[][] distance = new float[boardWidth][boardHeight];
		int[][] check = new int[boardWidth][boardHeight];
		int checkN = 0;
		
		for(int i = 0; i < boardWidth; i++) {
			for(int j = 0; j < boardHeight; j++) {
				distance[i][j] = -1;
			}
		}
		for(int i = 0; i < nodeCount; i++) {
			distance[nodeX[i]][nodeY[i]] = 9999;
		}
		distance[x][y] = 0;
		while(true) {
			if(nodeCount + 1 == checkN)
				break;
			float min = 10000;
			int visitX = 0, visitY = 0;
			for(int i = 0; i < boardWidth; i++) {
				for(int j = 0; j < boardHeight; j++) {
					if(min > distance[i][j] && distance[i][j] != -1 && check[i][j] == 0) {
						min = distance[i][j];
						visitX = i;
						visitY = j;
					}
				}
			}
			Node visitNode = board.board[visitX][visitY];
			for(int i = 0; i < visitNode.neighbors.length; i++) {
				if(distance[visitNode.neighbors[i].x][visitNode.neighbors[i].y] > distance[visitX][visitY] + GetDistance(visitNode, visitNode.neighbors[i])) {
					distance[visitNode.neighbors[i].x][visitNode.neighbors[i].y] = distance[visitX][visitY] + GetDistance(visitNode, visitNode.neighbors[i]);
				}
			}
			check[visitX][visitY] = 1;
			checkN++;
		}
		minDistance[x][y] = distance[ghostHouse.x][ghostHouse.y];
	}
	
	float GetDistance(GameObject a, GameObject b) {
		float dx = a.x - b.y;
		float dy = a.y - b.y;
		float dis = dx * dx + dy * dy;
		return dis;
	}

	Node GetTargetTile() {
		Node targetTile= null;
		if(ghostType==GhostType.Red)
        {
            targetTile = GetRedGhostTargetTile();
        }else if (ghostType == GhostType.Pink)
        {
            targetTile = GetPinkyGhostTargetTile();
        }else  if(ghostType== GhostType.Blue)
        {
            targetTile = GetBlueGhostTargetTile();
        }
        else if(ghostType== GhostType.Orange)
        {
            targetTile = GetOrangeGhostTargetTile();
        }
		
		return targetTile;
	}
	
	Node ChooseNextNode() {
		// 현재 유령 모드에 따라서 쫓아갈지 홈노드로 갈지 결정
		Node targetTile = null; // -> 바꿈
		if(currentMode == Mode.Chase) {
			targetTile = GetTargetTile();
		}
		else if(currentMode == Mode.Scatter) {
			targetTile = homeNode;
		}
		else if(currentMode == Mode.frighted) {
			targetTile = GetRandomTile();
		}
		else if(currentMode == Mode.Consumed) {
			targetTile = ghostHouse;
		}
		
		Node moveToNode = null;
		Node[] foundNodes = new Node[4];
		Vector2[] foundNodesDirection = new Vector2[4];
		
		int nodeCounter = 0, ch = 0, inverseDirection = 0;
		if(currentMode == Mode.Consumed) {
			for(int i = 0; i < currentNode.neighbors.length; i++) {
				foundNodes[nodeCounter] = currentNode.neighbors[i];
				foundNodesDirection[nodeCounter] = currentNode.validDirection[i];
				nodeCounter++;
				ch = 1;
			}
		}
		else {
			for(int i = 0; i < currentNode.neighbors.length; i++) {
				Vector2 minusDirection = Vector2.Right; // 팩맨의 현재 가는 방향의 반대방향
				switch(direction) {
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
				if(currentNode.validDirection[i] != minusDirection) {
					foundNodes[nodeCounter] = currentNode.neighbors[i];
					foundNodesDirection[nodeCounter] = currentNode.validDirection[i];
					nodeCounter++;
					ch = 1;
				}
				else if(currentNode.validDirection[i] == minusDirection) {
					inverseDirection = i;
				}
			}
		}
		if(ch==0) {
			foundNodes[nodeCounter] = currentNode.neighbors[inverseDirection];
			foundNodesDirection[nodeCounter] = currentNode.validDirection[inverseDirection];
			nodeCounter++;
		}
		if(nodeCounter == 1) {
			moveToNode = foundNodes[0];
			direction = foundNodesDirection[0];
		}
		else if(nodeCounter > 1) {
			if(currentMode == Mode.Consumed) {
				float distance = 999999;
				for (int i = 0; i < foundNodes.length; i++) {
					if(foundNodesDirection[i] != Vector2.Zero) {
						if(minDistance[foundNodes[i].x][foundNodes[i].y] + GetDistance(this, foundNodes[i]) < distance) {
							moveToNode = foundNodes[i];
							direction = foundNodesDirection[i];
							distance = minDistance[foundNodes[i].x][foundNodes[i].y] + GetDistance(this, foundNodes[i]);
						}
					}
				}
			}
			else {
				float leastDistance = 10000;
				for(int i = 0; i < foundNodes.length; i++) {
					if(foundNodesDirection[i] != Vector2.Zero) {
						float distance = GetDistance(foundNodes[i], targetTile);
						if(distance < leastDistance) {
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
	
	Node GetRedGhostTargetTile()
    {
        // 팩맨의 포지션을 알고 팩맨의 현재 노드를 바로 따라가는 AI
		Node targetTile = new Node(pacMan.x, pacMan.y);
		
        return targetTile;
    }
    Node GetPinkyGhostTargetTile()
    {
        //팩맨이 가는 방향으로 팩맨의 타겟노드로 가는 AI
    	Vector2 pacManOrientation = pacMan.direction;
    	int targetx = 0, targety = 0;
    	switch(pacManOrientation) {
    	case Up:
    		targety = 4;
    		break;
    		
    	case Down:
    		targety = -4;
    		break;
    		
    	case Left:
    		targetx = -4;
    		break;
    		
    	case Right:
    		targetx = 4;
    		break;
    		
    	case Zero:
    		break;
    	}
    	
    	Node targetTile = new Node(pacMan.x + targetx, pacMan.y + targety);
		
        return targetTile;
    }
    Node GetBlueGhostTargetTile()
    {
        // 팩맨이 가는 방향으로 팩맨의 4타일 뒤인 곳으로 가는 AI
    	Vector2 pacManOrientation = pacMan.direction;
    	int targetx = 0, targety = 0;
    	switch(pacManOrientation) {
    	case Up:
    		targety = -4;
    		break;
    		
    	case Down:
    		targety = +4;
    		break;
    		
    	case Left:
    		targetx = +4;
    		break;
    		
    	case Right:
    		targetx = -4;
    		break;
    		
    	case Zero:
    		break;
    	}
    	
    	Node targetTile = new Node(pacMan.x + targetx, pacMan.y + targety);
		
        return targetTile;
    }
    Node GetOrangeGhostTargetTile()
    {
    	Vector2 pacManOrientation = pacMan.direction;
    	int targetx = 0, targety = 0;
    	switch(pacManOrientation) {
    	case Up:
    		targety = 4;
    		break;
    		
    	case Down:
    		targety = -4;
    		break;
    		
    	case Left:
    		targetx = -4;
    		break;
    		
    	case Right:
    		targetx = 4;
    		break;
    		
    	case Zero:
    		break;
    	}
    	
    	Node targetTile = new Node(x + targetx, y + targety);
		
        return targetTile;
    }
    
    Node GetRandomTile() {
    	Random rand = new Random();
    	int targetx = rand.nextInt(boardWidth);
		int targety = rand.nextInt(boardHeight);
		
		Node targetTile = new Node(targetx, targety);
		
		return targetTile;
    }
    
    void ModeUpdate() {
    	if(currentMode != Mode.frighted) {
    		// modeChangeTimer  += Time.deltaTime;
    		if(modeChangeIteration == 1) {
    			if(currentMode == Mode.Scatter && modeChangeTimer > scatterModeTimer1) {
    				ChangeMode(Mode.Chase);
    				modeChangeTimer = 0;
    			}
    			if(currentMode == Mode.Chase && modeChangeTimer > chaseModeTimer1) {
    				ChangeMode(Mode.Scatter);
    				modeChangeTimer = 0;
    				modeChangeIteration = 2;
    			}
    		}
    		else if(modeChangeIteration == 2) {
    			if(currentMode == Mode.Scatter && modeChangeTimer > scatterModeTimer2) {
    				ChangeMode(Mode.Chase);
    				modeChangeTimer = 0;
    			}
    			if(currentMode == Mode.Chase && modeChangeTimer > chaseModeTimer2) {
    				ChangeMode(Mode.Scatter);
    				modeChangeTimer = 0;
    				modeChangeIteration = 3;
    			}
    		}
    		else if(modeChangeIteration == 3) {
    			if(currentMode == Mode.Scatter && modeChangeTimer > scatterModeTimer3) {
    				ChangeMode(Mode.Chase);
    				modeChangeTimer = 0;
    			}
    			if(currentMode == Mode.Chase && modeChangeTimer > chaseModeTimer3) {
    				ChangeMode(Mode.Scatter);
    				modeChangeTimer = 0;
    				modeChangeIteration = 4;
    			}
    		}
    		else if(modeChangeIteration == 4) {
    			if(currentMode == Mode.Scatter && modeChangeTimer > scatterModeTimer4) {
    				ChangeMode(Mode.Chase);
    				modeChangeTimer = 0;
    			}
    			if(currentMode == Mode.Chase && modeChangeTimer > chaseModeTimer4) {
    				ChangeMode(Mode.Scatter);
    				modeChangeTimer = 0;
    				modeChangeIteration = 5;
    			}
    		}
    		else if(modeChangeIteration == 5) {
    			if(currentMode == Mode.Scatter && modeChangeTimer > scatterModeTimer5) {
    				ChangeMode(Mode.Chase);
    				modeChangeTimer = 0;
    			}
    			if(currentMode == Mode.Chase && modeChangeTimer > chaseModeTimer5) {
    				ChangeMode(Mode.Scatter);
    				modeChangeTimer = 0;
    				modeChangeIteration = 5;
    			}
    		}
    	}
    	else if (currentMode == Mode.frighted) {
    		//frightendModeTimer += Time.deltaTime;
    		
    		if(frightenedModeTimer >= frightenedModeDuration) {
    			frightenedModeTimer = 0;
    			ChangeMode(previousMode);
    		}
    		
    		/* 아마 구현하기 시간없을 것 같음
    		// frightenedMode가 거의 끝나갈때
    		if(frightenedModeTimer >= startBlinkingAt) {
    			//blinkTimer += Time.deltaTime;
    			if(blinkTimer >= 0.1f) {
    				blinkTimer = 0f;
    				
    				// 깜빡거리기 표현
    				if(frightenedModelsWhite) {
    					// 검정색됬다가
    					frightenedModelsWhite = false;
    				}
    				else {
    					// 하얀색됬다가
    					frightenedModelsWhite = true;
    				}
    			}
    		}*/
    	}
    }
    
    void ChangeMode(Mode m) {
    	if(currentMode == Mode.frighted) {
    		movingSpeed = previousMovingSpeed;
    	}
    	if(m == Mode.frighted) {
    		previousMovingSpeed = movingSpeed;
    		movingSpeed = frightenedMovingSpeed;
    		
    		imageIndex = 4;
    	}
    	if(currentMode != m) {
    		previousMode = currentMode;
    		currentMode = m;
    	}
    	
    	// 스프라이트 컨트롤러
    }
    
    void Move() {
    	if(currentNode != targetNode && targetNode != null && !isInGhostHouse) {
    		if(OverShotTarget()) {
    			currentNode = targetNode;
    			x = currentNode.x;
    			y = currentNode.y;
    			
    			if(currentNode.otherNode != null) {
    				currentNode = currentNode.otherNode;
    				x = currentNode.x;
    				y = currentNode.y;
    			}
    			targetNode = ChooseNextNode();
    			previousNode = currentNode;
    			currentNode = null;
    			
    		}
    		else {
    			switch(direction) {
    			case Up:
    				if(currentMode!=Mode.frighted && currentMode != Mode.Consumed) imageIndex = 1;
    				y += movingSpeed;
    				break;
    				
    			case Down:
    				if(currentMode!=Mode.frighted && currentMode != Mode.Consumed) imageIndex = 3;
    				y -= movingSpeed;
    				break;
    				
    			case Left:
    				if(currentMode!=Mode.frighted && currentMode != Mode.Consumed) imageIndex = 2;
    				x -= movingSpeed;
    				break;
    				
    			case Right:
    				if(currentMode!=Mode.frighted && currentMode != Mode.Consumed) imageIndex = 0;
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
    	float fx = (float)targetx;
		float fy = (float)targety;
		
		// 길이: x^2 + y^2값 반환
		return (fx - (float)previousNode.x) * (fx - (float)previousNode.x) + (fy - (float)previousNode.y) * (fy - (float)previousNode.y);
    }
    
    void CheckIsInGhostHouse() {
    	if(currentMode == Mode.Consumed) {
    		if(currentNode == ghostHouse) { // 현재노드가 집노드(맵 중앙)라면 세팅 리셋
    			movingSpeed = normalMoveSpeed;
    			direction = Vector2.Up;
    			targetNode = ChooseNextNode();
    			previousNode = currentNode;
    			currentMode = Mode.Chase;
    			// 스프라이트 변경
    		}
    	}
    }
    
    // 팩맨과 충돌했을 때 죽음(consumed상태로 변경) (유령이 도망상태인 경우)
    public void Consumed() {
    	imageIndex = 5;
    	
    	currentMode = Mode.Consumed;
    	previousMovingSpeed = movingSpeed;
    	movingSpeed = consumedMoveSpeed;
    	
    	Node temp;
    	
    	if(currentNode == null && !(x == previousNode.x && y == previousNode.y)) {
    		if(minDistance[previousNode.x][previousNode.y] + GetDistance(this, previousNode) < minDistance[targetNode.x][targetNode.y] + GetDistance(this, targetNode)) {
    			if(x < previousNode.x) { 
    				// ghost.......previousNode
    				direction = Vector2.Right;
    			}
    			else if(x > previousNode.x) { 
    				// previousNode.......ghost
    				direction = Vector2.Left;
    			}
    			else if(y < previousNode.y) {
    				/*
    				 * previousNode
    				 * .
    				 * .
    				 * .
    				 * ghost
    				 */
    				direction = Vector2.Up;
    			}
    			else if(y > previousNode.y){
    				/*
    				 * ghost
    				 * .
    				 * .
    				 * .
    				 * previousNode
    				 */
    				direction = Vector2.Down;
    			}
    			
    			// targetNode와 previousNode를 swap
    			temp = targetNode;
    			targetNode = previousNode;
    			previousNode = temp;
    		}
    	}
    	
    	// consumed 스프라이트로 변경
    }
        
    // frightenedMode 시작
    public void StartFrightenedMode() {
    	if(currentMode != Mode.Consumed) {
    		frightenedModeTimer = 0;
    		ChangeMode(Mode.frighted);
    	}
    }
    
    
    // 그리기 함수
    @Override
    public void render(Graphics g) {
		g.drawImage(ghostSprite[imageIndex], x, y, null);
	}
}
