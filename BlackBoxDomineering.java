import java.util.Scanner;

public class BlackBoxDomineering {

	private static GameTree<DomineeringMove> tree;

	private static class CommandLineD implements MoveChannel<DomineeringMove> {
		public DomineeringMove getMove() {
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);
			String s = scanner.nextLine();
			String[] sa = s.split(",");
			int x=Integer.parseInt(sa[0]);
			int y=Integer.parseInt(sa[1]);
			DomineeringMove move = new DomineeringMove(x, y,
					DomineeringBoard.H == tree.board().nextPlayer());
			if (!tree.children().containsKey(move)) {
				System.exit(1);
			}
			tree = tree.children().get(move);
			return move;

		}

		public void giveMove(DomineeringMove move) {
			System.out.println(move.getX()+"," + move.getY());
			System.out.flush();
			tree = tree.children().get(move);
		}

		public void comment(String msg) {
			System.err.println(msg);
		}

		public void end(int value) {
			System.exit(0);
		}
	}

	public static void main(String[] args) {
		assert(args.length==4);
		
		int width=Integer.parseInt(args[2]);
		int height=Integer.parseInt(args[3]);
		DomineeringBoard board = new DomineeringBoard(width,height);
		tree = board.tree();
		if(args[0].equals("first")) {
			tree.firstPlayer(new CommandLineD());
		}
		else{
		tree.secondPlayer(new CommandLineD());
		}
	}
}