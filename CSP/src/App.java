import java.io.FileNotFoundException;
import java.util.ArrayList;

public class App {

	public static void main(String[] args) throws FileNotFoundException {

		Loader load1=new Loader("Sudoku.csv");
		Sudoku s1=load1.loadSudoku(2);
		s1.solve();
//		load1.loadSudoku(3).solve();
		Jolka j0= Loader.loadJolka("Jolka/puzzle1", "Jolka/words1");
		char[][] ch1= new char[][]{"boat".toCharArray(),"art#".toCharArray(), "need".toCharArray()};
		
		j0.solve();
//		System.out.println(j0.checkRestrictions(ch1));
	}

}
