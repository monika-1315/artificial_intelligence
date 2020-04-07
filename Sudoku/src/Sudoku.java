import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Sudoku extends CSP<Character> {

	public static final int SUDOKU_SIZE = 9;
	public static final int VAR_INORDER = 0;
	public static final int VAR_DOMAIN_SORTED = 1;
	public static final int VAR_RANDOM = 2;
	public static final int VAR_COLS = 3;
	public static final int VAL_INORDER = 0;
	public static final int VAL_RANDOM = 1;
	public static final int VAL_LEAST_LEFT = 2;
	public static final boolean BACKTRACKING = false;
	public static final boolean FORWARD_CHECKING = true;

	private int pickVarHeur = 0;
	private int pickValHeur = 0;
	private int[] numbersCount= new int[9];

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
					int v=Integer.parseInt(String.valueOf(startVals[ch_i]));
					numbersCount[v-1]++;
				}
				ch_i++;
			} // cols
		} // rows

	}

	public LinkedList<char[][]> solve(boolean method) {
		pickVarHeur = 0;
		pickVarHeur = 0;
//		printHeuristicsInfo();
		numbersCount= new int[9];
		if (method == BACKTRACKING)
			return backtracking();
		else
			return forwardChecking();
	}


	public LinkedList<char[][]> solve(boolean method, int valHeur, int varHeur) {
		pickVarHeur = varHeur;
		pickValHeur = valHeur;
//		printHeuristicsInfo();
		numbersCount= new int[9];

		if (method == BACKTRACKING) {
			System.out.print("Backtracking ");
			return backtracking();
		}
		else {
			System.out.print("Forward checking ");
			return forwardChecking();
			}
	}

	private void printHeuristicsInfo() {
		if (pickVarHeur == VAR_DOMAIN_SORTED)
			System.out.print("Variables - sorted by initial domain sizes ");
		else if (pickVarHeur == VAR_RANDOM)
			System.out.print("Variables - picked in a random way ");
		else if (pickVarHeur==VAR_COLS)
			System.out.print("Variables - in order by columns ");
		else // VAL_INORDER
			System.out.print("Variables - in order of definition ");
		
		if (pickValHeur == VAL_RANDOM)
			System.out.println("Values - picked in a random way");
		else if (pickValHeur==VAL_LEAST_LEFT)
			System.out.println("Values - what's less left to fullfill");
		else // VAL_INORDER
			System.out.println("Values - in order of definition");
		
	}
	

	@Override
	protected void insert(char[][] sol, int var, Character v, int lvl) {
		int row = Math.floorDiv(var, 10);
		int col = var % 10;
		sol[row][col] = v;
//		System.out.println(v);
		int vint=Integer.parseInt(String.valueOf(v));
		numbersCount[vint-1]++;
	}

	private boolean filterDomains(char[][] V, int row, int col, LinkedList<Character>[][] D) {
		char val = V[row][col];
		for (int i = 0; i < SUDOKU_SIZE; i++) {
			if (i != row) {
				D[i][col].remove(new Character(val));
				if (!checkDomain(V, i, col, D))
					return false;
			}
		}
		for (int j = 0; j < SUDOKU_SIZE; j++) {
			if (j != col) {
				D[row][j].remove(new Character(val));
				if (!checkDomain(V, row, j, D))
					return false;
			}
		}
		int r0 = Math.floorDiv(row, 3);
		int c0 = Math.floorDiv(col, 3);
		for (int r = 3 * r0; r < 3 * r0 + 3; r++) {
			for (int c = 3 * c0; c < 3 * c0 + 3; c++) {
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

	private LinkedList<char[][]> forwardChecking() {
		initialize();
		
		forwardChecking(0, initV, D);

		printSolutions();
		return solutions;
	}

	private void forwardChecking(int lvl, char[][] vals, LinkedList<Character>[][] dom0) {
		if (lvl == nextVars.size()) {
			checkFullSolution(vals);
			return;
		}
		int var = nextVars.get(lvl);
		int row = Math.floorDiv(var, 10);
		int col = var % 10;
		char[][] sol;
		LinkedList<Character>[][] doms;

		if (vals[row][col] != '.') {
			forwardChecking(lvl + 1, vals, dom0);
		} else {
			for (char v : nextVals(var, dom0)) {
				nodes++;
				sol = new char[vals.length][vals[0].length];
				doms = new LinkedList[vals.length][vals[0].length];
				copy(sol, doms, vals, dom0);
				sol[row][col] = v;
				int vint=Integer.parseInt(String.valueOf(v));
				numbersCount[vint-1]++;
				doms[row][col].clear();
				doms[row][col].add(v);

				if (filterDomains(sol, row, col, doms))
					forwardChecking(lvl + 1, sol, doms);
			} // for possible values of variable
			returns++;// no more values for this variable
		} // if var is empty

	}//forward checking

	private void copy(char[][] sol, LinkedList<Character>[][] doms, char[][] vals, LinkedList<Character>[][] dom0) {
		for (int chrow = 0; chrow < sol.length; chrow++) {
			sol[chrow] = vals[chrow].clone();
			for (int chcol = 0; chcol < doms[0].length; chcol++) {
//				doms[chrow][chcol] =(LinkedList<Character>) dom0[chrow][chcol].clone();
				doms[chrow][chcol] = new LinkedList<Character>();
				for (char domVal : dom0[chrow][chcol]) {
					doms[chrow][chcol].add(domVal);
				}
			}
		} // copy
	}
	@Override
	protected ArrayList<Integer> getVars() {// static heuristic for picking next variables
		if (pickVarHeur == VAR_DOMAIN_SORTED)
			return domSortVars();
		else if (pickVarHeur==VAR_COLS)
			return emptyVarsCols();
		else if (pickVarHeur == VAR_RANDOM)
			return randomVars();
		else // VAL_INORDER
			return emptyVars();
	}

	@Override
	protected ArrayList<Integer> emptyVars() {
		ArrayList<Integer> vars = new ArrayList<Integer>();
		for (int row = 0; row < SUDOKU_SIZE; row++) {
			for (int col = 0; col < SUDOKU_SIZE; col++) {
				if (initV[row][col] == '.')
					vars.add(row * 10 + col);
				else
					filterDomains(initV, row, col, this.D);
			}
		}
		return vars;
	}
	class EmptyColsComparator implements Comparator<Integer> {

		@Override
		public int compare(Integer o1, Integer o2) {
			int count1=0, count2=0;
			for (int r1=0; r1<SUDOKU_SIZE; r1++) {
				if (initV[r1][o1]=='.')
					count1++;
			}
			for (int r2=0; r2<SUDOKU_SIZE; r2++) {
				if (initV[r2][o2]=='.')
					count2++;
			}
			return count1 - count2;
		}

	}
	
	protected ArrayList<Integer> emptyVarsCols() {
		ArrayList<Integer> vars = new ArrayList<Integer>();
		LinkedList<Integer> cols= new LinkedList<Integer>(Arrays.asList(0,1,2,3,4,5,6,7,8));
		Collections.sort(cols, new EmptyColsComparator());
		for (int col: cols) {
			for (int row = 0; row < SUDOKU_SIZE; row++) {
				if (initV[row][col] == '.')
					vars.add(row * 10 + col);
				else
					filterDomains(initV, row, col, this.D);
			}
		}
		return vars;
	}

	protected ArrayList<Integer> domSortVars() {
		ArrayList<Integer> list = emptyVars();
		Collections.sort(list, new LengthComparator());
		return list;
	}

	class LengthComparator implements Comparator<Integer> {

		@Override
		public int compare(Integer o1, Integer o2) {
			int i1 = D[Math.floorDiv(o1, 10)][Math.floorMod(o1, 10)].size();
			int i2 = D[Math.floorDiv(o2, 10)][Math.floorMod(o2, 10)].size();

			return i1 - i2;
		}

	}

	protected ArrayList<Integer> randomVars() {
		ArrayList<Integer> list = emptyVars();
		Collections.shuffle(list);
		return list;
	}


	@Override
	protected LinkedList<Character> nextVals(int var, LinkedList<Character>[][] D) {
//		System.out.println("ah");
		if (pickValHeur == VAL_RANDOM) {
			return randValFromD(var, D);
		}
		else if (pickValHeur==VAL_LEAST_LEFT) {
			return bestValFromD(var, D);
		}
		else // VAL_INORDER
			return nextValFromD(var, D);
	}

	protected LinkedList<Character> nextValFromD(int var, LinkedList<Character>[][] D) {
		int row = Math.floorDiv(var, 10);
		int col = var % 10;
		return D[row][col];
	}

	private LinkedList<Character> randValFromD(int var, LinkedList<Character>[][] D) {
		LinkedList<Character> list = nextValFromD(var, D);
		Collections.shuffle(list);
		return list;
	}
	class FrequencyComparator implements Comparator<Integer> {

		@Override
		public int compare(Integer o1, Integer o2) {
			return -numbersCount[o1-1] + numbersCount[o2-1];
		}

	}

	private LinkedList<Character> bestValFromD(int var, LinkedList<Character>[][] D) {
		LinkedList<Character> list = nextValFromD(var, D);
		LinkedList<Integer> intlist= new LinkedList<>();
		for (Character chr: list) {
			intlist.add(Integer.parseInt(String.valueOf(chr)));
		}
		Collections.sort(intlist, new FrequencyComparator());
		LinkedList<Character> chlist =new LinkedList<>();
		for (int i:intlist) {
			chlist.add((char)(i+'0'));
		}
//		System.out.println(chlist);
		return chlist;
	}
}