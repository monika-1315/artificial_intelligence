import java.io.FileNotFoundException;

public class App {

	public static void main(String[] args) throws FileNotFoundException {
		Loader load= new Loader("TSP/ali535.tsp");
//		Loader load= new Loader("TSP/berlin11_modified.tsp");
//		Loader load= new Loader("TSP/berlin52.tsp");
//		Loader load= new Loader("TSP/fl417.tsp");
//		Loader load= new Loader("TSP/gr666.tsp");
//		Loader load= new Loader("TSP/kroA200.tsp");
//		Loader load= new Loader("TSP/nrw1379.tsp");
//		Loader load= new Loader("TSP/pr2392.tsp");
		
		Problem p = load.load();
		System.out.println(p.toString());
		Algorithm alg = new Algorithm(p);
		alg.randomMethod(1000000);
		alg.greedyAlg(7);

	}
//2op. selekcji, op. krzy¿owania, op.mutacji
}
