import java.util.ArrayList;
import java.util.LinkedList;

public class Jolka extends CSP<String> {
	
	private ArrayList<String> words;
	private ArrayList<Gap> fields;
	public Jolka(char[][] puzzle, ArrayList<String> words) {
		this.initV=puzzle;
		this.words=words;
		fields=new ArrayList<Gap>();
		int row0=0, col0=0, length=0;
		//check for horizontal fields
		for(int row=0; row<puzzle.length;row++) {
			for(int col=0; col<puzzle[0].length; col++) {
				if(puzzle[row][col]!='#') {
					if(length==0) {
						row0=row;
						col0=col;
					}
					length++;
				} else {
					if (length!=0) {
						fields.add(new Gap(row0,col0, length,true));
						length=0;
					}
				}
			}//for col
			if (length!=0) {
				fields.add(new Gap(row0,col0, length,true));
				length=0;
			}
		}//for row
		
		//check for vertical fields
		System.out.println(fields);
		D = new LinkedList[1][1];
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
