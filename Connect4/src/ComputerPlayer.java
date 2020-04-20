
public abstract class ComputerPlayer {

	volatile Board game;

	public ComputerPlayer(Board game) {
		super();
		this.game = game;
	}
	
	public abstract int nextMove();
}
