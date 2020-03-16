import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Collections;

public abstract class Algorithm {
	protected Problem prob;
	protected BufferedWriter writer;

	public Algorithm(Problem prob) {
		super();
		this.prob = prob;
	}

	public abstract void run();
	
	protected ArrayList<Integer> randomInd() {
		ArrayList<Integer> indiv = new ArrayList<Integer>(prob.getDimension());
		for (int i = 0; i < prob.getDimension(); i++) {
			indiv.add(i);
		}
		Collections.shuffle(indiv);
		return indiv;
	}

	protected ArrayList<Integer> greedyInd(int startCity){
		ArrayList<Integer> indiv = new ArrayList<Integer>(prob.getDimension());
		indiv.add(startCity);
		
		for (int i=1; i<prob.getDimension();i++) {
			int nextInd=0;
			double bestDist= Integer.MAX_VALUE;
			for(int y=0; y<prob.getDimension();y++) {
				if(!indiv.contains(y)) {
					if(prob.getEvals()[indiv.get(i-1)][y]<bestDist) {
						bestDist=prob.getEvals()[indiv.get(i-1)][y];
						nextInd=y;
					}
				}
			
			}//for(int y=0; y<prob.getDimension();y++) znajdz nastêpne miasto
			indiv.add(nextInd);
		}//for (int i=1; i<prob.getDimension();i++) ca³y ci¹g
		
		return indiv;
	}
}
