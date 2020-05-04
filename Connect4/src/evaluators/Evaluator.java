package evaluators;
import game.Board;

public interface Evaluator {

	public int evaluate(Board board, int depth, int maxPoints);
}
