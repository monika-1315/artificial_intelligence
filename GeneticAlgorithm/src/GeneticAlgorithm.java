import java.util.ArrayList;
import java.util.List;

public class GeneticAlgorithm extends Algorithm {

	private int popSize;
	private ArrayList<ArrayList<Integer>> population;
	private ArrayList<Double> popEvals;
	private ArrayList<Integer> currentBest = new ArrayList<Integer>(prob.getDimension());
	private double currentBestEval = Double.MAX_VALUE;

	public GeneticAlgorithm(Problem prob, int populationSize) {
		super(prob);
		popSize = populationSize;
		population = new ArrayList<ArrayList<Integer>>(popSize);
		popEvals = new ArrayList<Double>(popSize);
	}

	private void makeInitPop() {
		for (int i = 0; i < popSize; i++) {
			population.add(this.randomInd());
		}
	}

	/** Crossing operator PMX */
	public ArrayList<ArrayList<Integer>> crossing(ArrayList<Integer> parent1, ArrayList<Integer> parent2) {
		ArrayList<ArrayList<Integer>> offspring = new ArrayList<ArrayList<Integer>>(2);
		int cut1 = (int) Math.floor(Math.random() * prob.getDimension());
		int cut2 = (int) Math.floor(Math.random() * prob.getDimension());
		if (cut1 > cut2) {
			int temp = cut1;
			cut1 = cut2;
			cut2 = temp;
		}
		System.out.println("cuts: "+ cut1+" "+cut2);
		ArrayList<Integer> child1 = new ArrayList<Integer>(prob.getDimension());
		ArrayList<Integer> child2 = new ArrayList<Integer>(prob.getDimension());
		List<Integer> subsection1 =  parent1.subList(cut1, cut2);
		List<Integer> subsection2 =  parent2.subList(cut1, cut2);

		for (int i = 0; i < prob.getDimension(); i++) {
			if (i >=cut1 && i< cut2) {
				child1.add(subsection2.get(i - cut1));
				child2.add(subsection1.get(i - cut1));
			} else {
				if (!subsection2.contains(parent1.get(i))) {
					child1.add(parent1.get(i));
				} else {
					child1.add(subsection1.get(subsection2.indexOf(parent1.get(i))));
				}
				
				if (!subsection1.contains(parent2.get(i))) {
					child2.add(parent2.get(i));
				} else {
					child2.add(subsection2.get(subsection1.indexOf(parent2.get(i))));
				}

			}
		}//for
		System.out.println(child1);
		System.out.println(child2);
		offspring.add(child1);
		offspring.add(child2);
		return offspring;
	}

}
