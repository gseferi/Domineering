import java.util.Scanner;

public class BlackBoxDomineering2 {

	private static GameTree2<DomineeringMove> tree;

	private static class CommandLineD implements MoveChannel<DomineeringMove> {
		public DomineeringMove getMove() {
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);
			String s = scanner.nextLine();
			String[] sa = s.split(",");
			int x=Integer.parseInt(sa[0]);
			int y=Integer.parseInt(sa[1]);
			DomineeringMove move = new DomineeringMove(x, y,
					DomineeringBoard2.H == tree.board().nextPlayer());
			if (!tree.containsKey(move)) {
				if(tree.board().availableMoves().contains(move)){
					if(tree.board().getSize().length <=5 && tree.board().getSize()[0].length<=5) {
						tree = tree.board().play(move).tree(-1, 1);
					}
					else{
						tree = tree.board().play(move).hTree(4, -1, 1);
					}
					return move;
				}
				else{
					System.exit(1);
				}
				
			}
			tree = tree.get(move);
			return move;

		}

		public void giveMove(DomineeringMove move) {
			System.out.println(move.getX()+"," + move.getY());
			System.out.flush();
			tree = tree.get(move);
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
		
		DomineeringBoard2 board = new DomineeringBoard2(width,height);
		if(width <= 5 && height <= 5) {
			tree = board.tree(-1, 1);
		}
		else{
			tree = board.hTree(4, -1, 1);
		}

		if(args[0].equals("first")) {
			tree.firstPlayer(new CommandLineD());
		}
		else{
		tree.secondPlayer(new CommandLineD());
		}
	}
}