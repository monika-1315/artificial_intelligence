import java.util.ArrayList;

public class GeneticAlgorithm extends Algorithm {

	private ArrayList<ArrayList<Integer>> population;
	private ArrayList<Double> popEvals;
	private ArrayList<Integer> currentBest = new ArrayList<Integer>(prob.getDimension());
	
	public GeneticAlgorithm(Problem prob) {
		super(prob);
	}

}
