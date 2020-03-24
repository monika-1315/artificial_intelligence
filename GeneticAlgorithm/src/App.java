import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class App {

	public static void main(String[] args) throws IOException {
		Loader load0= new Loader("TSP/berlin52.tsp");
		Loader load1 = new Loader("TSP/kroA100.tsp");
		Loader load2 = new Loader("TSP/kroA150.tsp");
		Loader load3 = new Loader("TSP/kroA200.tsp");
		Loader load4= new Loader("TSP/fl417.tsp");
		Problem p0 = load0.load();
		Problem p1 = load1.load();
		Problem p2 = load2.load();
		Problem p3 = load3.load();
		Problem p4 = load4.load();
		BufferedWriter writer = new BufferedWriter(new FileWriter("GeneticAlg.csv"));

		int popSize = 800; 
		double crossProb = 0.95;
		double mutProb = 0.4;
		int genNum=800;
		GeneticAlgorithm galg0 = new GeneticAlgorithm(p0, popSize, crossProb, mutProb, genNum);
		GeneticAlgorithm galg1 = new GeneticAlgorithm(p1, popSize, crossProb, mutProb, genNum);
		GeneticAlgorithm galg2 = new GeneticAlgorithm(p2, popSize, crossProb, mutProb, genNum);
		GeneticAlgorithm galg3 = new GeneticAlgorithm(p3, popSize, crossProb, mutProb, genNum);
		GeneticAlgorithm galg4 = new GeneticAlgorithm(p4, popSize, crossProb, mutProb, genNum);
		
		double b0 = 0;
		double b1 = 0;
		double b2 = 0;
		double b3 = 0;
		double b4 = 0;
		double s0 = 0;
		double s1 = 0;
		double s2 = 0;
		double s3 = 0;
		double s4 = 0;
		//running AG 10 times
		int iters=10;
		for (int i = 0; i < iters; i++) {
			b0 = galg0.run(); 
			s0 += b0;
			writer.append(b0 + ", ");
			b1 = galg1.run();
			s1 += b1;
			writer.append(b1 + ", ");
			b2 = galg2.run();
			s2 += b2;
			writer.append(b2 + ", ");
			b3 = galg3.run();
			s3 += b3;
			writer.append(b3 + ", ");
			b4 = galg4.run();
			s4 += b4;
			writer.append(b4 + "\n");
		}
		writer.close();

		//showing avg
		System.out.println(s0 / iters);
		System.out.println(s1 / iters);
		System.out.println(s2 / iters);
		System.out.println(s3 / iters);
		System.out.println(s4 / iters);
		
		//checking random and greedy
		new RandomAlgorithm(p0, 640000).run();
		new GreedyAlgorithm(p0).run();
		new RandomAlgorithm(p1, 640000).run();
		new GreedyAlgorithm(p1).run();
		new RandomAlgorithm(p2, 640000).run();
		new GreedyAlgorithm(p2).run();
		new RandomAlgorithm(p3, 640000).run();
		new GreedyAlgorithm(p3).run();
		new RandomAlgorithm(p4, 640000).run();
		new GreedyAlgorithm(p4).run();
	}
}
