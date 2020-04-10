
public abstract class ComputerPlayer {

	ConnectFour game;

	public ComputerPlayer(ConnectFour game) {
		super();
		this.game = game;
	}
	
	public abstract int nextMove();
}
