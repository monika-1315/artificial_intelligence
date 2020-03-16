import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
	private boolean debug=false;

	public GeneticAlgorithm(Problem prob, int populationSize, double crossProb, double mutProb, int genNum) {
		super(prob);
		this.crossProb = crossProb;
		this.mutProb = mutProb;
		this.genNum = genNum;
		popSize = populationSize;
		population = new ArrayList<ArrayList<Integer>>(popSize);
		popEvals = new ArrayList<Double>(popSize);
		try {
			writer = new BufferedWriter(new FileWriter("GeneticAlg.csv", true));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		makeInitPop();// make the first population
		try {
			writer.append("\nTour5 PopSize: "+popSize+", genNum: "+genNum+", crossProb: "+crossProb+", mutProb: "+mutProb+"\n1,");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		evaluatePop();
		if (genNum > 1) {// iterate through next generations
			for (int p = 1; p < genNum; p++) {
				try {
					writer.append(p+1+",");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				population = makeNewPopulation();
				evaluatePop();
			}
		}
		System.out.println("GA: best eval: " + currentBestEval + " individual: " + currentBest);
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**Test method only to show and print to check whether all the operators work as they should*/
	public void testOperators() {
		if (this.prob.getDimension() != 11) {//exemplary individuals are for berlin11
			System.err.println("Test works only for berlin 11");
			return;
		}
		debug=true;
		System.out.println("TEST how operators work:");
		population = new ArrayList<ArrayList<Integer>>(popSize);
		popEvals = new ArrayList<Double>(popSize);
		population.add(new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10)));
		population.add(new ArrayList<Integer>(Arrays.asList(10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)));
		population.add(new ArrayList<Integer>(Arrays.asList(10, 8, 3, 9, 7, 6, 5, 4, 1, 2, 0)));
		population.add(new ArrayList<Integer>(Arrays.asList(10, 1, 6, 0, 2, 7, 8, 9, 4, 5, 3)));//greedy individual
		int tempPopSize=popSize;
		popSize=4;
		System.out.println("Population: "+ population);
		evaluatePop();
		System.out.println("Population evaluations: "+popEvals);
		System.out.println("Best: "+currentBestEval+" Worst: "+popWorst);
		System.out.println("Tournament 4: "+tournament(4)+" , "+tournament(4)+" , "+tournament(4));
		System.out.println("Tournament 1 - random: "+tournament(1)+" , "+tournament(1)+" , "+tournament(1)+" , "+tournament(1));
		System.out.println(roulette());
		System.out.println(roulette());
		System.out.println(roulette());
		System.out.println(roulette());
		System.out.println(roulette());
		
		crossing(tournament(3), tournament(3));
		crossing(tournament(4), tournament(1));
		crossing(tournament(4), tournament(1));
		
		System.out.println(mutate(population.get(0)));
		System.out.println(mutate(population.get(0)));
		System.out.println(mutate(tournament(2)));
		System.out.println(mutate(tournament(2)));
		System.out.println();
		popSize=tempPopSize;
		population = new ArrayList<ArrayList<Integer>>(popSize);
		popEvals = new ArrayList<Double>(popSize);
		debug=false;
	}

	private void makeInitPop() {
		
		for (int i = 0; i < popSize; i++) {
			population.add(this.randomInd());
		}
	}

	private void evaluatePop() {
		popSum = 0;
		popEvals = new ArrayList<Double>(popSize);
		popWorst=0;
		popBest=Double.MAX_VALUE;
		for (int i = 0; i < popSize; i++) {// evaluate every individual from population
			double eval = prob.evalInd(population.get(i));
			popEvals.add(eval);
			popSum += eval;
			if (eval < currentBestEval) {
				currentBestEval = eval;
				currentBest = population.get(i);
			}
			if (eval < popBest) {
				popBest = eval;
			}
			if (eval > popWorst) {
				popWorst = eval;
			}
		}//for
		try {
			writer.append(" "+popBest + ", "+ popSum/popSize + ", "+popWorst+"\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private ArrayList<ArrayList<Integer>> makeNewPopulation() {
		ArrayList<ArrayList<Integer>> newPop = new ArrayList<ArrayList<Integer>>(prob.getDimension());
		while (newPop.size() < popSize) {
			ArrayList<Integer> indiv1 = getParent();
			ArrayList<Integer> indiv2 = getParent();
			if (Math.random() <= crossProb) {
				ArrayList<ArrayList<Integer>> children = crossing(indiv1, indiv2);
				indiv1 = children.get(0);
				indiv2 = children.get(1);
			}
			if (Math.random() <= mutProb) {
				indiv1 = mutate(indiv1);
			}
			newPop.add(indiv1);

			if (Math.random() <= mutProb && newPop.size() < popSize) {
				indiv2 = mutate(indiv2);
				newPop.add(indiv2);
			} else if (newPop.size() < popSize) {
				newPop.add(indiv2);
			}
		}
		return newPop;
	}

	private ArrayList<Integer> mutate(ArrayList<Integer> indiv) {
		ArrayList<Integer> individual=(ArrayList<Integer>) indiv.clone();
		int loc1 = (int) Math.floor(Math.random() * prob.getDimension());
		int loc2 = (int) Math.floor(Math.random() * prob.getDimension());
		while (loc1 == loc2) {
			loc2 = (int) Math.floor(Math.random() * prob.getDimension());
		}
		if (debug)
			System.out.print("Mutate "+indiv+ " swap "+loc1+" and "+loc2+": ");
		Integer temp = individual.get(loc1);
		individual.set(loc1, individual.get(loc2));
		individual.set(loc2, temp);

		return individual;
	}

	private ArrayList<Integer> getParent() {
		 return tournament(5);

		//return roulette();
	}

	private ArrayList<Integer> tournament(int n) {
		ArrayList<Integer> selected = new ArrayList<Integer>();
		selected.add((int) Math.floor(Math.random() * popSize));
		for (int i = 1; i < n; i++) {
			selected.add((int) Math.floor(Math.random() * popSize));
		}
		int bestInd = selected.get(0);
		double bestEval = popEvals.get(bestInd);
		for (int i = 1; i < n; i++) {
			if (popEvals.get(selected.get(i)) < bestEval) {
				bestInd = selected.get(i);
				bestEval = popEvals.get(bestInd);
			}
		}
		return population.get(bestInd);
	}

	private ArrayList<Integer> roulette() {
		double sum = -popSum + popSize * (popWorst + 1);
		double random = Math.random() * sum;
		if(debug) System.out.print("Roulette: random = "+random);
		for (int i = 0; i < popSize; i++) {
			double indivPart=(popWorst - popEvals.get(i) + 1);
			random -= indivPart;
			if(debug) System.out.print(" Individual "+i+" has "+indivPart);
			if (random <= 0) {
				if(debug) System.out.print("\nWins "+i);
				return population.get(i);
				
			}
		}
		return population.get(popSize - 1);
	}

	/** Crossing operator PMX */
	private ArrayList<ArrayList<Integer>> crossing(ArrayList<Integer> parent1, ArrayList<Integer> parent2) {
		if(debug) {
			System.out.println("Crossing\nParent1: "+parent1);
			System.out.println("Parent2: "+parent2);
		}
		ArrayList<ArrayList<Integer>> offspring = new ArrayList<ArrayList<Integer>>(2);
		int cut1 = (int) Math.floor(Math.random() * prob.getDimension());
		int cut2 = (int) Math.floor(Math.random() * prob.getDimension());
		if (cut1 > cut2) {
			int temp = cut1;
			cut1 = cut2;
			cut2 = temp;
		}
		if(debug) System.out.println("cuts: " + cut1 + " " + cut2);
		ArrayList<Integer> child1 = new ArrayList<Integer>(prob.getDimension());
		ArrayList<Integer> child2 = new ArrayList<Integer>(prob.getDimension());
		List<Integer> subsection1 = parent1.subList(cut1, cut2);
		List<Integer> subsection2 = parent2.subList(cut1, cut2);

		for (int i = 0; i < prob.getDimension(); i++) {
			if (i >= cut1 && i < cut2) {
				child1.add(subsection2.get(i - cut1));
				child2.add(subsection1.get(i - cut1));
			} else {
				int nextCity = parent1.get(i);
				while (subsection2.contains(nextCity)) {
					nextCity = subsection1.get(subsection2.indexOf(nextCity));
				}
				child1.add(nextCity);

				nextCity = parent2.get(i);
				while (subsection1.contains(nextCity)) {
					nextCity = subsection2.get(subsection1.indexOf(nextCity));
				}
				child2.add(nextCity);
			}
		} // for
		if(debug) {
			System.out.println("Parent1: "+child1);
			System.out.println("Parent2: "+child2);
		}
	
		offspring.add(child1);
		offspring.add(child2);
		return offspring;
	}

}
