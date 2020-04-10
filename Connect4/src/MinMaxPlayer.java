
public class MinMaxPlayer extends ComputerPlayer {

	int playerNum;
	private static final int WON_POINTS = 100;
	private int maxDepth;

	public MinMaxPlayer(Board game, int playerNumber, int maxDepth) {
		super(game);
		this.playerNum = playerNumber;
		this.maxDepth=maxDepth;
	}
	
	public MinMaxPlayer(Board game) {
		super(game);
		this.playerNum = 1;
		this.maxDepth=7;
	}

	@Override
	public int nextMove()  {
		int bestVal = Integer.MIN_VALUE;
		int bestMove = -1;

		for (int i = 0; i < game.getWidth(); i++) {
			if (!game.isColumnFull(i)) {
				Board newBoard = (Board) game.clone();
				newBoard.drop(Board.PLAYERS[playerNum], i);

				int moveVal = minmax(newBoard, 0, false);
				if (moveVal > bestVal) {
					bestMove = i;
					bestVal = moveVal;
				}
			}
		}
		return bestMove;
	}

	private int evaluate(Board board) {
		if (board.isWinningPlay()) {
			if (board.getLastPlayerSymbol() == Board.PLAYERS[playerNum])
				return WON_POINTS;
			else
				return -WON_POINTS;
		}
		return 0;
	}

	private int minmax(Board board, int depth, Boolean isMax) {
		if (depth>maxDepth)
			return 0;
		int score = evaluate(board);

		if (score == WON_POINTS || score == -WON_POINTS)
			return score;

		if (board.isFull())
			return 0;

		if (isMax) {
			int best = Integer.MIN_VALUE;

			for (int i = 0; i < board.getWidth(); i++) {

				if (!board.isColumnFull(i)) {
					Board newBoard = board.clone();
					newBoard.drop(Board.PLAYERS[playerNum], i);

					best = Math.max(best, minmax(newBoard, depth + 1, !isMax));
				}
			}
			return best;
		} else {// minimizer's move
			int best = Integer.MAX_VALUE;
			for (int i = 0; i < board.getWidth(); i++) {

				if (!board.isColumnFull(i)) {
					Board newBoard = board.clone();
					newBoard.drop(Board.PLAYERS[1 - playerNum], i);

					best = Math.min(best, minmax(newBoard, depth + 1, !isMax));
				}
			}
			return best;
		}
	}// minmax
}
