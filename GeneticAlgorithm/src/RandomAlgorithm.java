import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class RandomAlgorithm extends Algorithm {
	private int cnt;

	public RandomAlgorithm(Problem prob, int cnt) {
		super(prob);
		this.cnt=cnt;
		try {
			writer = new BufferedWriter(new FileWriter("RandomAlg.csv"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		ArrayList<Integer> bestInd = new ArrayList<Integer>(prob.getDimension());
		double bestEval = Integer.MAX_VALUE;
		ArrayList<Integer> currInd;
		double currEval;
		for (int i = 0; i < cnt; i++) {
			currInd = randomInd();
			currEval = prob.evalInd(currInd);
			try {
				writer.append(currEval+"\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (currEval < bestEval) {
				bestEval = currEval;
				bestInd = currInd;
			}
		}
		System.out.println("Random " + cnt + ": best eval: " + bestEval + " indiv: " + bestInd);
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
