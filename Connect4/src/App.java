public class App {

	public static void main(String[] args) {

//		ConnectFour board = new ConnectFour();
//		ComputerPlayer ai=new MinMaxPlayer(board.getBoard(),0, 8);
//		ComputerPlayer ai2=new MinMaxPlayer(board.getBoard(),1, 7);
//		board.playInConsole(null, ai2);
		GUI gui=new GUI();
		gui.play();
	}

}
