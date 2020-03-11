import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

public class App {

	public static void main(String[] args) throws FileNotFoundException {
		Loader load= new Loader("TSP/berlin11_modified.tsp");
//		 load= new Loader("TSP/ali535.tsp");
//		 load= new Loader("TSP/berlin52.tsp");
//		 load= new Loader("TSP/fl417.tsp");
//		 load= new Loader("TSP/gr666.tsp");
		load= new Loader("TSP/kroA100.tsp");
//		 load= new Loader("TSP/kroA200.tsp");
//		 load= new Loader("TSP/nrw1379.tsp");
//		 load= new Loader("TSP/pr2392.tsp");
		Problem p = load.load();
		System.out.println(p.toString());
		GeneticAlgorithm galg= new GeneticAlgorithm(p, 1000, 0.5, 0.1,100);
		galg.run();
		Algorithm alg = new RandomAlgorithm(p, 10000);
		alg.run();
//		alg = new GreedyAlgorithm(p, 7);
//		alg.run();
		
	}
}
