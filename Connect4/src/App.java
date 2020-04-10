import java.util.Scanner;

public class App {

	public static void main(String[] args) {
		int height = 6;
		int width = 8;

		ConnectFour board = new ConnectFour(width, height);
		RandomPlayer ai=new RandomPlayer(board);
		board.playWithComputer(ai);
	}

}
