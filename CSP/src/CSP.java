import java.util.ArrayList;
import java.util.LinkedList;

public abstract class CSP {


	protected LinkedList<Character>[][] D;
	protected abstract boolean checkRestrictions(char[][] V);
	public abstract LinkedList<char[][]> solve();
}
