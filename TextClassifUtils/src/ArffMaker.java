import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ArffMaker {

	public static void main(String args[]) throws IOException {
		getTxts();
	}

	private static void getTxts() throws IOException {

		BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream("Data.arff"), StandardCharsets.UTF_8));
		Scanner reader;
		File folder = new File("Data");
		File[] listOfFiles = folder.listFiles();

		int i = 0;
		for (File file : listOfFiles) {
			if (i > 20)
				break;
			if (file.isFile()) {
				reader = new Scanner(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
				while (reader.hasNextLine()) {
					String line = reader.nextLine();
					writer.append(line);
				}

				String className = file.getName().split("_")[0];
//				System.out.println(className);
				writer.append(" " + className + "\n");
				writer.flush();
			}
			i++;
		}
		writer.close();
	}
}
