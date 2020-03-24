import java.io.FileNotFoundException;

public class App {

	public static void main(String[] args) throws FileNotFoundException {

		Loader load1=new Loader("Sudoku.csv");
		Sudoku s1=load1.loadSudoku(2);
		s1.solve();
		Sudoku s2=load1.loadSudoku(42);
				s2.solve();
		Sudoku s3=load1.loadSudoku(46);
				s3.solve();
	}

}
