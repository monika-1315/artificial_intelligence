package evaluators;

import game.Board;

public class ThreeEvaluator extends Evaluator {

	public ThreeEvaluator(int wonPoints) {
		super(wonPoints);
	}

	@Override
	public int evaluate(Board board, int depth) {
		int value=0;
		if (board.isWinningPlay()) {
			value= wonPoints - depth;
		} else if (hasThree(board)) {
			value=(int)(0.7*wonPoints) - depth;
		}
		if (board.getLastPlayerSymbol() == Board.PLAYERS[1])
			return value;
		else
			return -value;
	}

	private boolean hasThree(Board board) {
		char sym=board.getLastPlayerSymbol();
		String streak = "" + sym + sym + sym + ".";
		if (Board.contains(board.horizontal(), streak) || Board.contains(board.vertical(), streak) || Board.contains(board.slashDiagonal(), streak)
				|| Board.contains(board.backslashDiagonal(), streak)) {
//			System.out.println("It works!\n");
			return false;
		}
		return false;
	}

	@Override
	public String toString() {
		return "ThreeEval";
	}

}
