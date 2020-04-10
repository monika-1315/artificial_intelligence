public class App {

	public static void main(String[] args) {

		ConnectFour board = new ConnectFour();
		ComputerPlayer ai=new MinMaxPlayer(board.getBoard(),1, 7);
		board.play(ai);
	}

}
