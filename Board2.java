
// An abstract two-player game with outcomes in the integers.
// We define a particular game by implementing the abstract methods.
//
// Our approach requires immutable implementations of Board.  We
// require that the only public constructor builds the initial board.
// Other constructors may be used for private purposes.

import java.util.*;
import java.lang.Integer;

public abstract class Board2<Move> {
	abstract Player nextPlayer();

	abstract Set<Move> availableMoves();

	abstract int value();

	abstract Board2<Move> play(Move move);
	
	abstract int heuristics();
	
	abstract boolean[][] getSize();

	// Constructs the game tree of the board using the minimax algorithm
	// (without alpha-beta pruning):
	public GameTree2<Move> tree(int alpha, int beta) {
		if (availableMoves().isEmpty())
			return new GameTree2<Move>(this, new Nil<Entry<Move, GameTree2<Move>>>(), value());
		else
			return (nextPlayer() == Player.MAXIMIZER ? maxTree(alpha,beta) : minTree(alpha,beta));
	}

	// Two helper methods for that, which call the above method tree:
	@SuppressWarnings("unchecked")
	public GameTree2<Move> maxTree(int alpha, int beta) {
		assert (!availableMoves().isEmpty());

		int optimalOutcome = Integer.MIN_VALUE;
		LList<Entry<Move, GameTree2<Move>>> children = new Nil<Entry<Move, GameTree2<Move>>>();
		LList<Move> l = new Nil<Move>();
		for (Move m : availableMoves()) {
			if(play(m).tree(alpha, beta).optimalOutcome()==beta){
			l = new Cons<Move>(m, l);
			break;
			}
		}
		if(l.isEmpty()){
			l = new Cons<Move>((Move)availableMoves().toArray()[0], l);
		}

		children = l.map(m -> new Entry<Move, GameTree2<Move>>(m, play(m).tree(alpha, beta)));
		optimalOutcome = LLists.boundedMaximum(children.map(e -> e.getValue().optimalOutcome()), beta);
		return new GameTree2<Move>(this, children, optimalOutcome);
	}

	@SuppressWarnings("unchecked")
	public GameTree2<Move> minTree(int alpha, int beta) {
		assert (!availableMoves().isEmpty());

		int optimalOutcome = Integer.MAX_VALUE;
		LList<Entry<Move, GameTree2<Move>>> children = new Nil<Entry<Move, GameTree2<Move>>>();
		LList<Move> l = new Nil<Move>();
		for (Move m : availableMoves()) {
			if(play(m).tree(alpha, beta).optimalOutcome()==alpha){
				l = new Cons<Move>(m, l);
				break;
			}
			

		}
		if(l.isEmpty()){
			l = new Cons<Move>((Move)availableMoves().toArray()[0], l);
		}

		children = l.map(m -> new Entry<Move, GameTree2<Move>>(m, play(m).tree(alpha, beta)));
		optimalOutcome = LLists.boundedMinimum(children.map(e -> e.getValue().optimalOutcome()), alpha);
		return new GameTree2<Move>(this, children, optimalOutcome);
	}
	
	public GameTree2<Move> hTree(int level, int alpha, int beta) {
		if(level == 0){
			return new GameTree2<Move>(this, new Nil<Entry<Move, GameTree2<Move>>>(), heuristics());
		}
		else{
			if (availableMoves().isEmpty())
				return new GameTree2<Move>(this, new Nil<Entry<Move, GameTree2<Move>>>(), value());
			else
				return (nextPlayer() == Player.MAXIMIZER ? hMaxTree(level, alpha,beta) : hMinTree(level, alpha,beta));
		}
		
	}

	private GameTree2<Move> hMinTree(int level, int alpha, int beta) {
		assert (!availableMoves().isEmpty());

		int optimalOutcome = Integer.MAX_VALUE;
		LList<Entry<Move, GameTree2<Move>>> children = new Nil<Entry<Move, GameTree2<Move>>>();
		LList<Move> l = new Nil<Move>();
		for (Move m : availableMoves()) {
			if(play(m).hTree(level -1, alpha, beta).optimalOutcome()==alpha){
				l = new Cons<Move>(m, l);
				break;
			}
			

		}
		if(l.isEmpty()){
			l = new Cons<Move>((Move)availableMoves().toArray()[0], l);
		}

		children = l.map(m -> new Entry<Move, GameTree2<Move>>(m, play(m).hTree(level -1, alpha, beta)));
		optimalOutcome = LLists.boundedMinimum(children.map(e -> e.getValue().optimalOutcome()), alpha);
		return new GameTree2<Move>(this, children, optimalOutcome);
	}

	private GameTree2<Move> hMaxTree(int level, int alpha, int beta) {
		assert (!availableMoves().isEmpty());

		int optimalOutcome = Integer.MIN_VALUE;
		LList<Entry<Move, GameTree2<Move>>> children = new Nil<Entry<Move, GameTree2<Move>>>();
		LList<Move> l = new Nil<Move>();
		for (Move m : availableMoves()) {
			if(play(m).hTree(level -1, alpha, beta).optimalOutcome()==beta){
			l = new Cons<Move>(m, l);
			break;
			}
		}
		if(l.isEmpty()){
			l = new Cons<Move>((Move)availableMoves().toArray()[0], l);
		}

		children = l.map(m -> new Entry<Move, GameTree2<Move>>(m, play(m).hTree(level -1, alpha, beta)));
		optimalOutcome = LLists.boundedMaximum(children.map(e -> e.getValue().optimalOutcome()), beta);
		return new GameTree2<Move>(this, children, optimalOutcome);
	}
}
