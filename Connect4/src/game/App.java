package game;
import evaluators.SimpleEvaluator;
import players.AlphaBetaPlayer;
import players.ComputerPlayer;
import players.MinMaxPlayer;
import players.RandomPlayer;

public class App {

	public static void main(String[] args) {
//		runRandomTests();

		GUI gui=new GUI();
		gui.play();
	}

	private static void runRandomTests() {
		ConnectFour board = new ConnectFour();
		ComputerPlayer ai1;// =new MinMaxPlayer(board.getBoard(),0, 8);
		ComputerPlayer ai2;// =new MinMaxPlayer(board.getBoard(),1, 7);
		int depth;
		double p;
		while (true) {
			p = Math.random();
			if (p < 0.45) {
				depth = (int) (Math.random() * 6) + 3;
				ai1 = new MinMaxPlayer(board.getBoard(), 0, depth, new SimpleEvaluator());
			} else if (p < 0.9) {
				depth = (int) (Math.random() * 11) + 3;
				ai1 = new AlphaBetaPlayer(board.getBoard(), 0, depth, new SimpleEvaluator());
			} else
				ai1 = new RandomPlayer(board.getBoard(), 0);

			p = Math.random();
			if (p < 0.45) {
				depth = (int) (Math.random() * 6) + 3;
				ai2 = new MinMaxPlayer(board.getBoard(), 1, depth, new SimpleEvaluator());
			} else if (p < 0.9) {
				depth = (int) (Math.random() * 11) + 3;
				ai2 = new AlphaBetaPlayer(board.getBoard(), 1, depth, new SimpleEvaluator());
			} else
				ai2 = new RandomPlayer(board.getBoard(), 1);

			board.play(ai1, ai2);
			board.clearBoard();
		}
	}

}
