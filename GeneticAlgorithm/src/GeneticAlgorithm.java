import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
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
	private double popSum;
	private double popBest;
	private double popWorst;

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
		System.out.println("GA: best eval: "+ currentBestEval + " individual: "+currentBest);
	}

	private void makeInitPop() {
		//population.add(greedyInd(((int) Math.floor(Math.random() * prob.getDimension()))));
		for (int i = 0; i < popSize; i++) {
			population.add(this.randomInd());
		}
	}

	private void evaluatePop() {
		popSum=0;
		for (int i = 0; i < popSize; i++) {//evaluate every individual from population
			double eval=prob.evalInd(population.get(i));
			popEvals.add(eval);
			popSum+=eval;///needed only with roulette
			if (eval<currentBestEval) {
				currentBestEval=eval;
				currentBest=population.get(i);
			}
			if (eval<popBest) {
				popBest=eval;
			}
			if (eval>popWorst) {
				popWorst=eval;
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
		//return tournament(5);
		
		return roulette();
	}
	
	private ArrayList<Integer> tournament(int n){
		ArrayList<Integer> selected = new ArrayList<Integer>();
		selected.add((int)Math.floor(Math.random() * popSize));
		for (int i=1; i<n;i++) {
			selected.add((int)Math.floor(Math.random() * popSize));
		}
		int bestInd=selected.get(0);
		double bestEval=popEvals.get(bestInd);
		for (int i=1; i<n; i++) {
			if(popEvals.get(selected.get(i))<bestEval){
				bestInd=selected.get(i);
				bestEval=popEvals.get(bestInd);
			}
		}
		return population.get(bestInd);
	}
	
	private ArrayList<Integer> roulette(){
		double sum = popSum+popSize*(popWorst+1);
		double random = Math.random()*sum;
		for (int i=0; i<popSize; i++) {
			random-=(popWorst-popEvals.get(i)+1);
			if(random<=0) {
				return population.get(i);
			}
		}
		return population.get(popSize-1);
	}

	/** Crossing operator PMX */
	private ArrayList<ArrayList<Integer>> crossing(ArrayList<Integer> parent1, ArrayList<Integer> parent2) {
		ArrayList<ArrayList<Integer>> offspring = new ArrayList<ArrayList<Integer>>(2);
		int cut1 = (int) Math.floor(Math.random() * prob.getDimension());
		int cut2 = (int) Math.floor(Math.random() * prob.getDimension());
		if (cut1 > cut2) {
			int temp = cut1;
			cut1 = cut2;
			cut2 = temp;
		}
//		System.out.println("cuts: " + cut1 + " " + cut2);
		ArrayList<Integer> child1 = new ArrayList<Integer>(prob.getDimension());
		ArrayList<Integer> child2 = new ArrayList<Integer>(prob.getDimension());
		List<Integer> subsection1 = parent1.subList(cut1, cut2);
		List<Integer> subsection2 = parent2.subList(cut1, cut2);

		for (int i = 0; i < prob.getDimension(); i++) {
			if (i >= cut1 && i < cut2) {
				child1.add(subsection2.get(i - cut1));
				child2.add(subsection1.get(i - cut1));
			} else {
				int nextCity=parent1.get(i);
				while (subsection2.contains(nextCity)) {
					nextCity=subsection1.get(subsection2.indexOf(nextCity));
				} 
				child1.add(nextCity);

				nextCity=parent2.get(i);
				while (subsection1.contains(nextCity)) {
					nextCity=subsection2.get(subsection1.indexOf(nextCity));
				} 
				child2.add(nextCity);
			}
		} // for
//		System.out.println(child1);
//		System.out.println(child2);
		offspring.add(child1);
		offspring.add(child2);
		return offspring;
	}

}
