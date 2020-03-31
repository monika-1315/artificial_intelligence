import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Sudoku extends CSP<Character> {

	public static int SUDOKU_SIZE = 9;

	public Sudoku(char[] startVals) {
		super();
		initV = new char[SUDOKU_SIZE][SUDOKU_SIZE];
		D = new LinkedList[SUDOKU_SIZE][SUDOKU_SIZE];
		int ch_i = 0;
		// insert initial values from the board and set domains for each field
		for (int row = 0; row < SUDOKU_SIZE; row++) {
			for (int col = 0; col < SUDOKU_SIZE; col++) {
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

	private boolean filterDomains(char[][] V, int row, int col, LinkedList<Character>[][] D) {
		char val = V[row][col];
//		System.out.println(val +" row "+row+" col "+col);
		for (int i = 0; i < SUDOKU_SIZE; i++) {
			if (i != row) {
//				System.out.println("row= "+row+" col= "+col+" i= "+i+D[i][col]);
				D[i][col].remove(new Character(val));
				if (!checkDomain(V, i, col, D))
					return false;
			}
		}
		for (int j = 0; j < SUDOKU_SIZE; j++) {
			if (j != col) {
//				System.out.println("row= "+row+" col= "+col+" j= "+j+D[row][j]);
				D[row][j].remove(new Character(val));
				if (!checkDomain(V, row, j, D))
					return false;
			}
		}
		int r0 = Math.floorDiv(row, 3);
		int c0 = Math.floorDiv(col, 3);
		for (int r = r0; r < r0 + 3; r++) {
			for (int c = c0; c < c0 + 3; c++) {
				if (r != row && c != col) {
					D[r][c].remove(new Character(val));
					if (!checkDomain(V, r, c, D))
						return false;
				}
			}
		}
		return true;
	}

	private boolean checkDomain(char[][] V, int row, int col, LinkedList<Character>[][] D) {
		int domSize;
		domSize = D[row][col].size();
		if (domSize == 0) {
			returns++;
			return false;
		}
		if (domSize == 1 && V[row][col] == '.') {
			V[row][col] = D[row][col].getFirst();
			if (!filterDomains(V, row, col, D))
				return false;
		}
		return true;
	}

	@Override
	protected boolean checkRestrictions(char[][] V, boolean partial) {

		// check repetitions in rows
		Set<Character> set;
		for (int row = 0; row < SUDOKU_SIZE; row++) {
			set = new HashSet<Character>();
			for (char i : V[row]) {
				if (!(partial && i == '.')) {// while partial checking accept empty fields
					if (set.contains(i))
						return false;
					set.add(i);
				}
			}
		}

		// check repetitions in cols
		for (int col = 0; col < SUDOKU_SIZE; col++) {
			set = new HashSet<Character>();
			for (int row = 0; row < SUDOKU_SIZE; row++) {
				if (!(partial && V[row][col] == '.')) {
					if (set.contains(V[row][col]))
						return false;
					set.add(V[row][col]);
//					System.out.println(set);
				}
			}
		}
		// check repetitions in small squares
		for (int row = 0; row < SUDOKU_SIZE; row += 3) {
			for (int col = 0; col < SUDOKU_SIZE; col += 3) {
				if (!checkSmallSquare(V, row, col, partial))
					return false;
			}
		}
		return true;
	}

	private boolean checkSmallSquare(char[][] V, int startRow, int startCol, boolean partial) {
		Set<Character> set = new HashSet<Character>();
		for (int row = startRow; row < startRow + 3; row++) {
			for (int col = startCol; col < startCol + 3; col++) {
				if (!(partial && V[row][col] == '.')) {
					if (set.contains(V[row][col]))
						return false;
					set.add(V[row][col]);
				}
			}
		}
		return true;
	}

	public LinkedList<char[][]> forwardChecking() {
		solutions = new LinkedList<char[][]>();
		t0 = java.lang.System.currentTimeMillis();
		nodes = 0;
		returns = 0;
		nextVars = getVars();

		forwardChecking(0, initV, D);

		System.out.println("FC " + (java.lang.System.currentTimeMillis() - t0) + " ms. Found " + solutions.size()
				+ " solutions " + nodes + " nodes visited " + returns + " returns");
		for (char[][] sol : solutions) {
			printSol(sol);
			System.out.println();
		}
		return solutions;
	}

	protected void forwardChecking(int lvl, char[][] vals, LinkedList<Character>[][] dom0) {
//		System.out.println("lvl= " + lvl);
		if (lvl == nextVars.size()) {
			if (checkRestrictions(vals, false)) {
				if (solutions.size() == 0) {
					System.out.println("First solution. Time: " + (java.lang.System.currentTimeMillis() - t0) + " ms. "
							+ nodes + " nodes visited, " + returns + " returns");
				}
				solutions.add(vals);
			} else {
				returns++;
			}
			return;
		}

		int var = nextVars.get(lvl);
		int row = Math.floorDiv(var, 10);
		int col = var % 10;

		char[][] sol;
		LinkedList<Character>[][] doms;

		if (vals[row][col] == '.') {
			for (char v : nextVals(var)) {
				nodes++;
				sol = new char[vals.length][vals[0].length];
				doms = new LinkedList[vals.length][vals[0].length];
				for (int chrow = 0; chrow < sol.length; chrow++) {
					sol[chrow] = vals[chrow].clone();
					for (int chcol = 0; chcol < doms[0].length; chcol++) {
//						doms[chrow][chcol] =(LinkedList<Character>) dom0[chrow][chcol].clone();
						doms[chrow][chcol] = new LinkedList<Character>();
						for (char domVal : dom0[chrow][chcol]) {
							doms[chrow][chcol].add(domVal);
						}
					}
				} // copy
				sol[row][col] = v;
				doms[row][col].clear();
				doms[row][col].add(v);

				if (filterDomains(sol, row, col, doms))
					forwardChecking(lvl + 1, sol, doms);
			} // for possible values of variable
			returns++;// no more values for this variable
		} // if var is empty

	}

	protected ArrayList<Integer> emptyVars() {// heuristic of selecting variables
		ArrayList<Integer> vars = new ArrayList<Integer>();
		for (int row = 0; row < SUDOKU_SIZE; row++) {
			for (int col = 0; col < SUDOKU_SIZE; col++) {
				if (initV[row][col] == '.')
					vars.add(row * 10 + col);
			}
		}
		return vars;
	}

	@Override
	protected void insert(char[][] sol, int var, Character v, int lvl) {
		int row = Math.floorDiv(var, 10);
		int col = var % 10;
		sol[row][col] = v;
		if (checkRestrictions(sol, true))
			backtracking(lvl + 1, sol);
		else
			returns++;

	}

	@Override
	protected LinkedList<Character> nextValFromD(int var) {
		int row = Math.floorDiv(var, 10);
		int col = var % 10;
		return D[row][col];
	}

	private static void printSol(char[][] board) {
		for (int r = 0; r < board.length; r++) {
			System.out.println(board[r]);
		}
	}

}