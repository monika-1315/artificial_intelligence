import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

public class App {

	public static void main(String[] args) throws FileNotFoundException {
		Loader load= new Loader("TSP/berlin11_modified.tsp");
		
		Problem p = load.load();
		System.out.println(p.toString());
		GeneticAlgorithm galg= new GeneticAlgorithm(p, 10);
		galg.crossing(new ArrayList<Integer>(Arrays.asList(0,1,2,3,4,5,6,7,8,9,10)), new ArrayList<Integer>(Arrays.asList(10,9,8,7,6,5,4,3,2,1,0)));
//		Algorithm alg = new Algorithm(p);
//		alg.randomMethod(10000);
//		alg.greedyAlg(7);
//
//		 load= new Loader("TSP/ali535.tsp");
//		 p = load.load();
//		 System.out.println(p.toString());
//		 alg = new Algorithm(p);
//		 alg.randomMethod(10000);
//		 alg.greedyAlg(7);
//
//		 load= new Loader("TSP/berlin52.tsp");
//		 p = load.load();
//		 System.out.println(p.toString());
//		 alg = new Algorithm(p);
//		 alg.randomMethod(10000);
//		 alg.greedyAlg(7);
//		 
//		 load= new Loader("TSP/fl417.tsp");
//		 p = load.load();
//		 System.out.println(p.toString());
//		 alg = new Algorithm(p);
//		 alg.randomMethod(10000);
//		 alg.greedyAlg(7);
//		 
//		 load= new Loader("TSP/gr666.tsp");
//		 p = load.load();
//		 System.out.println(p.toString());
//		 alg = new Algorithm(p);
//		 alg.randomMethod(10000);
//		 alg.greedyAlg(7);
//		 
//		 load= new Loader("TSP/kroA200.tsp");
//		 p = load.load();
//		 System.out.println(p.toString());
//		 alg = new Algorithm(p);
//		 alg.randomMethod(10000);
//		 alg.greedyAlg(7);
//		 
//		 load= new Loader("TSP/nrw1379.tsp");
//		 p = load.load();
//		 System.out.println(p.toString());
//		 alg = new Algorithm(p);
//		 alg.randomMethod(10000);
//		 alg.greedyAlg(7);
//		 
//		 load= new Loader("TSP/pr2392.tsp");
//		 p = load.load();
//		 System.out.println(p.toString());
//		 alg = new Algorithm(p);
//		 alg.randomMethod(10000);
//		 alg.greedyAlg(7);
		
		
	}
//2op. selekcji, op. krzy¿owania, op.mutacji
}
