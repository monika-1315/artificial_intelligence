
public class RandomPlayer extends ComputerPlayer {

	public RandomPlayer(ConnectFour game) {
		super(game);
	}

	@Override
	public int nextMove() {
		int move;
		do {
			move = (int)(Math.random() * game.getWidth());
//			System.out.println(move);
		} while (game.getGrid()[0][move] != '.');
		return move;
	}

}
