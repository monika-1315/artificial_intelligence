import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Loader {
	File file;

	public Loader(String filename) {
		super();
		this.file = new File(filename);
	}

	public Sudoku loadSudoku(int nr) throws FileNotFoundException {
		Scanner scan = new Scanner(file);
		int scannedLine=0;
		do{
			scan.nextLine();
			scannedLine++;
		} while (scannedLine<nr && scan.hasNext());
		if(!scan.hasNext()) {
			System.err.println("There's no such sudoku! Too big number");
			scan.close();
			return null;
		}
		String line=scan.nextLine();
		scan.close();
		String[] words=line.split(";");
		System.out.println("Sudoku nr "+words[0]+" difficulty level: "+words[1]);
//		System.out.println(words[2]);
		return new Sudoku(words[2].toCharArray());
		
	}
	
	public static Jolka loadJolka(String puzzleFile, String wordsFile) throws FileNotFoundException {
		Scanner scanP = new Scanner(new File(puzzleFile));
		ArrayList<char[]>puzzle = new ArrayList<char[]>();
		while (scanP.hasNext()) {
			puzzle.add(scanP.nextLine().toCharArray());
		}
		scanP.close();
		Scanner scanW=new Scanner(new File(wordsFile));
		ArrayList<String> words= new ArrayList<String>();
		while (scanP.hasNext()) {
			puzzle.add(scanP.nextLine().toCharArray());
		}
		return new Jolka(puzzle, words);
	}
}
