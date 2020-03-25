import java.util.ArrayList;
import java.util.LinkedList;

public class Jolka extends CSP<String> {

	private ArrayList<String> words;
	private ArrayList<Gap> fields;

	public Jolka(char[][] puzzle, ArrayList<String> words) {
		this.initV = puzzle;
		this.words = words;
		fields = new ArrayList<Gap>();

		lookForFields(true);
		lookForFields(false);
		System.out.println(fields);
		D = new LinkedList[1][1];
	}

	private void lookForFields(boolean isHorizontal) {
		int row0 = 0, col0 = 0, length = 0;
		int x1Max, x2Max;
		if(isHorizontal) {
			x1Max=initV.length;
			x2Max=initV[0].length;
		}
		else {
			x1Max=initV[0].length;
			x2Max=initV.length;
		}
		for (int x1 = 0; x1 < x1Max; x1++) {
			for (int x2 = 0; x2 < x2Max; x2++) {
				char val;
				if(isHorizontal)
					val = initV[x1][x2];
				else
					val = initV[x2][x1];
				if (val != '#') {
					if (length ==0) {
						row0 = x1;
						col0 = x2;
					}
					length++;
				} else {
					if (length > 1) {
						fields.add(new Gap(row0, col0, length, isHorizontal));
					}
					length = 0;
				}
			} // for col
			if (length > 1) {
				fields.add(new Gap(row0, col0, length, isHorizontal));
				length = 0;
			}
		} // for row
	}

	@Override
	protected boolean checkRestrictions(char[][] V) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public LinkedList<char[][]> solve() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ArrayList<Integer> emptyVars() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void backtracking(int lvl, char[][] vals) {
		// TODO Auto-generated method stub

	}

}
