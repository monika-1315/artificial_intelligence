import java.util.ArrayList;

public class Problem {

	private String name;
	private String type;
	private String comment;
	private int dimension;
	/** edgeWeightType=true -EUC_2D, false=GEO */
	private boolean edgeWeightType;
	private Node[] nodeCoord;

	private double[][] evals;

	public void evaluate() {
	
		for (int row = 0; row < dimension; row++) {
			for (int col = 0; col < dimension; col++) {
				if (edgeWeightType) {
					evals[row][col] = calculateEuc(row, col);
				} else
					evals[row][col] = calculateGeo(row, col);
			}
		}
	
	}

	private double calculateGeo(int row, int col) {
		double x1 = nodeCoord[row].x*0.0174532925;//degrees to radians
		double x2 = nodeCoord[col].x*0.0174532925;
		double y1 = nodeCoord[row].y*0.0174532925;
		double y2 = nodeCoord[col].y*0.0174532925;
		
		return Math.acos(Math.cos(x1-x2)*Math.cos(y1-y2));
	}

	private double calculateEuc(int row, int col) {
		double x1 = nodeCoord[row].x;
		double x2 = nodeCoord[col].x;
		double y1 = nodeCoord[row].y;
		double y2 = nodeCoord[col].y;
	
		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}

	public double evalInd(ArrayList<Integer> indiv) {
		double val=0;
		for(int i=0; i<indiv.size()-1;i++) {
			val+=evals[indiv.get(i)][indiv.get(i+1)];
		}
		val +=evals[indiv.get(indiv.size()-1)][indiv.get(0)];
		return val;
	}
	
	public double[][] getEvals(){
		return evals;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getDimension() {
		return dimension;
	}

	public void setDimension(int dimension) {
		this.dimension = dimension;
		this.evals = new double[dimension][dimension];
	}

	public boolean isEdgeWeightType() {
		return edgeWeightType;
	}

	public void setEdgeWeightType(boolean edgeWeightType) {
		this.edgeWeightType = edgeWeightType;
	}

	public Node[] getNodeCoord() {
		return nodeCoord;
	}

	public void setNodeCoord(Node[] nodeCoord) {
		this.nodeCoord = nodeCoord;
	}

	@Override
	public String toString() {
		return "Problem [name=" + name + ", type=" + type + ", comment=" + comment + ", dimension=" + dimension
				+ ", edgeWeightType=" + edgeWeightType +"]";
	}
}
