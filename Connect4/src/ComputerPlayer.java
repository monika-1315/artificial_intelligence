
public abstract class ComputerPlayer {

	Board game;
	protected int playerNum;
	protected int movesCounter=0;
	protected long thinkingTimeSum=0;
	
	public ComputerPlayer(Board game, int playerNum) {
		super();
		this.game = game;
		this.playerNum=playerNum;
	}
	
	public abstract int nextMove();
	
	public String getResearch() {
		double avgTime = thinkingTimeSum/movesCounter;
		if (playerNum==1)
			return (movesCounter+1) + "\n" + avgTime;
		return movesCounter + "\n" + avgTime;
	}
}
