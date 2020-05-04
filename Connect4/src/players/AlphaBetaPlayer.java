package players;

import evaluators.Evaluator;
import evaluators.SimpleEvaluator;
import game.Board;

public class AlphaBetaPlayer extends ComputerPlayer {

	private int wonPoints = 100;
	private int maxDepth;

	public AlphaBetaPlayer(Board game, int playerNumber, int maxDepth, int wonPoints, Evaluator eval) {
		super(game, playerNumber, eval);
		this.maxDepth = maxDepth;
		this.wonPoints = wonPoints;
		this.setAlgorithmInfo("AlphaBeta," + maxDepth);
	}

	public AlphaBetaPlayer(Board game, int playerNumber, int maxDepth, Evaluator eval) {
		this(game, playerNumber, maxDepth, maxDepth * 2, eval);
	}

	public AlphaBetaPlayer(Board game) {
		this(game, 1, 5, 2, new SimpleEvaluator());
	}

	@Override
	public int nextMove() {
		long t0 = System.currentTimeMillis();
		movesCounter++;
		int bestVal;
		int bestMove = -1;
		if (playerNum == 1) {
			bestVal = Integer.MIN_VALUE;
		} else {
			bestVal = Integer.MAX_VALUE;
		}
		int alpha = Integer.MIN_VALUE;
		int beta = Integer.MAX_VALUE;
		for (int i = 0; i < game.getWidth(); i++) {
			if (!game.isColumnFull(i)) {
				Board newBoard = (Board) game.clone();
				newBoard.drop(Board.PLAYERS[playerNum], i);

				int moveVal;
				if (playerNum == 1) {
					moveVal = minmax(newBoard, 0, false, alpha, beta);
					if (moveVal > bestVal) {
						bestMove = i;
						bestVal = moveVal;
					}
					alpha = Math.max(alpha, bestVal);
					if (beta <= alpha)
						break;
					System.out.println(moveVal + " " + bestVal);
				} else {
					moveVal = minmax(newBoard, 0, true, alpha, beta);
					if (moveVal < bestVal) {
						bestMove = i;
						bestVal = moveVal;
					}
					beta = Math.min(beta, bestVal);
					if (beta <= alpha)
						break;
					System.out.println(moveVal + " " + bestVal);
				}
			}
		}
//		if(bestVal==0 && bestMove==0) {
//			do {
//				bestMove = (int)(Math.random() * game.getWidth());
//			} while (game.isColumnFull(bestMove));
//		}
		long t1 = System.currentTimeMillis();
		this.thinkingTimeSum += (t1 - t0);
		return bestMove;
	}

	private int evaluate(Board board, int depth) {

		return eval.evaluate(board, depth);
	}

	private int minmax(Board board, int depth, Boolean isMax, int alpha, int beta) {
		if (depth >= maxDepth || board.isGameOver())
			return evaluate(board, depth);

		if (isMax) {
			int best = Integer.MIN_VALUE;

			for (int i = 0; i < board.getWidth(); i++) {

				if (!board.isColumnFull(i)) {
					Board newBoard = board.clone();
					newBoard.drop(Board.PLAYERS[1], i);

					best = Math.max(best, minmax(newBoard, depth + 1, !isMax, alpha, beta));
					alpha = Math.max(alpha, best);
					if (beta <= alpha)
						break;
				}
			}
			return best;
		} else {// minimizer's move
			int best = Integer.MAX_VALUE;
			for (int i = 0; i < board.getWidth(); i++) {

				if (!board.isColumnFull(i)) {
					Board newBoard = board.clone();
					newBoard.drop(Board.PLAYERS[0], i);

					best = Math.min(best, minmax(newBoard, depth + 1, !isMax, alpha, beta));
					beta = Math.min(beta, best);
					if (beta <= alpha)
						break;
				}
			}
			return best;
		}
	}// minmax
}
