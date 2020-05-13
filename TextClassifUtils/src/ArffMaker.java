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
import java.util.HashSet;
import java.util.Scanner;

public class ArffMaker {

	public static void main(String args[]) throws IOException {
		getTxts();
	}

	private static void getTxts() throws IOException {

		Scanner reader;
		File folder = new File("Data");
		File[] listOfFiles = folder.listFiles();
		HashSet<String> classes = new HashSet<String>();
		BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream("Data.arff"), StandardCharsets.UTF_8));
		writer.append("@relation wikidata\n" + "@attribute text string\n@attribute class {");// string\n\n@data\n");
		BufferedWriter writer2 = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream("Data_tune.arff"), StandardCharsets.UTF_8));
		writer2.append("@relation wikidata\n" + "@attribute text string\n@attribute class {");
		for (File file : listOfFiles) {
			if (file.isFile()) {
				String className = file.getName().split("_")[0];
				classes.add(className);
			}
		}
		String allClasess = classes.toString();
		writer.append(allClasess.substring(1, allClasess.length() - 1) + "}\n\n@data\n");
		writer2.append(allClasess.substring(1, allClasess.length() - 1) + "}\n\n@data\n");

		int i = 0;
		for (File file : listOfFiles) {
			if (i % 10 != 0) {
				if (file.isFile()) {
					reader = new Scanner(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
					writer.append("\"");
					while (reader.hasNextLine()) {
						String line = reader.nextLine();
						String clnLine = line.replaceAll("'", "").replaceAll("\"", "").replaceAll("[-+&*/^]*", "");
						writer.append(" " + clnLine);
					}

					String className = file.getName().split("_")[0];
//				System.out.println(className);
					writer.append("\" " + className + "\n");
					writer.flush();
				}
			} else {
				if (file.isFile()) {
					reader = new Scanner(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
					writer2.append("\"");
					while (reader.hasNextLine()) {
						String line = reader.nextLine();
						String clnLine = line.replaceAll("'", "").replaceAll("\"", "").replaceAll("[-+&*/^]*", "");
						writer2.append(" " + clnLine);
					}

					String className = file.getName().split("_")[0];
//				System.out.println(className);
					writer2.append("\" " + className + "\n");
					writer2.flush();
				}
			}
			i++;
		}
		writer.close();
		writer2.close();
////		System.out.println(i);
	}
}
