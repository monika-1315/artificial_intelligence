import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GreedyAlgorithm extends Algorithm {

	public GreedyAlgorithm(Problem prob) {
		super(prob);
//		this.startCity = startCity;
		try {
			writer = new BufferedWriter(new FileWriter("GreedyAlg" + prob.getName() + ".csv", true));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public double run() {
		ArrayList<Integer> currInd = new ArrayList<Integer>(prob.getDimension());
		double currEval = 0;
		System.out.println(prob.getDimension());
		for (int startCity = 0; startCity < prob.getDimension(); startCity++) {
			currInd = greedyInd(startCity);
			currEval = prob.evalInd(currInd);
			System.out.println("Greedy: eval: " + currEval + " indiv: " + currInd);
			try {
				writer.append(startCity + ", " + currEval + "\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} // for
		try {
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return currEval;
	}
}
