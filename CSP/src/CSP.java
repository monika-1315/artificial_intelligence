import java.util.ArrayList;
import java.util.HashSet;

public abstract class CSP {


	protected HashSet[][] D;
	protected abstract boolean checkRestrictions(char[][] V);
	public abstract ArrayList<char[][]> solve();
}
