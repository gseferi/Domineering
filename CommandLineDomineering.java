import java.util.Scanner;

public class CommandLineDomineering {

	private static GameTree<DomineeringMove> tree;

	private static class CommandLineD implements MoveChannel<DomineeringMove> {
		public DomineeringMove getMove() {
			System.out.println("Please enter x");
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);
			int x = scanner.nextInt();
			System.out.println("Please enter y");
			int y = scanner.nextInt();
			scanner.nextLine();
			DomineeringMove move = new DomineeringMove(x, y,
					DomineeringBoard.H == tree.board().nextPlayer());
			while (!tree.children().containsKey(move)) {
				System.out.println("Invalid Move, Please enter another move");
				System.out.println("Please enter x");
				x = scanner.nextInt();
				System.out.println("Please enter y");
				y = scanner.nextInt();
				//scanner.nextLine();
				move = new DomineeringMove(x, y,
						DomineeringBoard.H == tree.board().nextPlayer());
			}
			tree = tree.children().get(move);
			return move;

		}

		public void giveMove(DomineeringMove move) {
			System.out.println("I play " + move);
			tree = tree.children().get(move);
		}

		public void comment(String msg) {
			System.out.println(msg);
		}

		public void end(int value) {
			System.out.println("Game over. The result is " + value);
		}
	}

	public static void main(String[] args) {
		DomineeringBoard board = new DomineeringBoard();
		tree =  board.tree();
		tree.secondPlayer(new CommandLineD());
	}
}
