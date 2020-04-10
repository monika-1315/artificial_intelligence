public class App {

	public static void main(String[] args) {
		int height = 6;
		int width = 7;

		ConnectFour board = new ConnectFour(width, height);
		ComputerPlayer ai=new MinMaxPlayer(board.getBoard(),1);
		board.play(ai);
	}

}
