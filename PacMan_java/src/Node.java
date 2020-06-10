import java.awt.Graphics;

public class Node extends GameObject{ // GameObject를 상속받을 때 x,y위치 변수 갖음
	
	public Node[] temp = new Node[4]; // 해당 노드에서 갈 수 있는 노드를 임시 저장 (0: 오른쪽, 1: 아래, 2: 왼쪽, 3: 위쪽)
	//public boolean[] canMove = new boolean[4]; // 해당 방향으로 갈 수 있는지 (0: 오른쪽, 1: 아래, 2: 왼쪽, 3: 위쪽)
	public boolean isPotal = false; // 이 노드가 포탈인지를 확인
	public Node otherNode = null; // 이 노드가 포탈이라면 통로 노드를 담음
	
	public Node[] neighbors; // 해당 노드에서 갈 수 있는 노드만 저장
	public Vector2[] validDirection; // 해당 노드에서 갈 수 있는 방향 저장
	public int length = 0; // 이웃의 개수 
	
	// default Constructor
	public Node() {
		
	}
	
	public Node(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	// neighbor 및 기본 설정 -> 이후 최적화 해야할 듯 (gameboard 노가다 바꾸기 싫어서 일단 이렇게 함)
	public void Setting(Node right, Node down, Node left, Node up, boolean isPotal, Node otherNode, int x, int y) {
		temp[0] = right;
		temp[1] = down;
		temp[2] = left;
		temp[3] = up;
		
		/*
		canMove[0] = (right != null);
		canMove[1] = (down != null);
		canMove[2] = (left != null);
		canMove[3] = (up != null);*/
		
		if(right != null) length++;
		if(down != null) length++;
		if(left != null) length++;
		if(up != null) length++;
		
		neighbors = new Node[length];
		validDirection = new Vector2[length];
		
		int j = 0;
		for(int i = 0; i < 4; i++) {
			if(temp[i] != null) {
				neighbors[j] = temp[i];
				switch(i) {
				case 0:
					validDirection[j] = Vector2.Right;
					break;
				case 1:
					validDirection[j] = Vector2.Down;
					break;
				case 2:
					validDirection[j] = Vector2.Left;
					break;
				case 3:
					validDirection[j] = Vector2.Up;
					break;
				}
				j++;
			}
		}
		
		this.isPotal = isPotal;
		this.otherNode = otherNode;
		
		this.x = x;
		this.y = y;
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
		
	}

}
