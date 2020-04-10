
public class MinMaxPlayer extends ComputerPlayer {

	int playerNum;
	private static final int WON_POINTS = 100;

	public MinMaxPlayer(Board game, int playerNumber) {
		super(game);
		this.playerNum = playerNumber;
	}

	@Override
	public int nextMove() {
		// TODO Auto-generated method stub
		return 0;
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

	private int minmax(Board board, int depth, Boolean isMax) throws CloneNotSupportedException {
		int score = evaluate(board);

		if (score == WON_POINTS || score == -WON_POINTS)
			return score;

		if (board.isFull())
			return 0;

		if (isMax) {
			int best = Integer.MIN_VALUE;

			for (int i = 0; i < board.getWidth(); i++) {

				if (!board.isColumnFull(i)) {
					Board newBoard = (Board) board.clone();
					newBoard.drop(Board.PLAYERS[playerNum], i);

					best = Math.max(best, minmax(newBoard, depth + 1, !isMax));
				}
			}
			return best;
		}
		else {// minimizer's move
			int best = Integer.MAX_VALUE;
			for (int i = 0; i < board.getWidth(); i++) {

				if (!board.isColumnFull(i)) {
					Board newBoard = (Board) board.clone();
					newBoard.drop(Board.PLAYERS[1 - playerNum], i);

					best = Math.min(best, minmax(newBoard, depth + 1, !isMax));
				}
			}
			return best;
		}
	}//minmax
}
