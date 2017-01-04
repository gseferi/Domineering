// We play tic-tac-toe in the comment line. This works by creating a
// command-line based move channel.
//
//
// Compile with
//               $ javac -Xlint CommandLineTicTacToe.java
// Run with
//               $ java -ea CommandLineTicTacToe
//
// Change main to let the computer or the user start.
//
// This class is simple minded and for testing purposes only, and will
// trigger exceptions or cause assertions to fail if the user provides
// incorrect input.

public class CommandLineTicTacToe {

  private static class CommandLineTTT implements MoveChannel<TTTMove> {
    public TTTMove getMove() {
      return(TTTMove.valueOf(System.console().readLine("Enter your move: ")));
    }

    public void giveMove(TTTMove move) {
      System.out.println("I play " + move);
    }

    public void comment(String msg) {
      System.out.println(msg);
    }

    public void end(int value) {
      System.out.println("Game over. The result is " + value);
    }
  }

  public static void main(String [] args) {
    TTTBoard board = new TTTBoard();
     // board.tree().firstPlayer(new CommandLineTTT());
    board.tree().secondPlayer(new CommandLineTTT());
  }
}
