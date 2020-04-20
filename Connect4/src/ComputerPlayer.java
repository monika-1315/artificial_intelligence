
public abstract class ComputerPlayer {

	Board game;

	public ComputerPlayer(Board game) {
		super();
		this.game = game;
	}
	
	public abstract int nextMove();
}
