import java.util.List;

import java.util.ArrayList;

public class GameBoard { // Node들을 저장할 class

	public Node[] nodes; // 맵에 존재하는 모든 노드를 담을 배열
	public int[] nodeX = new int[510];
	public int[] nodeY = new int [510];
	
	public List<Coin> coins; // 맵에 존재하는 모든 코인을 담을 배열
	public List<BigCoin> bigCoins; // 맵에 존재하는 모든 bigCoin을 담을 배열
	public List<Ghost> ghosts; // 맵에 존재하는 모든 유령을 담을 배열
	public Pacman pacman; // 팩맨 생성
	
	private int boardWidth = 21;
	private int boardHeight = 24;
	public Node[][] board = new Node[boardWidth][boardHeight]; // 노드위치에 해당 노드 저장
	
	public GameBoard() {
		nodes = new Node[74];
		
		nodes[0].Setting(nodes[1], nodes[6], null, null, false, null, 2, 2);
		nodes[1].Setting(nodes[2], nodes[7], nodes[0], null, false, null, 5, 2);
		nodes[2].Setting(null, nodes[9], nodes[1], null, false, null, 9, 2);
		nodes[3].Setting(nodes[4], nodes[10], null, null, false, null, 11, 2);
		nodes[4].Setting(nodes[5], nodes[12], nodes[3], null, false, null, 15, 2);
		nodes[5].Setting(null, nodes[13], nodes[4], null, false, null, 18, 2);
		nodes[6].Setting(nodes[7], nodes[14], null, nodes[0], false, null, 2, 5);
		nodes[7].Setting(nodes[8], nodes[15], nodes[6], nodes[1], false, null, 5, 5);
		nodes[8].Setting(nodes[9], nodes[16], nodes[7], null, false, null, 7, 5);
		nodes[9].Setting(nodes[10], null, nodes[8], nodes[2], false, null, 9, 5);
		nodes[10].Setting(nodes[11], null, nodes[9], nodes[3], false, null, 11, 5);
		nodes[11].Setting(nodes[12], nodes[20], nodes[10], null, false, null, 13, 5);
		nodes[12].Setting(nodes[13], nodes[21], nodes[11], nodes[4], false, null, 15, 5);
		nodes[13].Setting(null, nodes[22], nodes[12], nodes[5], false, null, 18, 5);
		nodes[14].Setting(nodes[15], null, null, nodes[6], false, null, 2, 7);
		nodes[15].Setting(null, nodes[29], nodes[14], nodes[7], false, null, 5, 7);
		nodes[16].Setting(nodes[17], null, null, nodes[8], false, null, 7, 7);
		nodes[17].Setting(nodes[18], nodes[24], nodes[16], null, false, null, 9, 7);
		nodes[18].Setting(nodes[19], null, nodes[17], null, false, null, 10, 7);
		nodes[19].Setting(nodes[20], nodes[26], nodes[18], null, false, null, 11, 7);
		nodes[20].Setting(null, null, nodes[19], nodes[11], false, null, 13, 7);
		nodes[21].Setting(nodes[22], nodes[33], null, nodes[12], false, null, 15, 7);
		nodes[22].Setting(null, null, nodes[21], nodes[13], false, null, 18, 7);
		nodes[23].Setting(nodes[24], nodes[30], null, null, false, null, 7, 9);
		nodes[24].Setting(nodes[25], null, nodes[23], nodes[17], false, null, 9, 9);
		nodes[25].Setting(nodes[26], nodes[31], nodes[24], null, false, null, 10, 9);
		nodes[26].Setting(nodes[27], null, nodes[25], nodes[19], false, null, 11, 9);
		nodes[27].Setting(null, nodes[32], nodes[26], null, false, null, 13, 9);
		nodes[28].Setting(nodes[29], null, null, null, true, nodes[34], 1, 11);
		nodes[29].Setting(nodes[30], nodes[38], nodes[28], nodes[15], false, null, 5, 11);
		nodes[30].Setting(null, nodes[35], nodes[29], nodes[23], false, null, 7, 11);
		nodes[31].Setting(null, null, null, nodes[35], false, null, 10, 11);
		nodes[32].Setting(nodes[33], nodes[36], null, nodes[27], false, null, 13, 11);
		nodes[33].Setting(nodes[34], nodes[43], nodes[32], nodes[21], false, null, 15, 11);
		nodes[34].Setting(null, null, nodes[33], null, true, nodes[28], 2, 11);
		nodes[35].Setting(nodes[69], nodes[39], null, nodes[30], false, null, 7, 13);
		nodes[36].Setting(null, nodes[42], nodes[69], nodes[32], false, null, 13, 13);
		nodes[37].Setting(nodes[38], nodes[45], null, null, false, null, 2, 15);
		nodes[38].Setting(nodes[39], nodes[47], nodes[37], nodes[29], false, null, 5, 15);
		nodes[39].Setting(nodes[40], null, nodes[38], nodes[35], false, null, 7, 15);
		nodes[40].Setting(null, nodes[49], nodes[39], null, false, null, 9, 15);
		nodes[41].Setting(nodes[42], nodes[50], null, null, false, null, 11, 15);
		nodes[42].Setting(nodes[43], null, nodes[41], nodes[36], false, null, 13, 15);
		nodes[43].Setting(nodes[44], nodes[52], nodes[42], nodes[33], false, null, 15, 15);
		nodes[44].Setting(null, nodes[54], nodes[43], null, false, null, 18, 15);
		nodes[45].Setting(nodes[46], null, null, nodes[37], false, null, 2, 17);
		nodes[46].Setting(null, nodes[56], nodes[45], null, false, null, 3, 17);
		nodes[47].Setting(nodes[48], nodes[57], null, nodes[38], false, null, 5, 17);
		nodes[48].Setting(nodes[49], nodes[58], nodes[47], null, false, null, 7, 17);
		nodes[49].Setting(nodes[50], null, nodes[48], nodes[40], false, null, 9, 17);
		nodes[50].Setting(nodes[51], null, nodes[49], nodes[41], false, null, 11, 17);
		nodes[51].Setting(nodes[52], nodes[61], nodes[50], null, false, null, 13, 17);
		nodes[52].Setting(null, nodes[62], nodes[51], nodes[43], false, null, 15, 17);
		nodes[53].Setting(nodes[54], nodes[63], null, null, false, null, 17, 17);
		nodes[54].Setting(null, null, nodes[53], nodes[44], false, null, 18, 17);
		nodes[55].Setting(nodes[56], nodes[65], null, null, false, null, 2, 19);
		nodes[56].Setting(nodes[57], null, nodes[55], nodes[46], false, null, 3, 19);
		nodes[57].Setting(null, null, nodes[56], nodes[47], false, null, 5, 19);
		nodes[58].Setting(nodes[59], null, null, nodes[48], false, null, 7, 19);
		nodes[59].Setting(null, nodes[66], nodes[58], null, false, null, 9, 19);
		nodes[60].Setting(nodes[61], nodes[67], null, null, false, null, 2, 19);
		nodes[61].Setting(null, null, nodes[60], nodes[51], false, null, 13, 19);
		nodes[62].Setting(nodes[63], null, null, nodes[52], false, null, 15, 19);
		nodes[63].Setting(nodes[64], null, nodes[62], nodes[53], false, null, 17, 19);
		nodes[64].Setting(null, nodes[68], nodes[63], null, false, null, 18, 19);
		nodes[65].Setting(nodes[66], null, null, nodes[55], false, null, 2, 21);
		nodes[66].Setting(nodes[67], null, nodes[65], nodes[59], false, null, 9, 21);
		nodes[67].Setting(nodes[68], null, nodes[66], nodes[60], false, null, 11, 21);
		nodes[68].Setting(null, null, nodes[67], nodes[64], false, null, 18, 21);
		nodes[69].Setting(nodes[36], null, nodes[35], null, false, null, 13, 10);
		
		nodes[70].Setting(null, null, null, null, false, null, 0, 0); // 좌측 상단
		nodes[71].Setting(null, null, nodes[35], null, false, null, 20, 0); // 우측 상단
		nodes[72].Setting(null, null, nodes[35], null, false, null, 0, 23); // 좌측 하단
		nodes[73].Setting(null, null, nodes[35], null, false, null, 20, 23); // 우측 하단
		
		// nodeX와 nodeY에 모든 노드의 x좌표와 y좌표를 담음
		for(int i = 0; i < nodes.length; i++) {
			nodeX[i] = nodes[i].x;
			nodeY[i] = nodes[i].y;
		}
		for(int i = 0; i < nodes.length; i++) {
			board[nodeX[i]][nodeY[i]] = nodes[i];
		}
		
		// 코인 생성
		coins = new ArrayList<Coin>(161);
		// 코인 위치 조정
		
		// bigCoins 생성
		bigCoins = new ArrayList<BigCoin>(4);
		// Big Coins 위치 조정? 아니면 생성과 동시에 위치 설정
		
		// 팩맨 생성
		pacman = new Pacman(this);
		
		// 유령 생성
		ghosts.add(new Ghost(nodes[31], pacman, this, Ghost.GhostType.Red));
		ghosts.add(new Ghost(nodes[31], pacman, this, Ghost.GhostType.Blue));
		ghosts.add(new Ghost(nodes[31], pacman, this, Ghost.GhostType.Orange));
		ghosts.add(new Ghost(nodes[31], pacman, this, Ghost.GhostType.Pink));
	}
	
}
