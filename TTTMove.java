import java.util.*;

/*
    A0 | A1 | A2
   ----+----+----
    B0 | B1 | B2
   ----+----+----
    C0 | C1 | C2
*/

public enum TTTMove {
  A0, A1, A2, B0, B1, B2, C0, C1, C2;

  // Some helper data and methods:

  static private final TTTMove first = A0;
  static private final TTTMove last  = C2;
  static private final EnumSet<TTTMove> lineA = EnumSet.of(A0,A1,A2);
  static private final EnumSet<TTTMove> lineB = EnumSet.of(B0,B1,B2);
  static private final EnumSet<TTTMove> lineC = EnumSet.of(C0,C1,C2);
  static private final EnumSet<TTTMove> col0  = EnumSet.of(A0,B0,C0);
  static private final EnumSet<TTTMove> col1  = EnumSet.of(A1,B1,C1);
  static private final EnumSet<TTTMove> col2  = EnumSet.of(A2,B2,C2);
  static private final EnumSet<TTTMove> diagD = EnumSet.of(A0,B1,C2);
  static private final EnumSet<TTTMove> diagU = EnumSet.of(C0,B1,A2);

  public static boolean wins(EnumSet<TTTMove> moves) {
    return (  moves.containsAll(lineA) 
           || moves.containsAll(lineB) 
           || moves.containsAll(lineC) 
           || moves.containsAll(col0) 
           || moves.containsAll(col1) 
           || moves.containsAll(col2) 
           || moves.containsAll(diagD) 
           || moves.containsAll(diagU) );
  }

  static public EnumSet<TTTMove> allMoves() {
    return(EnumSet.range(first,last));
  } 

  static public EnumSet<TTTMove> noMoves() {
    return(EnumSet.noneOf(TTTMove.class));
  }  
}
