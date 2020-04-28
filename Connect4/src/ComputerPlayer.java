
public abstract class ComputerPlayer {

	Board game;
	protected int playerNum;
	protected int movesCounter=0;
	protected long thinkingTimeSum=0;
	protected String algorithmInfo;
	
	public ComputerPlayer(Board game, int playerNum) {
		super();
		this.game = game;
		this.playerNum=playerNum;
	}
	
	public abstract int nextMove();
	
	public String getResearch() {
		double avgTime = thinkingTimeSum/movesCounter;
		if (playerNum==0)
			return (movesCounter+1) + "," + avgTime;
		return movesCounter + "," + avgTime;
	}
	
	public String algInfo() {
		return algorithmInfo + "," ;
	}
}
