
public class RandomPlayer extends ComputerPlayer {

	public RandomPlayer(Board game) {
		super(game);
	}

	@Override
	public int nextMove() {
		int move;
		do {
			move = (int)(Math.random() * game.getWidth());
//			System.out.println(move);
		} while (game.isColumnFull(move));
		return move;
	}

}
