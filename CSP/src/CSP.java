import java.util.ArrayList;
import java.util.LinkedList;

public abstract class CSP<T> {

	protected char[][] initV;
	protected LinkedList<T>[][] D;
	protected long t0;
	protected int nodes;
	protected int returns;
	protected ArrayList<Integer> nextVars;
	protected LinkedList<char[][]> solutions;
	
	protected abstract boolean checkRestrictions(char[][] V);
	public LinkedList<char[][]> solve(){
		return backtracking();
	};
	protected ArrayList<Integer> getVars() {
		return emptyVars();
	}
	protected abstract ArrayList<Integer> emptyVars();
	
	protected LinkedList<char[][]> backtracking() {
		solutions = new LinkedList<char[][]>();
		t0 = java.lang.System.currentTimeMillis();
		nodes = 0;
		returns = 0;
		nextVars = getVars();
//		System.out.println(nextVars);

		backtracking(0, initV);

		System.out.println((java.lang.System.currentTimeMillis() - t0) + " ms. Found " + solutions.size()
				+ " solutions " + nodes + " nodes visited " + returns + " returns");
		for (char[][] sol : solutions) {
			for (int r = 0; r < solutions.getFirst().length; r++) {
				System.out.println(sol[r]);
			}
			System.out.println();
		}
		return solutions;
	}

	protected void backtracking(int lvl, char[][] vals) {
//		for (char[] sol : vals) {
//			System.out.println(sol);
//		}
		if (lvl == nextVars.size()) {
			
			if (checkRestrictions(vals)) {
				
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
		
		char[][] sol;

		for (T v : nextVals(var)) {
			nodes++;
//			System.out.println(v);
			sol = new char[vals.length][vals[0].length];
			for (int chrow = 0; chrow < sol.length; chrow++) {
				sol[chrow] = vals[chrow].clone();
			}

			insert(sol, var, v, lvl);
		}
		returns++;

	}
	
	protected abstract void insert(char[][] sol, int fieldVar, T v, int lvl);
	
	protected LinkedList<T> nextVals(int var) {
		return nextValFromD(var);
	}

	protected abstract LinkedList<T> nextValFromD(int var);
}
