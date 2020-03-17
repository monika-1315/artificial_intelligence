import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GreedyAlgorithm extends Algorithm {

	int startCity;
	
	public GreedyAlgorithm(Problem prob, int startCity) {
		super(prob);
		this.startCity = startCity;
		try {
			writer = new BufferedWriter(new FileWriter("GreedyAlg.csv", true));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public double run() {
		ArrayList<Integer> currInd = new ArrayList<Integer>(prob.getDimension());
		
		currInd =greedyInd(startCity);
		double currEval = prob.evalInd(currInd);
		
		System.out.println("Greedy: eval: " + currEval + " indiv: " + currInd);
		try {
			writer.append(startCity+", "+currEval+"\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return currEval;
	}
}
