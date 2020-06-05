
public class Pacman extends GameObject{
	
	public Vector2 direction; // 팩맨의 이동 방향 
	public boolean ghostAte = false; // 팩맨이 유령들을 잡아먹을 수 있는 상태인지
	public Node currentNode, previousNode, targetNode; // 팩맨이 현재 위치하는 노드, 이전에 위치했던 노드, 가려는 노드
	
	private int speed = 4; // 팩맨 이동속도
	private int imageIndex = 0; // 팩맨 이미지 index (0: 오른쪽, 1: 아래쪽, 2: 왼쪽, 3: 위쪽)
	private int animation_index = 0; // 팩맨 애니메이션 index
	private Vector2 nextDirection; // 방향키를 눌러서 팩맨이 움직일 방향
	
	private GameBoard board;
	
	public Pacman(GameBoard board) {
		this.board = board; // 모든 노드를 담고 있는 게임보드를 받음
	}
	
	@Override
	public void start() {
		currentNode = board.nodes[69];
		
		direction = Vector2.Right;
		changePosition(direction);
	}
	
	@Override
	public void update() {
		//CheckInput(); // 사용자가 어떤 키를 입력했는지 매 프레임마다 체크
		Move(); // 팩맨을 계속 움직임
		ConsumeCoin();
		UpdateOrientation(); // 팩맨이 바라보는 방향의 이미지로 바꿔줌
		//CheckAte();
	}

	Node GetNodePosition() {
		
		return null;
	}
	
	// 포식자 모드로 설정
	public void setAteMode() {
		ghostAte = true;
	}
	
	// 다시 원래모드로 설정
	public void backtoBasicMode() {
		ghostAte = false;
	}
	
	// d방향으로 팩맨의 방향을 바꿈(가능할 시)
	void changePosition(Vector2 d) {
		if(direction != d) { // 팩맨이 가고 있는 방향과 다른 방향인지 체크
			nextDirection = d; // 팩맨이 갈 방향은 d
		}
		if(currentNode != null) {
			Node moveToNode = CanMove(d); // d 방향으로 갈 수 있다면 moveToNode에 갈 수 있는 노드가 저장
			
			if(moveToNode != null) {
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
		
		for(int i = 0; i < currentNode.length; i++) { // 현재 노드에서 이웃한 노드 갯수 만큼 반복
			if(currentNode.validDirection[i] == d) { // 현재 노드에서 갈 수 있는 방향이랑 d의 방향이랑 같은 지 체크
				moveToNode = currentNode.neighbors[i];
				break;
			}
		}		
		
		return moveToNode;
	}
	
	void Move(){
		if(targetNode != currentNode && targetNode != null) {
			Vector2 inverseDirection = Vector2.Right; // 팩맨의 현재 가는 방향의 반대방향
			switch(direction) {
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
			if(nextDirection == inverseDirection) { // 다음 방향이 현재 가고 있는 방향의 반대 방향인지 체크
				direction = nextDirection; // 반대방향으로 팩맨의 방향을 바꿔줌
				//팩맨의 이동방향을 반대로 만들었으니 이전 노드와 다음 노드도 반대로 바꿔줌
				Node tmpNode = targetNode;
				targetNode = previousNode;
				previousNode= tmpNode;
			}
			if(OverShotTarget()) { // 타겟을 지나옴
				currentNode = targetNode;
				x = currentNode.x;
				y = currentNode.y;
				
				if(currentNode.otherNode != null) { // 현재 노드가 포탈이라면 포탈로 이동한 후 현재 노드 변경
					x = currentNode.otherNode.x;
					y = currentNode.otherNode.y;
					currentNode = currentNode.otherNode;
				}
				
				Node moveToNode = CanMove(nextDirection); // 다음 방향에 갈 수 있는 노드를 정함
				if(moveToNode != null) // 갈 수 있는 노드가 있다면 현재 방향을 다음 방향으로 설정
					direction = nextDirection;
				else moveToNode = CanMove(direction); // 없다면 현재방향으로 갈 수 있는 노드를 찾음
				
				if(moveToNode != null) { // 있다면 타겟노드와 이전 노드를 재 설정
					targetNode = moveToNode;
					previousNode = currentNode;
					currentNode = null;
				}
				else direction = Vector2.Zero; // 없다면 팩맨 정지
			}
			else {
				switch(direction) {
				case Up:
					y += speed;
					break;
				case Down:
					y -= speed;
					break;
				case Left:
					x -= speed;
					break;
				case Right:
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
		float fx = (float)targetx;
		float fy = (float)targety;
		
		// 길이: x^2 + y^2값 반환
		return (fx - (float)previousNode.x) * (fx - (float)previousNode.x) + (fy - (float)previousNode.y) * (fy - (float)previousNode.y);
	}
	
	void ConsumeCoin() { // rectangle을 써서 코인이랑 충돌시로
		
	}
	
	void UpdateOrientation() {
		switch(direction) {
		case Up:
			imageIndex = 3; // 이미지를 위를 바라보는 이미지로 바꿈
			break;
		case Down:
			imageIndex = 1; // 이미지를 아래를 바라보는 이미지로 바꿈
			break;
		case Left:
			imageIndex = 2; // 이미지를 왼쪽을 바라보는 이미지로 바꿈
			break;
		case Right:
			imageIndex = 0; // 이미지를 오른쪽을 바라보는 이미지로 바꿈
			break;
		default:
			 // Zero의 경우 그냥 이전 모습으로 놨둠
			break;
		}
	}
}
