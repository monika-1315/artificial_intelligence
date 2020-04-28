public class App {

	public static void main(String[] args) {

		ConnectFour board = new ConnectFour();
		ComputerPlayer ai1;//=new MinMaxPlayer(board.getBoard(),0, 8);
		ComputerPlayer ai2;//=new MinMaxPlayer(board.getBoard(),1, 7);
		int depth;
		while(true) {
			if(Math.random()<0.5) {
				depth=(int) (Math.random()*7)+3;
				ai1=new MinMaxPlayer(board.getBoard(), 0, depth);
			}else {
				depth=(int) (Math.random()*11)+3;
				ai1=new AlphaBetaPlayer(board.getBoard(), 0, depth);
			}
			if(Math.random()<0.5) {
				depth=(int) (Math.random()*7)+3;
				ai2=new MinMaxPlayer(board.getBoard(), 1, depth);
			}else {
				depth=(int) (Math.random()*11)+3;
				ai2=new AlphaBetaPlayer(board.getBoard(), 1, depth);
			}
			board.play(ai1, ai2);
			board.clearBoard();
		}

		
		
//		GUI gui=new GUI();
//		gui.play();
	}

}
