// Tic-tac-toe abstract board methods.
// Remember we require an immutable implementation.
// So we can change things only after cloning.

import java.util.*;

public class TTTBoard extends Board<TTTMove>{
  // Rename the players for this particular game:
  private static final Player X = Player.MAXIMIZER;
  private static final Player O = Player.MINIMIZER;

  // We use the following class fields to represent the board:
  private final EnumSet<TTTMove> xMoves;
  private final EnumSet<TTTMove> oMoves;
  // NB. Finality is not enough to ensure immutability! (Why?)

  // A public method to construct the initial board:
  public TTTBoard() {
    xMoves = TTTMove.noMoves();
    oMoves = TTTMove.noMoves();
  }

  // All other constructors should be private (why?):
  private TTTBoard(EnumSet<TTTMove> xMoves, EnumSet<TTTMove> oMoves) 
  {
    assert(disjoint(xMoves,oMoves));
    this.xMoves  = xMoves;
    this.oMoves  = oMoves;
  }


  // Now the abstract class methods we need to implement to define
  // tic-tac-toe.


  // The usual convention is that X is the first player:
  public Player nextPlayer() {
    return ((xMoves.size() + oMoves.size()) % 2 == 0 ? X : O);
  }

  public EnumSet<TTTMove> availableMoves(){
    return ( value() == 0
               ? complement(union(xMoves,oMoves))
               : TTTMove.noMoves() );
  } 

  public int value(){
    return ( TTTMove.wins(xMoves) 
                ? 1
                : TTTMove.wins(oMoves)
                     ? -1
                     :  0 );
  } 

  // We create a new board after playing a move:
  public TTTBoard play(TTTMove move){
    assert(!xMoves.contains(move) && !oMoves.contains(move));

    if (nextPlayer() == X)
      return new TTTBoard(add(xMoves,move), oMoves);
    else
      return new TTTBoard(xMoves, add(oMoves,move));
  }

  // A simple conversion to string for testing:
  public String toString() {
    return(pm(TTTMove.A0) + " | " +  pm(TTTMove.A1) + " | " +  pm(TTTMove.A2) + "\n"
         + "---+----+---\n"
         + pm(TTTMove.B0) + " | " +  pm(TTTMove.B1) + " | " +  pm(TTTMove.B2) + "\n"
         + "---+----+---\n"
         + pm(TTTMove.C0) + " | " +  pm(TTTMove.C1) + " | " +  pm(TTTMove.C2) + "\n");
  } 

  private String pm(TTTMove m) {
    return(xMoves.contains(m) ? "X " : oMoves.contains(m) ? "O " : m.toString());
  }

  // The following short private methods are for readability. They
  // ensure immutability.

  // We promise we won't change the set a (so we clone it):
  static private EnumSet<TTTMove> intersection(EnumSet<TTTMove> a, EnumSet<TTTMove> b) {
    EnumSet<TTTMove> c = EnumSet.copyOf(a); // a.clone();
    c.retainAll(b);
    return c;
  }

  static private boolean disjoint(EnumSet<TTTMove> a, EnumSet<TTTMove> b) {
    return(intersection(a,b).isEmpty());
  }

  static private EnumSet<TTTMove> union(EnumSet<TTTMove> a, EnumSet<TTTMove> b) {
    EnumSet<TTTMove> c = EnumSet.copyOf(a); // a.clone();
    c.addAll(b);
    return c;
  }  

  static private EnumSet<TTTMove> add(EnumSet<TTTMove> a, TTTMove b) {
    EnumSet<TTTMove> c = EnumSet.copyOf(a); // .clone();
    c.add(b);
    return c;
  }  

  static private EnumSet<TTTMove> complement(EnumSet<TTTMove> a) {
    return(EnumSet.complementOf(a));
  }  
}
