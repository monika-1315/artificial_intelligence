import java.util.Arrays;

public class Board {

	static final char[] PLAYERS = { 'O', 'X' };
	
	public static char[] getPlayers() {
		return PLAYERS;
	}

	private final int width, height;
	private final char[][] grid;

	// last move made by a player
	private int lastCol = -1, lastTop = -1;

	public Board(int w, int h) {
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
	public boolean isFull() {
		for (int row=0; row<height; row++) {
			for (int col=0; col<width; col++) {
				if(grid[row][col]=='.')
					return false;
			}
		}
		return true;
	}

	boolean isWinningPlay() {
		if (lastCol == -1) {
			System.err.println("No move has been made yet");
			return false;
		}

		char sym = grid[lastTop][lastCol];
		String streak = "" + sym + sym + sym + sym;
		return contains(horizontal(), streak) || contains(vertical(), streak) || contains(slashDiagonal(), streak)
				|| contains(backslashDiagonal(), streak);
	}
	
	public boolean isColumnFull(int col) {
		return grid[0][col] != '.';
	}

	public char getLastPlayerSymbol() {
		return grid[lastTop][lastCol];
	}

	boolean drop(char symbol, int col) {
		if (!(0 <= col && col < width)) {
//			System.out.println("Column must be between 0 and " + (width - 1));
			return false;
		}

		for (int h = height - 1; h >= 0; h--) {
			if (grid[h][col] == '.') {
				grid[lastTop = h][lastCol = col] = symbol;
				return true;
			}
		}
		// column is full
//		System.out.println("Column " + col + " is full.");
		return false;
	}
	

	public Board clone() {
		Board copy = new Board(width, height);
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				copy.grid[row][col]= grid[row][col];
			}
		}
		return copy;
	}

	public char[][] getGrid(){
		return grid;
	}
}
