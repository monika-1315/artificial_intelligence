import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Jolka extends CSP<String> {

	private ArrayList<String> words;
	private ArrayList<Gap> fields;
	//I need to save both board char[][] and fields where words can be inserted as one

	public Jolka(char[][] puzzle, ArrayList<String> words) {
		this.initV = puzzle;
		this.words = words;
		fields = new ArrayList<Gap>();
		lookForFields(true);
		lookForFields(false);
		checkDomains();
	}

	private void checkDomains() {
		D = new LinkedList[1][];//I won't use board like in sudoku, only one array of fields - left like that to keep it more abstract and generic
		D[0] = new LinkedList[fields.size()];
		for (int ix = 0; ix < fields.size(); ix++) {
			D[0][ix] = new LinkedList<String>();
			for (String word : words) {
				if (word.length() == fields.get(ix).length) {
					D[0][ix].add(word);
				}
			}
		}

	}

	private void lookForFields(boolean isHorizontal) {
		int row0 = 0, col0 = 0, length = 0;
		int x1Max, x2Max;
		if (isHorizontal) {
			x1Max = initV.length;
			x2Max = initV[0].length;
		} else {
			x1Max = initV[0].length;
			x2Max = initV.length;
		}

		for (int x1 = 0; x1 < x1Max; x1++) {
			for (int x2 = 0; x2 < x2Max; x2++) {
				char val;
				int row = x1;
				int col = x2;
				if (isHorizontal) {
					row = x1;
					col = x2;
				} else {
					row = x2;
					col = x1;
				}
				val = initV[row][col];
				if (val != '#') {
					if (length == 0) {//mark the beginning
						row0 = row;
						col0 = col;
					}
					length++;
				} else {//the end of word
					if (length > 1) {//we don't check words with length 1
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
	protected boolean checkRestrictions(char[][] V, boolean partial) {
		Set<String> foundWords = new HashSet<String>();
		String word = "";

		for (Integer var : nextVars) {
			word = fields.get(var).getValue(V);
			if (!(partial&&word.contains("_"))) {//accept empty fields while partial checking
				if (!words.contains(word)) {
					return false;
				}
				if (foundWords.contains(word))
					return false;
				else
					foundWords.add(word);
			}
		}
		return true;
	}

	@Override
	protected ArrayList<Integer> emptyVars() {//numbers of fields in a row (numbers to get domains of fields)
		ArrayList<Integer> gaps = new ArrayList<Integer>();
		for (int i = 0; i < fields.size(); i++) {
			gaps.add(i);
		}
		return gaps;
	}

	@Override
	protected void insert(char[][] sol, int field, String v, int lvl) {
		char[] word = v.toCharArray();
		Gap gap = fields.get(field);
		int row, col;
		//check whether inserting word won't cause conflict on the board
		for (int i = 0; i < word.length; i++) {
			if (gap.isHorizontal) {
				row = gap.row0;
				col = gap.col0 + i;
			} else {
				row = gap.row0 + i;
				col = gap.col0;
			}
			if (sol[row][col] != '_' && sol[row][col] != word[i]) {
				returns++;
				return;
			}
		}
		//insert the word
		for (int i = 0; i < word.length; i++) {
			if (gap.isHorizontal) {
				row = gap.row0;
				col = gap.col0 + i;
			} else {
				row = gap.row0 + i;
				col = gap.col0;
			}
			sol[row][col] = word[i];
		}
		if (checkRestrictions(sol, true))//check whether the word is already on the board
			backtracking(lvl + 1, sol);//keep solving
		else {
			returns++;
			return;//
		}

	}

	@Override
	protected LinkedList<String> nextValFromD(int var) {

		return D[0][var];
	}

}
