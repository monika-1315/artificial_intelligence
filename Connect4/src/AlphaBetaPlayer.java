
public class AlphaBetaPlayer extends ComputerPlayer {

	
	private int wonPoints = 100;
	private int maxDepth;

	public AlphaBetaPlayer(Board game, int playerNumber, int maxDepth, int wonPoints) {
		super(game,playerNumber);
		this.maxDepth = maxDepth;
		this.wonPoints = wonPoints;
	}

	public AlphaBetaPlayer(Board game, int playerNumber, int maxDepth) {
		super(game,playerNumber);
		this.maxDepth = maxDepth;
		this.wonPoints = maxDepth * 2;
	}

	public AlphaBetaPlayer(Board game) {
		super(game, 1);
		this.maxDepth = 5;
		this.wonPoints = maxDepth * 2;
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
					moveVal = minmax(newBoard, 0, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
					if (moveVal > bestVal) {
						bestMove = i;
						bestVal = moveVal;
					}
					System.out.println(moveVal+" "+bestVal);
				} else {
					moveVal = minmax(newBoard, 0, true, Integer.MIN_VALUE, Integer.MAX_VALUE);
					if (moveVal < bestVal) {
						bestMove = i;
						bestVal = moveVal;
					}
					System.out.println(moveVal+" "+bestVal);
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
		if (board.isWinningPlay()) {
			if (board.getLastPlayerSymbol() == Board.PLAYERS[1])
				return wonPoints - depth;
			else 
				return -wonPoints + depth;
		}
		return 0;
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
					if(beta<=alpha)
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
					beta = Math.min(beta,  best);
					if (beta<=alpha)
						break;
				}
			}
			return best;
		}
	}// minmax
}
