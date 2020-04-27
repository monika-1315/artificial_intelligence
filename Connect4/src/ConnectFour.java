import java.util.Scanner;

public class ConnectFour {

	private Board board;
	private volatile boolean isDropped;

	public ConnectFour(int w, int h) {
		board = new Board(w, h);
	}

	public ConnectFour() {
		board = new Board(7, 6);
	}
	public void clearBoard() {
		board = new Board(7, 6);
	}

	@Override
	public String toString() {
		return board.toString();
	}

	public Board getBoard() {
		return board;
	}

	private void chooseAndDrop(char symbol, Scanner input) {
		isDropped = false;
		do {
			System.out.println("\nPlayer " + symbol + " turn: ");
			try {
				int col = input.nextInt();
				if (!(0 <= col && col < board.getWidth())) {
					System.out.println("Column must be between 0 and " + (board.getWidth() - 1));
				} else {
					isDropped = board.drop(symbol, col);
					if (!isDropped) {
						System.out.println("Column " + col + " is full.");
					}
				}
			} catch (Exception e) {
				System.err.println("Enter correct number!");
				input.next();
			}

		} while (!isDropped);
	}

	public void play(GUI gui) {
		play(null, null, gui);
	}

	public void play(ComputerPlayer ai, GUI gui) {
		play(null, ai, gui);
	}

	public void play(ComputerPlayer ai1, ComputerPlayer ai2, GUI gui) {

//		System.out.println(this.toString());
		int moves = board.getHeight() * board.getWidth();

		for (int player = 0; moves-- > 0; player = 1 - player) {
			char symbol = Board.PLAYERS[player];

			if (player == 0)
				if (ai1 == null) {
					chooseAndDropGUI(player, gui);
				} else {
					gui.setInfo("Computer " + (player + 1) + " is thinking...");
					board.drop(symbol, ai1.nextMove());
					gui.refresh();
				}
			else {
				if (ai2 == null) {
					
					chooseAndDropGUI(player, gui);
				} else {
					gui.setInfo("Computer " + (player + 1) + " is thinking...");
					board.drop(symbol, ai2.nextMove());
					gui.refresh();
				}
			}
			System.out.println(this.toString());

			if (board.isWinningPlay()) {
				gui.onWin("Player "+Board.PLAYERS_COL[player]);
				return;
			}
		}

		gui.onWin("Nobody");
	}

	private void chooseAndDropGUI(int player, GUI gui) {
		gui.setPlayer(player);
		gui.setButtonsEnabled(true);
		isDropped = false;
		gui.setInfo("\nPlayer " + Board.PLAYERS_COL[player] + " turn: ");
		do {
		} while (!isDropped);
		gui.setButtonsEnabled(false);
	}

	public void playInConsole(ComputerPlayer ai1, ComputerPlayer ai2) {
		try (Scanner input = new Scanner(System.in)) {
			System.out.println("Use 0-" + (board.getWidth() - 1) + " to choose a column");
			System.out.println(this.toString());
			int moves = board.getHeight() * board.getWidth();

			for (int player = 0; moves-- > 0; player = 1 - player) {
				char symbol = Board.PLAYERS[player];

				if (player == 0)
					if (ai1 == null) {
						chooseAndDrop(symbol, input);
					} else {
						System.out.println("Computer " + player + " is thinking...");
						board.drop(symbol, ai1.nextMove());
					}
				else {
					if (ai2 == null) {
						chooseAndDrop(symbol, input);
					} else {
						System.out.println("Computer " + player + " is thinking...");
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

	public void setDroppedTrue() {
		isDropped=true;
	}
}