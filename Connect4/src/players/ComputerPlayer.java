package players;
import game.Board;

public abstract class ComputerPlayer {

	Board game;
	protected int playerNum;
	protected int movesCounter=0;
	protected long thinkingTimeSum=0;
	private String algorithmInfo;
	
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
		return getAlgorithmInfo() + "," ;
	}

	public String getAlgorithmInfo() {
		return algorithmInfo;
	}

	public void setAlgorithmInfo(String algorithmInfo) {
		this.algorithmInfo = algorithmInfo;
	}
}
