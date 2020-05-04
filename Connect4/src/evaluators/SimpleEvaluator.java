package evaluators;
import game.Board;

public class SimpleEvaluator extends Evaluator {


	public SimpleEvaluator() {
		super(20);
	}
	public SimpleEvaluator(int wonPoints) {
		super(wonPoints);
	}
	
	@Override
	public  int evaluate(Board board, int depth) {
		if (board.isWinningPlay()) {
			if (board.getLastPlayerSymbol() == Board.PLAYERS[1])
				return wonPoints - depth;
			else 
				return -wonPoints + depth;
		}
		return 0;
	}

	@Override
	public String toString() {
		
		return "SimpleEval";
	}

	

}
