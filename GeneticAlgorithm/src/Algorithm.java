import java.util.ArrayList;
import java.util.Collections;

public class Algorithm {
	private Problem prob;

	public Algorithm(Problem prob) {
		super();
		this.prob = prob;
	}

	public ArrayList<Integer> randomInd() {
		ArrayList<Integer> indiv = new ArrayList<Integer>(prob.getDimension());
		for (int i = 0; i < prob.getDimension(); i++) {
			indiv.add(i);
		}
		Collections.shuffle(indiv);
		return indiv;
	}

	public void randomMethod(int cnt) {
		ArrayList<Integer> bestInd = new ArrayList<Integer>(prob.getDimension());
		double bestEval = Integer.MAX_VALUE;
		ArrayList<Integer> currInd;
		double currEval;
		for (int i = 0; i < cnt; i++) {
			currInd = randomInd();
			currEval = prob.evalInd(currInd);
			if (currEval < bestEval) {
				bestEval = currEval;
				bestInd = currInd;
			}
		}
		System.out.println("After random " + cnt + " : best eval: " + bestEval + " indiv: " + bestInd);
	}

	public void greedyAlg(int startCity) {
		ArrayList<Integer> currInd = new ArrayList<Integer>(prob.getDimension());
		
		currInd =greedyInd(startCity);
		double currEval = prob.evalInd(currInd);
		
		System.out.println("Greedy: best eval: " + currEval + " indiv: " + currInd);
	}
	private ArrayList<Integer> greedyInd(int startCity){
		ArrayList<Integer> indiv = new ArrayList<Integer>(prob.getDimension());
		indiv.add(startCity);
		
		for (int i=1; i<prob.getDimension();i++) {
			int nextInd=0;
			double bestDist= Integer.MAX_VALUE;
			for(int y=0; y<prob.getDimension();y++) {
				if(!indiv.contains(y)) {
					if(prob.evals[indiv.get(i-1)][y]<bestDist) {
						bestDist=prob.evals[indiv.get(i-1)][y];
						nextInd=y;
					}
				}
			
			}//for(int y=0; y<prob.getDimension();y++) znajdz nastêpne miasto
			indiv.add(nextInd);
		}//for (int i=1; i<prob.getDimension();i++) ca³y ci¹g
		
		return indiv;
	}
}
