
public class Pacman extends GameObject{
	
	public Vector2 direction; // 팩맨의 이동 방향 
	public boolean ghostAte = false; // 팩맨이 유령들을 잡아먹을 수 있는 상태인지
	public Node currentNode, previousNode, targetNode; // 팩맨이 현재 위치하는 노드, 이전에 위치했던 노드, 가려는 노드
	
	private int speed = 4; // 팩맨 이동속도
	private int imageindex = 0; // 팩맨 이미지 index (0: 오른쪽, 1: 아래쪽, 2: 왼쪽, 3: 위쪽)
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

	}
	
	void ConsumeCoin() {
		
	}
}
