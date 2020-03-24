import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Sudoku extends CSP {

	public Sudoku(char[] startVals) {
		super();
		D = new HashSet[9][9];
		int ch_i = 0;
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				if (startVals[ch_i] == '.') {
					D[row][col] = new HashSet<>(Arrays.asList('1', '2', '3', '4', '5', '6', '7', '8', '9'));
				} else {
					D[row][col] = new HashSet<>(Arrays.asList(startVals[ch_i]));
				}
			} // cols
		} // rows
	}

	@Override
	protected boolean checkRestrictions(char[][] V) {

		// check repetitions in rows
		Set<Character> set;
		for (int row = 0; row < 9; row++) {
			set = new HashSet<Character>();
			for (char i : V[row]) {
				if (i != ' ') {
					if (set.contains(i))
						return false;
					set.add(i);
				}
			}
		}

		// check repetitions in cols
		for (int col = 0; col < 9; col++) {
			set = new HashSet<Character>();
			for (int row = 0; row < 0; row++) {
				if (V[row][col] != ' ') {
					if (set.contains(V[row][col]))
						return false;
					set.add(V[row][col]);
				}
			}
		}
		// check repetitions in small squares
		for (int row = 0; row < 9; row += 3) {
			for (int col = 0; col < 9; col += 3) {
				if (!checkSmallSquare(V, 0, 0))
					return false;
			}
		}

		return true;
	}

	private boolean checkSmallSquare(char[][] V, int startRow, int startCol) {
		Set<Character> set = new HashSet<Character>();
		for (int row = startRow; row < startRow + 3; row++) {
			for (int col = startCol; col < startCol + 3; col++) {
				if (V[row][col] != ' ') {
					if (set.contains(V[row][col]))
						return false;
					set.add(V[row][col]);
				}
			}
		}
		return true;
	}

	@Override
	public ArrayList<char[][]> solve() {
		// TODO Auto-generated method stub
		return null;
	}

}
