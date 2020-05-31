
public class Node extends GameObject{ // GameObject를 상속받을 때 x,y위치 변수 갖음
	
	public Node[] neighbors = new Node[4]; // 해당 노드에서 갈 수 있는 노드를 저장 (0: 오른쪽, 1: 아래, 2: 왼쪽, 3: 위쪽)
	public boolean[] validDirections = new boolean[4]; // 갈 수 있는 방향들을 저장 (0: 오른쪽, 1: 아래, 2: 왼쪽, 3: 위쪽)
	public boolean isPotal = false; // 이 노드가 포탈인지를 확인
	public Node otherNode = null; // 이 노드가 포탈이라면 통로 노드를 담음
	
	// default Constructer
	public Node() {
		
	}
	
	// neighbor 및 기본 설정
	public void Setting(Node right, Node down, Node left, Node up, boolean isPotal, Node otherNode, int x, int y) {
		neighbors[0] = right;
		neighbors[1] = down;
		neighbors[2] = left;
		neighbors[3] = up;
		
		validDirections[0] = (right != null);
		validDirections[1] = (down != null);
		validDirections[2] = (left != null);
		validDirections[3] = (up != null);
		
		this.isPotal = isPotal;
		this.otherNode = otherNode;
		
		this.x = x;
		this.y = y;
	}

}
