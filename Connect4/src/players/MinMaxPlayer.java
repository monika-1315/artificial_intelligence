package players;
import evaluators.Evaluator;
import evaluators.SimpleEvaluator;
import game.Board;

public class MinMaxPlayer extends ComputerPlayer {

	
	private int maxDepth;

	public MinMaxPlayer(Board game, int playerNumber, int maxDepth,  Evaluator eval) {
		super(game,playerNumber, eval);
		this.maxDepth = maxDepth;
		this.setAlgorithmInfo("MinMax,"+maxDepth);
	}

	
	public MinMaxPlayer(Board game) {
		this(game,1,5, new SimpleEvaluator());
	}

	@Override
	public int nextMove() {
		long t0=System.currentTimeMillis();
		movesCounter++;
		int bestVal;
		int bestMove = -1;
		if (playerNum == 1) {
			bestVal = Integer.MIN_VALUE;
		} else {
			bestVal = Integer.MAX_VALUE;
		}
		for (int i = 0; i < game.getWidth(); i++) {
			if (!game.isColumnFull(i)) {
				Board newBoard = (Board) game.clone();
				newBoard.drop(Board.PLAYERS[playerNum], i);

				int moveVal;
				if (playerNum == 1) {
					moveVal = minmax(newBoard, 0, false);
					if (moveVal > bestVal) {
						bestMove = i;
						bestVal = moveVal;
					}
					System.out.println(moveVal + " " + bestVal);
				} else {
					moveVal = minmax(newBoard, 0, true);
					if (moveVal < bestVal) {
						bestMove = i;
						bestVal = moveVal;
					}
					System.out.println(moveVal + " " + bestVal);
				}
			}
		}
//		if(bestVal==0 && bestMove==0) {
//			do {
//				bestMove = (int)(Math.random() * game.getWidth());
//			} while (game.isColumnFull(bestMove));
//		}
		long t1=System.currentTimeMillis();
		this.thinkingTimeSum+=(t1-t0);
		return bestMove;
	}

	private int evaluate(Board board, int depth) {
		
		return eval.evaluate(board, depth);
	}

	private int minmax(Board board, int depth, Boolean isMax) {
		if (depth >= maxDepth || board.isGameOver())
			return evaluate(board, depth);

		if (isMax) {
			int best = Integer.MIN_VALUE;

			for (int i = 0; i < board.getWidth(); i++) {

				if (!board.isColumnFull(i)) {
					Board newBoard = board.clone();
//					newBoard.drop(Board.PLAYERS[playerNum], i);
					newBoard.drop(Board.PLAYERS[1], i);

					best = Math.max(best, minmax(newBoard, depth + 1, !isMax));
				}
			}
			return best;
		} else {// minimizer's move
			int best = Integer.MAX_VALUE;
			for (int i = 0; i < board.getWidth(); i++) {

				if (!board.isColumnFull(i)) {
					Board newBoard = board.clone();
//					newBoard.drop(Board.PLAYERS[1 - playerNum], i);
					newBoard.drop(Board.PLAYERS[0], i);
					best = Math.min(best, minmax(newBoard, depth + 1, !isMax));
				}
			}
			return best;
		}
	}// minmax
}
