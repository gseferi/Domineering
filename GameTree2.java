// Game trees for abstract games two-person games with outcomes in the
// type of integers, parametrized by a type of moves.



public class GameTree2<Move> {
  private final Board2<Move> board;
  private final LList<Entry<Move,GameTree2<Move>>> children;
  final int optimalOutcome; 
 

  public GameTree2(Board2<Move> board, 
                  LList<Entry<Move,GameTree2<Move>>> children, 
                  int optimalOutcome) {

    assert(board != null && children != null);
    this.board = board;
    this.children = children;
    this.optimalOutcome = optimalOutcome;
  }

  public boolean isLeaf() {
    return(children.isEmpty());
  }

  // Getter methods:
  public Board2<Move> board () {
    return board;
  }

  public LList<Entry<Move,GameTree2<Move>>> children() {
    return children;
  }

  public int optimalOutcome() {
    return optimalOutcome;
  }

  // The following two methods are for game tree statistics only.
  // They are not used for playing.

  // Number of tree nodes:
 /* public int size() {
    int size = 1;
    for (Map.Entry<Move,GameTree2<Move>> child : children.entrySet()) {
    size += child.getValue().size();
    }
    return size;
  }*/

  // We take the height of a leaf to be zero (rather than -1):
  /*public int height() { 
    int height = -1;
    for (Map.Entry<Move,GameTree2<Move>> e : children.entrySet()) {
      height = Math.max(height,e.getValue().height());
    }
    return 1+height;
  }*/

  // Plays first using this tree:
  public void firstPlayer(MoveChannel<Move> c) {
    c.comment(board + "\nThe optimal outcome is " + optimalOutcome);

    if (isLeaf()) {
      assert(optimalOutcome == board.value());
      c.end(board.value());
    } 
    else {
      Entry<Move,GameTree2<Move>> optimalEntry = null;
      LList<Entry<Move, GameTree2<Move>>> list = children; 
      while (!list.isEmpty()) {
    	 Entry<Move, GameTree2<Move>> child= list.head();
        if (optimalOutcome == child.getValue().optimalOutcome) {
          optimalEntry = child;
          break;
        }
        list = list.tail();
      }
      assert(optimalEntry != null);
      c.giveMove(optimalEntry.getKey());
      optimalEntry.getValue().secondPlayer(c);
    }
  }
  public boolean containsKey(Move m) {
	  LList<Entry<Move, GameTree2<Move>>> list = children;
	  while (!list.isEmpty()) {
	    	 Entry<Move, GameTree2<Move>> child= list.head();
	        if (child.getKey().equals(m)) {
	          return true;
	        }
	        list = list.tail();
	      }
	  return false;
  }
  
  public GameTree2<Move> get(Move m) {
	  LList<Entry<Move, GameTree2<Move>>> list = children;
	  while (!list.isEmpty()) {
	    	 Entry<Move, GameTree2<Move>> child= list.head();
	        if (child.getKey().equals(m)) {
	          return child.getValue();
	        }
	        list = list.tail();
	      }
	  return null;
  }
  
  // Plays second using this tree:
  public void secondPlayer(MoveChannel<Move> c) {
    c.comment(board + "\nThe optimal outcome is " + optimalOutcome);

    if (isLeaf()) {
      assert(optimalOutcome == board.value());
      c.end(board.value());
    } 
    else {
      Move m = c.getMove();
      assert(containsKey(m));
      if(containsKey(m)){
      get(m).firstPlayer(c);
      }
      else{
    	  if(board.getSize().length<=5 && board.getSize()[0].length<=5){
    		  board.play(m).tree(-1, 1).firstPlayer(c);
    	  }
    	  else{
    	  board.play(m).hTree(4, -1, 1).firstPlayer(c);
      }
    }
  }
  }
}
