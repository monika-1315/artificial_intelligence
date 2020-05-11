import java.io.File;

public class ArffMaker {

	public static void main (String args[]) {
		getTxts();
	}

	private static void getTxts() {

		File folder = new File("Data");
		File[] listOfFiles = folder.listFiles();

		for (File file : listOfFiles) {
			if (file.isFile()) {
				System.out.println(file.getName());
			}
		}
	}
}
