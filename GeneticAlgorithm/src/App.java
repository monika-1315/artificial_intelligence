import java.io.FileNotFoundException;

public class App {

	public static void main(String[] args) throws FileNotFoundException {
		//Loader load= new Loader("TSP/ali535.tsp");
		Loader load= new Loader("TSP/kroA100.tsp");
		Problem p = load.load();
		System.out.println(p.toString());
		p.evaluate();
		Algorithm alg = new Algorithm(p);
		alg.randomMethod(1000);
		alg.greedyAlg(7);

	}
//2op. selekcji, op. krzy¿owania, op.mutacji
}
