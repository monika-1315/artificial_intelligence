import java.io.FileNotFoundException;

public class App {

	public static void main(String[] args) throws FileNotFoundException {

		Loader load1 = new Loader("Sudoku.csv");
		Sudoku s1 = load1.loadSudoku(1);
		System.out.println("Backtracking\nForward checking");
		s1.solve(Sudoku.BACKTRACKING, Sudoku.VAL_INORDER, Sudoku.VAR_INORDER);
		s1.solve(Sudoku.FORWARD_CHECKING, Sudoku.VAL_INORDER, Sudoku.VAR_INORDER);
		s1.solve(Sudoku.BACKTRACKING, Sudoku.VAL_RANDOM, Sudoku.VAR_DOMAIN_SORTED);
		s1.solve(Sudoku.FORWARD_CHECKING, Sudoku.VAL_RANDOM, Sudoku.VAR_DOMAIN_SORTED);
//		s1.solve(Sudoku.BACKTRACKING, Sudoku.VAL_RANDOM, Sudoku.VAR_INORDER);
//		s1.solve(Sudoku.FORWARD_CHECKING, Sudoku.VAL_RANDOM, Sudoku.VAR_INORDER);
//		s1.solve(Sudoku.BACKTRACKING, Sudoku.VAL_INORDER, Sudoku.VAR_DOMAIN_SORTED);
//		s1.solve(Sudoku.FORWARD_CHECKING, Sudoku.VAL_INORDER, Sudoku.VAR_DOMAIN_SORTED);
	}

}
