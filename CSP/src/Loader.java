import java.io.File;
import java.io.FileNotFoundException;
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
			return null;
		}
		String line=scan.nextLine();
		String[] words=line.split(";");
		System.out.println("Sudoku nr "+words[0]+" difficulty level: "+words[1]);
//		System.out.println(words[2]);
		return new Sudoku(words[2].toCharArray());
		
	}
}
