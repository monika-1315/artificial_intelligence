import java.io.FileNotFoundException;

public class App {

	public static void main(String[] args) throws FileNotFoundException {

		Loader load1 = new Loader("Sudoku.csv");
		Sudoku s1 = load1.loadSudoku(2);
		s1.solve();
		s1.forwardChecking();
	}

}
