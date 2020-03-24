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
	private int returns;
	private ArrayList<Integer> variables;
	private LinkedList<char[][]> solutions;

	public Sudoku(char[] startVals) {
		super();
		initV = new char[SUDOKU_SIZE][SUDOKU_SIZE];
		D = new LinkedList[SUDOKU_SIZE][SUDOKU_SIZE];
		int ch_i = 0;
		for (int row = 0; row < SUDOKU_SIZE; row++) {
			for (int col = 0; col < SUDOKU_SIZE; col++) {
//				System.out.println(row+" "+col+" "+startVals[ch_i]);
				initV[row][col] = startVals[ch_i];
				if (startVals[ch_i] == '.') {
					D[row][col] = new LinkedList<>(Arrays.asList('1', '2', '3', '4', '5', '6', '7', '8', '9'));
				} else {
					D[row][col] = new LinkedList<>(Arrays.asList(startVals[ch_i]));
				}
				ch_i++;
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
				 if (i != '.') {
				if (set.contains(i))
					return false;
				set.add(i);
				 }
			}
		}

		// check repetitions in cols
		for (int col = 0; col < SUDOKU_SIZE; col++) {
			set = new HashSet<Character>();
			for (int row = 0; row < 0; row++) {
				 if (V[row][col] != '.') {
				if (set.contains(V[row][col]))
					return false;
				set.add(V[row][col]);
				 }
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
				 if (V[row][col] != '.') {
				if (set.contains(V[row][col]))
					return false;
				set.add(V[row][col]);
				 }
			}
		}
		return true;
	}

	@Override
	public LinkedList<char[][]> solve() {
		return backtracking();
	}

	private LinkedList<char[][]> backtracking() {
		solutions = new LinkedList<char[][]>();
		t0 = java.lang.System.currentTimeMillis();
		nodes = 0;
		returns = 0;
		variables = getVars();

		backtracking(0, initV);

		System.out.println((java.lang.System.currentTimeMillis()-t0)+" ms. Found "+solutions.size()+" solutions "+nodes+" nodes visited "+returns+" returns");
		for (char[][]sol: solutions) {
			for(int r=0; r<SUDOKU_SIZE; r++) {
				System.out.println(sol[r]);
			}
			System.out.println();
		}
		return solutions;
	}

	private void backtracking(int lvl, char[][] vals) {
//		if (!checkRestrictions(vals)) {
//			returns++;
//			return;
//		}
		if (lvl == variables.size()) {
			if (checkRestrictions(vals)) {
				if(solutions.size()==0) {
					System.out.println("First solution. Time: "+(java.lang.System.currentTimeMillis()-t0)+" ms. "
							+nodes+" nodes visited, "+returns+" returns");
				}
				solutions.add(vals);
				return;
			} else {
				returns++;
				return;
			}
		}
		
		int var = variables.get(lvl);
		int row = Math.floorDiv(var, 10);
		int col = var % 10;
		char[][] sol;
		
		for (char v : nextVals(row, col)) {
			nodes++;
			sol = vals.clone();
			sol[row][col] = v;
			if(checkRestrictions(sol))
				backtracking(lvl + 1, sol);
		}
		returns++;

	}

	private ArrayList<Integer> getVars() {
		return emptyVars();
	}

	private ArrayList<Integer> emptyVars() {// heuristic of selecting variables
		ArrayList<Integer> vars = new ArrayList<Integer>();
		for (int row = 0; row < SUDOKU_SIZE; row++) {
			System.out.println(initV[row]);
			for (int col = 0; col < SUDOKU_SIZE; col++) {
				if (initV[row][col]== '.')
					vars.add(row * 10 + col);
			}
		}
		return vars;
	}

	private LinkedList<Character> nextVals(int row, int col) {
		return nextValFromD(row, col);
	}

	private LinkedList<Character> nextValFromD(int row, int col) {

		return D[row][col];
	}
}
