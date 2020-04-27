
public class RandomPlayer extends ComputerPlayer {

	public RandomPlayer(Board game, int playerNum) {
		super(game, playerNum);
	}

	@Override
	public int nextMove() {
		long t0=System.currentTimeMillis();
		movesCounter++;
		
		int move;
		do {
			move = (int)(Math.random() * game.getWidth());
//			System.out.println(move);
		} while (game.isColumnFull(move));
		
		long t1=System.currentTimeMillis();
		this.thinkingTimeSum+=(t1-t0);
		return move;
	}

}
