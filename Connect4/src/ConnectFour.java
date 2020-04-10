import java.util.Arrays;
import java.util.Scanner;

public class ConnectFour {

	private static final char[] PLAYERS = { 'O', 'X' };

	private final int width, height;
	private final char[][] grid;

	// we store last move made by a player
	private int lastCol = -1, lastTop = -1;

	public ConnectFour(int w, int h) {

		width = w;
		height = h;
		grid = new char[h][];
		for (int i = 0; i < h; i++) {
			Arrays.fill(grid[i] = new char[w], '.');
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public char[][] getGrid() {
		return grid;
	}


	@Override
	public String toString() {
		String ret = "";
		String nums = "";
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				ret += grid[row][col];
			}
			ret += "\n";
		}
		for (int col = 0; col < width; col++) {
			nums += col;
		}

		return ret + nums;
	}

	private String horizontal() {
		return new String(grid[lastTop]);
	}

	private String vertical() {

		StringBuilder sb = new StringBuilder(height);
		for (int h = 0; h < height; h++) {
			sb.append(grid[h][lastCol]);
		}

		return sb.toString();

	}

	private String slashDiagonal() {

		StringBuilder sb = new StringBuilder(height);

		for (int h = 0; h < height; h++) {
			int w = lastCol + lastTop - h;
			if (0 <= w && w < width) {
				sb.append(grid[h][w]);
			}
		}

		return sb.toString();
	}

	private String backslashDiagonal() {

		StringBuilder sb = new StringBuilder(height);

		for (int h = 0; h < height; h++) {
			int w = lastCol - lastTop + h;
			if (0 <= w && w < width) {
				sb.append(grid[h][w]);
			}
		}

		return sb.toString();
	}

	public static boolean contains(String str, String substring) {
		return str.indexOf(substring) >= 0;
	}

	private boolean isWinningPlay() {
		if (lastCol == -1) {
			System.err.println("No move has been made yet");
			return false;
		}

		char sym = grid[lastTop][lastCol];
		String streak = "" + sym + sym + sym + sym;
		return contains(horizontal(), streak) || contains(vertical(), streak) || contains(slashDiagonal(), streak)
				|| contains(backslashDiagonal(), streak);
	}

	private void chooseAndDrop(char symbol, Scanner input) {
		boolean isDropped = false;
		do {
			System.out.println("\nPlayer " + symbol + " turn: ");
			try{
				int col = input.nextInt();
				isDropped = drop(symbol, col);
			}
			catch(Exception e) {
				System.err.println("Enter correct number!");
				input.next();
			}
			
		} while (!isDropped);
	}

	private boolean drop(char symbol, int col) {
		if (!(0 <= col && col < width)) {
			System.out.println("Column must be between 0 and " + (width - 1));
			return false;
		}

		for (int h = height - 1; h >= 0; h--) {
			if (grid[h][col] == '.') {
				grid[lastTop = h][lastCol = col] = symbol;
				return true;
			}
		}
		// column is full
		System.out.println("Column " + col + " is full.");
		return false;
	}

	public void play() {
		play(null, null);
	}
	
	public void play(ComputerPlayer ai) {
		play(null, ai);
	}
			
	public void play(ComputerPlayer ai1, ComputerPlayer ai2) {
		try (Scanner input = new Scanner(System.in)) {
			System.out.println("Use 0-" + (width - 1) + " to choose a column");
			System.out.println(this.toString());
			int moves = height * width;

			for (int player = 0; moves-- > 0; player = 1 - player) {
				char symbol = PLAYERS[player];
				
				if(player==0)
					if(ai1==null) {
						chooseAndDrop(symbol, input);
					}
					else {
						drop(symbol, ai1.nextMove());
					}
				else {
					if(ai2==null) {
						chooseAndDrop(symbol, input);
					}
					else {
						drop(symbol, ai2.nextMove());
					}
				}
				System.out.println(this.toString());

				if (isWinningPlay()) {
					System.out.println("\nPlayer " + symbol + " wins!");
					return;
				}
			}

			System.out.println("Game over. No winner. Try again!");
		}
	}

}