
public class Ghost extends GameObject{
	
	public float ghostRleaseTimer = 0; // 유령이 나오는 시간
	public int pinkyReleaseTimer = 5; 
	
	public boolean isInGhostHouse = false; // 유령이 현재 집에 있는지
	
	/* 난이도마다 탐지 시간과 추격 시간
	 * scatter : 탐지, chase : 추격
	 * 시간이 갈수록 탐지시간이 짧아지고 추격시간이 늘어남 
	 */
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
	int previousMovingSpeed;
	int consumedMoveSpeed = 5;
	int normalMoveSpeed = 3;
	
	GameBoard board;
	Vector2 direction, nextDirection;
	
	boolean rightDir;
	
	boolean isActive = false; // 현재 유령이 활성화가 됬는지
	
	public Mode currentMode = Mode.Scatter; // 유령의 현재 모드
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
			//targetTile = GetRandomTile();
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
        return pacMan.previousNode;
    }
    Node GetPinkyGhostTargetTile()
    {
        //팩맨이 가는 방향으로 팩맨의 타겟노드로 가는 AI
        return pacMan.targetNode;
    }
    Node GetBlueGhostTargetTile()
    {
        // 팩맨이 가는 방향으로 팩맨의 4타일 뒤인 곳으로 가는 AI
    	return pacMan.previousNode;
    }
    Node GetOrangeGhostTargetTile()
    {
    	return pacMan.targetNode;
    }
}
