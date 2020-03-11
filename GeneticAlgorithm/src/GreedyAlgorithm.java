import java.util.ArrayList;

public class GreedyAlgorithm extends Algorithm {

	int startCity;
	
	public GreedyAlgorithm(Problem prob, int startCity) {
		super(prob);
		this.startCity = startCity;
	}

	public void run() {
		ArrayList<Integer> currInd = new ArrayList<Integer>(prob.getDimension());
		
		currInd =greedyInd(startCity);
		double currEval = prob.evalInd(currInd);
		
		System.out.println("Greedy: eval: " + currEval + " indiv: " + currInd);
	}
}
