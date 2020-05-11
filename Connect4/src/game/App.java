package game;

import evaluators.SimpleEvaluator;
import evaluators.ThreeEvaluator;
import evaluators.ThreeEvaluatorv2;
import players.AlphaBetaPlayer;
import players.ComputerPlayer;
import players.MinMaxPlayer;
import players.RandomPlayer;

public class App {

	public static void main(String[] args) {
//		runRandomTests();
//		runTests();
		GUI gui = new GUI();
		gui.play();
	}

	private static void runTests() {
		ConnectFour board = new ConnectFour();
		ComputerPlayer ai1;// =new MinMaxPlayer(board.getBoard(),0, 8);
		ComputerPlayer ai2;// =new MinMaxPlayer(board.getBoard(),1, 7);
		int depth;
		double p;
		while (true) {
			for (int d1 = 7; d1 < 13; d1++) {
				ai1 = new RandomPlayer(board.getBoard(), 0);
				ai2 = new AlphaBetaPlayer(board.getBoard(), 1, d1, new ThreeEvaluatorv2(2 * d1));
				board.play(ai1, ai2);
				board.clearBoard();
				
				ai2 = new RandomPlayer(board.getBoard(), 1);
				ai1 = new AlphaBetaPlayer(board.getBoard(), 0, d1, new ThreeEvaluatorv2(2 * d1));
				board.play(ai1, ai2);
				board.clearBoard();
				
				ai1 = new RandomPlayer(board.getBoard(), 0);
				ai2 = new AlphaBetaPlayer(board.getBoard(), 1, d1, new ThreeEvaluator(2 * d1));
				board.play(ai1, ai2);
				board.clearBoard();
				
				ai2 = new RandomPlayer(board.getBoard(), 1);
				ai1 = new AlphaBetaPlayer(board.getBoard(), 0, d1, new ThreeEvaluator(2 * d1));
				board.play(ai1, ai2);
				board.clearBoard();
				
				ai1 = new RandomPlayer(board.getBoard(), 0);
				ai2 = new AlphaBetaPlayer(board.getBoard(), 1, d1, new ThreeEvaluatorv2(2 * d1));
				board.play(ai1, ai2);
				board.clearBoard();
				
				ai2 = new RandomPlayer(board.getBoard(), 1);
				ai1 = new AlphaBetaPlayer(board.getBoard(), 0, d1, new ThreeEvaluatorv2(2 * d1));
				board.play(ai1, ai2);
				board.clearBoard();
			}
			System.err.println("DONE!");
				for (int d0 = 5; d0 < 12; d0++) {
					for (int d1 = 7; d1 < 12; d1++) {
						ai1 = new AlphaBetaPlayer(board.getBoard(), 0, d0, new ThreeEvaluator(2 * d0));
						ai2 = new AlphaBetaPlayer(board.getBoard(), 1, d1, new ThreeEvaluatorv2(2 * d1));
						board.play(ai1, ai2);
						board.clearBoard();
						
						ai2 = new AlphaBetaPlayer(board.getBoard(), 1, d0, new ThreeEvaluator(2 * d0));
						ai1 = new AlphaBetaPlayer(board.getBoard(), 0, d1, new ThreeEvaluatorv2(2 * d1));
						board.play(ai1, ai2);
						board.clearBoard();
						
						ai1 = new AlphaBetaPlayer(board.getBoard(), 0, d0, new SimpleEvaluator(2 * d0));
						ai2 = new AlphaBetaPlayer(board.getBoard(), 1, d1, new ThreeEvaluator(2 * d1));
						board.play(ai1, ai2);
						board.clearBoard();
						
						ai2 = new AlphaBetaPlayer(board.getBoard(), 1, d0, new SimpleEvaluator(2 * d0));
						ai1 = new AlphaBetaPlayer(board.getBoard(), 0, d1, new ThreeEvaluator(2 * d1));
						board.play(ai1, ai2);
						board.clearBoard();
						
						ai1 = new AlphaBetaPlayer(board.getBoard(), 0, d0, new SimpleEvaluator(2 * d0));
						ai2 = new AlphaBetaPlayer(board.getBoard(), 1, d1, new ThreeEvaluatorv2(2 * d1));
						board.play(ai1, ai2);
						board.clearBoard();
						
						ai2 = new AlphaBetaPlayer(board.getBoard(), 1, d0, new SimpleEvaluator(2 * d0));
						ai1 = new AlphaBetaPlayer(board.getBoard(), 0, d1, new ThreeEvaluatorv2(2 * d1));
						board.play(ai1, ai2);
						board.clearBoard();
					}
				}
				System.err.println("DONE1");

			for (int d0 = 3; d0 < 9; d0++) {
				for (int d1 = 3; d1 < 12; d1++) {
					ai1 = new MinMaxPlayer(board.getBoard(), 0, d0, new ThreeEvaluator(2 * d0));
					ai2 = new AlphaBetaPlayer(board.getBoard(), 1, d1, new ThreeEvaluator(2 * d1));
					board.play(ai1, ai2);
					board.clearBoard();

					ai2 = new MinMaxPlayer(board.getBoard(), 1, d0, new ThreeEvaluator(2 * d0));
					ai1 = new AlphaBetaPlayer(board.getBoard(), 0, d1, new ThreeEvaluator(2 * d1));
					board.play(ai1, ai2);
					board.clearBoard();

					ai1 = new MinMaxPlayer(board.getBoard(), 0, d0, new ThreeEvaluatorv2(2 * d0));
					ai2 = new AlphaBetaPlayer(board.getBoard(), 1, d1, new ThreeEvaluatorv2(2 * d1));
					board.play(ai1, ai2);
					board.clearBoard();

					ai2 = new MinMaxPlayer(board.getBoard(), 1, d0, new ThreeEvaluatorv2(2 * d0));
					ai1 = new AlphaBetaPlayer(board.getBoard(), 0, d1, new ThreeEvaluatorv2(2 * d1));
					board.play(ai1, ai2);
					board.clearBoard();
				}
			}
			System.err.println("DONE2");
			
			
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
