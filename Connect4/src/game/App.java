package game;

import evaluators.SimpleEvaluator;
import evaluators.ThreeEvaluator;
import players.AlphaBetaPlayer;
import players.ComputerPlayer;
import players.MinMaxPlayer;
import players.RandomPlayer;

public class App {

	public static void main(String[] args) {
//		runRandomTests();
		runTests();
//		GUI gui=new GUI();
//		gui.play();
	}

	private static void runTests() {
		ConnectFour board = new ConnectFour();
		ComputerPlayer ai1;// =new MinMaxPlayer(board.getBoard(),0, 8);
		ComputerPlayer ai2;// =new MinMaxPlayer(board.getBoard(),1, 7);
		int depth;
		double p;
		while (true) {
			for(int d0=3; d0<9; d0++) {
				for(int d1=3; d1<14; d1++) {
					ai1=new MinMaxPlayer(board.getBoard(), 0, d0, new ThreeEvaluator(2*d0));
					ai2=new AlphaBetaPlayer(board.getBoard(), 1, d1, new ThreeEvaluator(2*d1));
					board.play(ai1, ai2);
					board.clearBoard();
					
					ai2=new MinMaxPlayer(board.getBoard(), 1, d0, new ThreeEvaluator(2*d0));
					ai1=new AlphaBetaPlayer(board.getBoard(), 0, d1, new ThreeEvaluator(2*d1));
					board.play(ai1, ai2);
					board.clearBoard();
				}
			}
			
		}
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
