import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class App {

	public static void main(String[] args) throws IOException {
//		Loader load= new Loader("TSP/berlin11_modified.tsp");
//		 load= new Loader("TSP/ali535.tsp");
//		 load= new Loader("TSP/berlin52.tsp");
//		 load= new Loader("TSP/fl417.tsp");
//		 load= new Loader("TSP/gr666.tsp");
//		 load= new Loader("TSP/nrw1379.tsp");
//		 load= new Loader("TSP/pr2392.tsp");
		Loader load1 = new Loader("TSP/kroA100.tsp");
		Loader load2 = new Loader("TSP/kroA150.tsp");
		Loader load3 = new Loader("TSP/kroA200.tsp");
		Problem p1 = load1.load();
		Problem p2 = load2.load();
		Problem p3 = load3.load();
//		System.out.println(p.toString());
		BufferedWriter writer = new BufferedWriter(new FileWriter("GeneticAlg.csv", true));

		GeneticAlgorithm galg1 = new GeneticAlgorithm(p1, 60, 0.7, 0.1, 100);
		GeneticAlgorithm galg2 = new GeneticAlgorithm(p2, 60, 0.7, 0.1, 100);
		GeneticAlgorithm galg3 = new GeneticAlgorithm(p3, 60, 0.7, 0.1, 100);

		for (int i = 0; i < 10; i++) {
			writer.append(galg1.run() + ", ");
			writer.append(galg2.run() + ", ");
			writer.append(galg3.run() + "\n");
		}
		writer.close();

//		Algorithm alg = new RandomAlgorithm(p, 10000);
//		alg.run();
//		alg = new GreedyAlgorithm(p, 7);
//		alg.run();
	}
}
