import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Sudoku extends CSP {

	public static int SUDOKU_SIZE = 9;

	private char[][] initV;
	private long t0;
	private int nodes;
	private int backs;
	private LinkedList<Integer> variables;
	private ArrayList<char[][]> solutions;

	public Sudoku(char[] startVals) {
		super();
		initV = new char[SUDOKU_SIZE][SUDOKU_SIZE];
		D = new LinkedList[SUDOKU_SIZE][SUDOKU_SIZE];
		int ch_i = 0;
		for (int row = 0; row < SUDOKU_SIZE; row++) {
			for (int col = 0; col < SUDOKU_SIZE; col++) {
				initV[row][col] = startVals[ch_i];
				if (startVals[ch_i] == '.') {
					D[row][col] = new LinkedList<>(Arrays.asList('1', '2', '3', '4', '5', '6', '7', '8', '9'));
				} else {
					D[row][col] = new LinkedList<>(Arrays.asList(startVals[ch_i]));
				}
			} // cols
		} // rows
	}

	@Override
	protected boolean checkRestrictions(char[][] V) {

		// check repetitions in rows
		Set<Character> set;
		for (int row = 0; row < SUDOKU_SIZE; row++) {
			set = new HashSet<Character>();
			for (char i : V[row]) {
				// if (i != ' ') {
				if (set.contains(i))
					return false;
				set.add(i);
				// }
			}
		}

		// check repetitions in cols
		for (int col = 0; col < SUDOKU_SIZE; col++) {
			set = new HashSet<Character>();
			for (int row = 0; row < 0; row++) {
				// if (V[row][col] != ' ') {
				if (set.contains(V[row][col]))
					return false;
				set.add(V[row][col]);
				// }
			}
		}
		// check repetitions in small squares
		for (int row = 0; row < SUDOKU_SIZE; row += 3) {
			for (int col = 0; col < SUDOKU_SIZE; col += 3) {
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
				// if (V[row][col] != ' ') {
				if (set.contains(V[row][col]))
					return false;
				set.add(V[row][col]);
				// }
			}
		}
		return true;
	}

	@Override
	public ArrayList<char[][]> solve() {
		return backtracking();
	}

	private ArrayList<char[][]> backtracking() {
		solutions = new ArrayList<char[][]>();
		t0 = java.lang.System.currentTimeMillis();
		nodes = 0;
		backs = 0;
		variables = getVars();

		backtracking(initV);

		return solutions;
	}

	private void backtracking(char[][] vals) {
		int var=variables.getFirst();
		variables.removeFirst();
		int row=Math.floorDiv(var,10);
		int col=var%10;
		char[][] sol=vals.clone();
		
	}

	private LinkedList<Integer> getVars() {
		return emptyVars();
	}

	private LinkedList<Integer> emptyVars() {// heuristic of selecting variables
		LinkedList<Integer> vars = new LinkedList<Integer>();
		for (int row = 0; row < 0; row++) {
			for (int col = 0; col < SUDOKU_SIZE; col++) {
				if (initV[row][col] == ' ')
					vars.add(row * 10 + col);
			}
		}
		return vars;
	}

	private char nextVals(int row, int col, char lastVal) {
		return nextValFromD(row, col, lastVal);
	}

	private char nextValFromD(int row, int col, char lastVal) {
		int ind = D[row][col].indexOf(lastVal);
		if (ind == -1)
			return D[row][col].get(0);
		if (ind == D[row][col].size() - 1)
			return ' ';
		return D[row][col].get(ind);
	}
}
