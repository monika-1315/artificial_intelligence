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
		BufferedWriter writer = new BufferedWriter(new FileWriter("GeneticAlg.csv"));

		int popSize = 800;
		double crossProb = 0.7;
		double mutProb = 0.1;
		int genNum=1000;
		GeneticAlgorithm galg1 = new GeneticAlgorithm(p1, popSize, crossProb, mutProb, genNum);
		GeneticAlgorithm galg2 = new GeneticAlgorithm(p2, popSize, crossProb, mutProb, genNum);
		GeneticAlgorithm galg3 = new GeneticAlgorithm(p3, popSize, crossProb, mutProb, genNum);
		double b1 = 0;
		double b2 = 0;
		double b3 = 0;
		double s1 = 0;
		double s2 = 0;
		double s3 = 0;
		for (int i = 0; i < 10; i++) {
			b1 = galg1.run();
			s1 += b1;
			writer.append(b1 + ", ");
			b2 = galg2.run();
			s2 += b2;
			writer.append(b2 + ", ");
			b3 = galg3.run();
			s3 += b3;
			writer.append(b3 + ", ");
		}
		writer.close();

		System.out.println(s1 / 10);
		System.out.println(s2 / 10);
		System.out.println(s3 / 10);
//		Algorithm alg = new RandomAlgorithm(p1, 10000);
//		alg.run();
//		alg = new GreedyAlgorithm(p, 7);
//		alg.run();
	}
}
