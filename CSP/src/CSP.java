import java.util.ArrayList;
import java.util.LinkedList;

public abstract class CSP<E> {

	protected char[][] initV;
	protected LinkedList<E>[][] D;
	protected long t0;
	protected int nodes;
	protected int returns;
	protected ArrayList<Integer> variables;
	protected LinkedList<char[][]> solutions;
	
	protected abstract boolean checkRestrictions(char[][] V);
	public abstract LinkedList<char[][]> solve();
	protected ArrayList<Integer> getVars() {
		return emptyVars();
	}
	protected abstract ArrayList<Integer> emptyVars();
	
	protected LinkedList<char[][]> backtracking() {
		solutions = new LinkedList<char[][]>();
		t0 = java.lang.System.currentTimeMillis();
		nodes = 0;
		returns = 0;
		variables = getVars();

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

	protected abstract void backtracking(int lvl, char[][] vals);
}
