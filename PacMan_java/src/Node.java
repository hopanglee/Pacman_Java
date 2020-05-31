
public class Node extends GameObject{ // GameObject를 상속받을 때 x,y위치 변수 갖음
	
	public Node[] neighbors = new Node[4]; // 해당 노드에서 갈 수 있는 노드를 저장 (0: 오른쪽, 1: 아래, 2: 왼쪽, 3: 위쪽)
	public boolean[] validDirections = new boolean[4]; // 갈 수 있는 방향들을 저장 (0: 오른쪽, 1: 아래, 2: 왼쪽, 3: 위쪽)
	
	// default Constructer
	public Node() {
		neighbors[0] = null;
		neighbors[1] = null;
		neighbors[2] = null;
		neighbors[3] = null;
		
		validDirections[0] = false;
		validDirections[1] = false;
		validDirections[2] = false;
		validDirections[3] = false;
	}
	
	// basic Constructer
	public Node(Node right, Node down, Node left, Node up) {
		neighbors[0] = right;
		neighbors[1] = down;
		neighbors[2] = left;
		neighbors[3] = up;
		
		validDirections[0] = (right != null);
		validDirections[1] = (down != null);
		validDirections[2] = (left != null);
		validDirections[3] = (up != null);
	}

}
