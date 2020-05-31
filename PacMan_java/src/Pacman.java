
public class Pacman extends GameObject{

	public int direction = 0; // 팩맨의 이동 방향 (0: 오른쪽, 1: 아래쪽, 2: 왼쪽, 3:위쪽)
	public boolean ghostAte = false; // 팩맨이 유령들을 잡아먹을 수 있는 상태인지
	
	private int speed = 4; // 팩맨 이동속도
	private int imageindex = 0; // 팩맨 이미지 index (0: 오른쪽, 1: 아래쪽, 2: 왼쪽, 3: 위쪽)
	private int animation_index = 0; // 팩맨 애니메이션 index
	
	private GameBoard board;
	
	public Pacman(GameBoard board) {
		this.board = board;
	}
	
	@Override
	public void start() {
		//if(node)
	}
	
	@Override
	public void update() {
		
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
	

}
