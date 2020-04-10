import java.util.Scanner;

public class ConnectFour {

	private Board board;
	

	public ConnectFour(int w, int h) {
		board=new Board(w, h);
	}


	@Override
	public String toString() {
		return board.toString();
	}


	public Board getBoard() {
		return board;
	}

	private void chooseAndDrop(char symbol, Scanner input) {
		boolean isDropped = false;
		do {
			System.out.println("\nPlayer " + symbol + " turn: ");
			try{
				int col = input.nextInt();
				isDropped = board.drop(symbol, col);
			}
			catch(Exception e) {
				System.err.println("Enter correct number!");
				input.next();
			}
			
		} while (!isDropped);
	}

	public void play() {
		play(null, null);
	}
	
	public void play(ComputerPlayer ai) {
		play(null, ai);
	}
			
	public void play(ComputerPlayer ai1, ComputerPlayer ai2) {
		try (Scanner input = new Scanner(System.in)) {
			System.out.println("Use 0-" + (board.getWidth() - 1) + " to choose a column");
			System.out.println(this.toString());
			int moves = board.getHeight() * board.getWidth();

			for (int player = 0; moves-- > 0; player = 1 - player) {
				char symbol = Board.PLAYERS[player];
				
				if(player==0)
					if(ai1==null) {
						chooseAndDrop(symbol, input);
					}
					else {
						System.out.println("Computer "+player +" is thinking...");
						board.drop(symbol, ai1.nextMove());
					}
				else {
					if(ai2==null) {
						chooseAndDrop(symbol, input);
					}
					else {
						System.out.println("Computer "+player +" is thinking...");
						board.drop(symbol, ai2.nextMove());
					}
				}
				System.out.println(this.toString());

				if (board.isWinningPlay()) {
					System.out.println("\nPlayer " + symbol + " wins!");
					return;
				}
			}

			System.out.println("Game over. No winner. Try again!");
		}
	}



}