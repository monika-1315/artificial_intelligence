
public class MinMaxPlayer extends ComputerPlayer {

	int playerNum;
	private int wonPoints = 100;
	private int maxDepth;

	public MinMaxPlayer(Board game, int playerNumber, int maxDepth, int wonPoints) {
		super(game);
		this.playerNum = playerNumber;
		this.maxDepth = maxDepth;
		this.wonPoints = wonPoints;
	}

	public MinMaxPlayer(Board game, int playerNumber, int maxDepth) {
		super(game);
		this.playerNum = playerNumber;
		this.maxDepth = maxDepth;
		this.wonPoints = maxDepth * 2;
	}

	public MinMaxPlayer(Board game) {
		super(game);
		this.playerNum = 1;
		this.maxDepth = 5;
		this.wonPoints = maxDepth * 2;
	}

	@Override
	public int nextMove() {
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
//		if (bestVal == 0 && bestMove == 0) {
//			int move;
//			do {
//				move = (int) (Math.random() * game.getWidth());
////					System.out.println(move);
//			} while (game.isColumnFull(move));
//			return move;
//		}

		return bestMove;
	}

	private int evaluate(Board board, int depth) {
		if (board.isWinningPlay()) {
			if (board.getLastPlayerSymbol() == Board.PLAYERS[1])
//			if (board.getLastPlayerSymbol() == Board.PLAYERS[playerNum])	
				return wonPoints - depth;
			else
				return -wonPoints + depth;
		}
		return 0;
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
