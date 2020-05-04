package evaluators;
import game.Board;

public abstract class Evaluator {
	
	protected int wonPoints;

	public Evaluator(int wonPoints) {
		super();
		this.wonPoints = wonPoints;
	}

	public abstract int evaluate(Board board, int depth);
	
	public abstract String toString();
}
