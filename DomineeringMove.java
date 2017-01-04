import java.util.Set;

public class DomineeringMove{
	private final int i;
	private final int j;
	private final boolean b;

	public DomineeringMove(int i, int j, boolean b) {
		this.i = i;
		this.j = j;
		this.b = b;
	}

	public static boolean wins(Set<DomineeringMove> moves) {
		return moves.isEmpty();
	}

	public int getX() {
		return i;
	}

	public int getY() {
		return j;
	}

	public boolean isHorizontal() {
		return b;
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof DomineeringMove){
			DomineeringMove dom = (DomineeringMove)obj;
			return i==dom.getX() && j==dom.getY() && b==dom.isHorizontal();
		}
		return false;
	}
	@Override
	public int hashCode() {
		int x=0;
		if(b){
			x=103;
		}
		return (10 * (i+x)+ j );

	}

	public String toString() {
		return "" + i + "," + j + "," + b;
	}

}
