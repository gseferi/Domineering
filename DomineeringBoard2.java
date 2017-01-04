import java.util.HashSet;
import java.util.Set;

public class DomineeringBoard2 extends Board2<DomineeringMove> {
	public static final Player H = Player.MAXIMIZER;
	public static final Player V = Player.MINIMIZER;

	private final Set<DomineeringMove> vMoves;
	private final Set<DomineeringMove> hMoves;
	private final int m;
	private final int n;
	private final boolean[][] board;
	private final int count;

	public DomineeringBoard2() {
		this.m = 4;
		this.n = 4;
		this.count = 0;
		board = new boolean[4][4];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				board[i][j] = false;
			}
		}
		hMoves = new HashSet<DomineeringMove>();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 4; j++) {
				hMoves.add(new DomineeringMove(i, j, true));
			}
		}
		vMoves = new HashSet<DomineeringMove>();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 3; j++) {
				vMoves.add(new DomineeringMove(i, j, false));

			}
		}

	}

	public DomineeringBoard2(int m, int n) {
		assert (m > 1 && n > 1);
		this.m = m;
		this.n = n;
		this.count = 0;
		board = new boolean[m][n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				board[i][j] = false;
			}
		}
		hMoves = new HashSet<DomineeringMove>();
		for (int i = 0; i < m - 1; i++) {
			for (int j = 0; j < n; j++) {
				hMoves.add(new DomineeringMove(i, j, true));

			}
		}
		vMoves = new HashSet<DomineeringMove>();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n - 1; j++) {
				vMoves.add(new DomineeringMove(i, j, false));

			}
		}

	}

	private DomineeringBoard2(Set<DomineeringMove> hMoves, Set<DomineeringMove> vMoves, boolean[][] board, int count) {
		this.hMoves = hMoves;
		this.vMoves = vMoves;
		this.board = board;
		this.m = board.length;
		this.n = board[0].length;
		this.count = count;
	}

	@Override
	Player nextPlayer() {
		if (count % 2 == 0) {
			return H;
		} else {
			return V;
		}

	}

	@Override
	Set<DomineeringMove> availableMoves() {
		if (nextPlayer() == H) {

			return hMoves;
		} else {
			return vMoves;
		}
	}

	@Override
	int value() {
		if (DomineeringMove.wins(vMoves) && nextPlayer() == V) {
			return 1;
		} else if (DomineeringMove.wins(hMoves) && nextPlayer() == H) {
			return -1;
		} else {
			return 0;
		}
	}

	@Override
	Board2<DomineeringMove> play(DomineeringMove move) {
		assert (hMoves.contains(move) || vMoves.contains(move));

		if (nextPlayer() == H)
			return new DomineeringBoard2(remove(hMoves, move), remove(vMoves, move), add(board, move), count + 1);
		else
			return new DomineeringBoard2(remove(hMoves, move), remove(vMoves, move), add(board, move), count + 1);
	}

	private boolean[][] add(boolean[][] board2, DomineeringMove move) {
		boolean[][] b1 = new boolean[m][n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				b1[i][j] = board[i][j];
			}
		}
		b1[move.getX()][move.getY()] = true;
		if (move.isHorizontal()) {
			b1[move.getX() + 1][move.getY()] = true;
		} else {
			b1[move.getX()][move.getY() + 1] = true;
		}
		return b1;
	}

	private Set<DomineeringMove> remove(Set<DomineeringMove> hMoves2, DomineeringMove move) {
		Set<DomineeringMove> set = new HashSet<DomineeringMove>();
		set.addAll(hMoves2);
		for (DomineeringMove move2 : hMoves2) {
			if (move2.getX() == move.getX() && move2.getY() == move.getY()) {
				set.remove(move2);
			}
			if (move.isHorizontal()) {
				if (!move2.isHorizontal() && (move2.getY() + 1 == move.getY())) {
					if (move2.getX() == move.getX() || move2.getX() == move.getX() + 1) {
						set.remove(move2);
					}
				} else if ((move2.getX() == move.getX() + 1
						|| (move2.getX() + 1 == move.getX() && move2.isHorizontal())) && move2.getY() == move.getY()) {
					set.remove(move2);
				}

			} else {
				if (move2.isHorizontal() && move2.getX() + 1 == move.getX()) {
					if (move2.getY() == move.getY() || move2.getY() == move.getY() + 1) {
						set.remove(move2);
					}
				} else if ((move2.getY() == move.getY() + 1
						|| (move2.getY() + 1 == move.getY() && !move2.isHorizontal())) && move2.getX() == move.getX()) {
					set.remove(move2);
				}
			}
		}
		return set;
	}

	public String toString() {
		String s = "";
		for (int j = 0; j < n; j++) {
			for (int i = 0; i < m; i++) {
				s += (board[i][j]) ? " X  | " : "" + i + "," + j + " | ";
			}
			s += "\n";
		}
		return s;
	}

	//@Override
	int heuristics() {
		int verticalCount = 0;
		int horizontalCount = 0;
		for(int j=0; j < n; j++){
			for(int i=0; i < m; i++){
				if(i <(m-1)) {
					if(!board[i][j] && !board[i+1][j]){
						verticalCount++;
					}
				if(j<(n-1)){
					if(!board[i][j] && !board[i][j+1]){
						horizontalCount++;
					}
				}
				}
			}
		}
		if(nextPlayer() == Player.MAXIMIZER) {
			if(horizontalCount > verticalCount){
				return 1;
			}
			else {
				return -1;
			}	
		}
		else {
			if(horizontalCount < verticalCount){
				return 1;
			}
			else {
				return -1;
			}	
		}
	}
	
	public boolean[][] getSize(){
		return board;
	}
	
}

