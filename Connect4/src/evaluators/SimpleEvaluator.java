package evaluators;
import game.Board;

public class SimpleEvaluator implements Evaluator {

	public SimpleEvaluator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public  int evaluate(Board board, int depth, int wonPoints) {
		if (board.isWinningPlay()) {
			if (board.getLastPlayerSymbol() == Board.PLAYERS[1])
				return wonPoints - depth;
			else 
				return -wonPoints + depth;
		}
		return 0;
	}

}
