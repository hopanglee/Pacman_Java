
public class Pacman extends GameObject{

	public int direction = 0; // 팩맨의 이동 방향 (0: 오른쪽, 1: 아래쪽, 2: 왼쪽, 3:위쪽)
	
	private int speed = 4; // 팩맨 이동속도
	private int imageindex = 0; // 팩맨 이미지 index (0: 오른쪽, 1: 아래쪽, 2: 왼쪽, 3: 위쪽)
	private int animation_index = 0; // 팩맨 애니메이션 index
	
	public Pacman() {
		
	}

}
