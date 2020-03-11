import java.util.ArrayList;
import java.util.List;

public class GeneticAlgorithm extends Algorithm {

	private int popSize;
	private double crossProb;
	private double mutProb;
	private int genNum;
	private ArrayList<ArrayList<Integer>> population;
	private ArrayList<Double> popEvals;
	private ArrayList<Integer> currentBest = new ArrayList<Integer>(prob.getDimension());
	private double currentBestEval = Double.MAX_VALUE;

	public GeneticAlgorithm(Problem prob, int populationSize, double crossProb, double mutProb, int genNum) {
		super(prob);
		this.crossProb = crossProb;
		this.mutProb = mutProb;
		this.genNum=genNum;
		popSize = populationSize;
		population = new ArrayList<ArrayList<Integer>>(popSize);
		popEvals = new ArrayList<Double>(popSize);
	}

	public void run() {
		makeInitPop();//make the first population
		evaluatePop();
		if(genNum>1) {//iterate through next generations
			for(int p=1; p<genNum;p++) {
				population=makeNewPopulation();
				evaluatePop();
			}
		}
	}

	private void makeInitPop() {
		for (int i = 0; i < popSize; i++) {
			population.add(this.randomInd());
		}
	}

	private void evaluatePop() {
		for (int i = 0; i < popSize; i++) {//evaluate every individual from population
			double eval=prob.evalInd(population.get(i));
			popEvals.add(eval);
			if (eval<currentBestEval) {
				currentBestEval=eval;
				currentBest=population.get(i);
			}
		}
	}

	private ArrayList<ArrayList<Integer>> makeNewPopulation() {
		ArrayList<ArrayList<Integer>> newPop=new ArrayList<ArrayList<Integer>>(prob.getDimension());
		while(newPop.size()<popSize) {
			ArrayList<Integer> indiv1 = getParent();
			ArrayList<Integer> indiv2 = getParent();
			if(Math.random()<=crossProb) {
				 ArrayList<ArrayList<Integer>> children = crossing(indiv1, indiv2);
				 indiv1=children.get(0);
				 indiv2=children.get(1);
			}
			if(Math.random()<=mutProb) {
				indiv1=mutate(indiv1);
			}
			if(Math.random()<=mutProb &&newPop.size()<popSize-1) {
				indiv2=mutate(indiv2);
			}
			newPop.add(indiv1);
			if(newPop.size()<popSize) {
				newPop.add(indiv2);
			}
		}
		return newPop;
	}

	private ArrayList<Integer> mutate(ArrayList<Integer> indiv) {
		int loc1 =(int) Math.floor(Math.random() * prob.getDimension());
		int loc2 =(int) Math.floor(Math.random() * prob.getDimension());
		while (loc1==loc2) {
			loc2 =(int) Math.floor(Math.random() * prob.getDimension());
		}
		Integer temp = indiv.get(loc1);
		indiv.set(loc1, indiv.get(loc2));
		indiv.set(loc2, temp);
		
		return indiv;
	}

	private ArrayList<Integer> getParent() {
		// TODO Auto-generated method stub
		return null;
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
		System.out.println("cuts: " + cut1 + " " + cut2);
		ArrayList<Integer> child1 = new ArrayList<Integer>(prob.getDimension());
		ArrayList<Integer> child2 = new ArrayList<Integer>(prob.getDimension());
		List<Integer> subsection1 = parent1.subList(cut1, cut2);
		List<Integer> subsection2 = parent2.subList(cut1, cut2);

		for (int i = 0; i < prob.getDimension(); i++) {
			if (i >= cut1 && i < cut2) {
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
		} // for
		System.out.println(child1);
		System.out.println(child2);
		offspring.add(child1);
		offspring.add(child2);
		return offspring;
	}

}
