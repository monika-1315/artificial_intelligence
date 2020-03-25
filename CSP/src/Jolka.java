import java.util.ArrayList;
import java.util.LinkedList;

public class Jolka extends CSP<String> {

	private ArrayList<String> words;
	private ArrayList<Gap>[] fields;

	public Jolka(char[][] puzzle, ArrayList<String> words) {
		this.initV = puzzle;
		this.words = words;
		fields = new ArrayList[2];
		lookForFields(true);
		lookForFields(false);
		System.out.println(fields[0]);
		System.out.println(fields[1]);
		checkDomains();
	}

	private void checkDomains() {
		D=new LinkedList[2][];
		D[0]=new LinkedList[fields[0].size()];
		D[1]=new LinkedList[fields[1].size()];
		for (int ix=0; ix<2; ix++) {
			for(int iy=0; iy<D[ix].length;iy++) {
				D[ix][iy]=new LinkedList<String>();
				for (String word: words) {
					if(word.length()==fields[ix].get(iy).length) {
						D[ix][iy].add(word);
					}
				}
				System.out.println(ix+" "+iy+D[ix][iy]);
			}
		}//both hor and ver
				
	}

	private void lookForFields(boolean isHorizontal) {
		int row0 = 0, col0 = 0, length = 0;
		int x1Max, x2Max;
		if(isHorizontal) {
			x1Max=initV.length;
			x2Max=initV[0].length;
			fields[1]=new ArrayList<Gap>();
		}
		else {
			x1Max=initV[0].length;
			x2Max=initV.length;
			fields[0]=new ArrayList<Gap>();
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
						if(isHorizontal)
							fields[1].add(new Gap(row0, col0, length, isHorizontal));
						else
							fields[0].add(new Gap(row0, col0, length, isHorizontal));
					}
					length = 0;
				}
			} // for col
			if (length > 1) {
				if(isHorizontal)
					fields[1].add(new Gap(row0, col0, length, isHorizontal));
				else
					fields[0].add(new Gap(row0, col0, length, isHorizontal));
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
