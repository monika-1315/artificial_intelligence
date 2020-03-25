import java.util.ArrayList;
import java.util.LinkedList;

public class Jolka extends CSP {
	
	private ArrayList<String> words;
	private ArrayList<char[]>puzzle;
	public Jolka(ArrayList<char[]>puzzle, ArrayList<String> words) {
		this.puzzle=puzzle;
		this.words=words;
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

}
