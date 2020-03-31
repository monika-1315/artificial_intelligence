import java.io.FileNotFoundException;

public class App {

	public static void main(String[] args) throws FileNotFoundException {

		Loader load1 = new Loader("Sudoku.csv");
		Sudoku s1 = load1.loadSudoku(4);
		s1.solve();
		load1.loadSudoku(41).solve();
		Jolka j0 = Loader.loadJolka("Jolka/puzzle1", "Jolka/words1");
		System.out.println("Jolka");
		j0.solve();
	}

}
