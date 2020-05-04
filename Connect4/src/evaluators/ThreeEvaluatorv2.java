package evaluators;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

import game.Board;

public class ThreeEvaluatorv2 extends Evaluator {

	char[][] grid;
	int height = -1, width = -1, lastCol = -1, lastTop = -1;
	private final static double POINTS_FOR_THREE=0.5;

	public ThreeEvaluatorv2(int wonPoints) {
		super(wonPoints);
	}

	@Override
	public int evaluate(Board board, int depth) {
		int value = 0;
		if (board.isWinningPlay()) {
			value = wonPoints - depth;
		} else {
			grid = board.getGrid();
			height = board.getHeight();
			width = board.getWidth();
			lastCol = board.getLastCol();
			lastTop = board.getLastTop();

			value=hasThree(board);
		}
		if (board.getLastPlayerSymbol() == Board.PLAYERS[1])
			return value;
		else
			return -value;
	}

	private int hasThree(Board board) {
		char sym = board.getLastPlayerSymbol();
		int a= checkHorizontal(sym);
		int b= checkVertical(sym);
		int c= checkSlashDiagonal(sym);
		int d = checkBackslashDiagonal(sym);
		return Math.max(a, Math.max(b, Math.max(c, d)));
	}

	private int checkHorizontal(char symbol) {
		int value = 0;
		int[] matches=checkContains(new String(grid[lastTop]), symbol);
		for(int i: matches) {
			if(i>=0) {
				int newVal=(int)(POINTS_FOR_THREE*wonPoints)-1 - checkEmptyCell(lastTop, i) ;
				if(newVal==0)
					newVal=1;
				if(newVal>value)
					value=newVal;
			}
		}
		return value;
	}

	private int checkVertical(char symbol) {
		int value = 0;
		StringBuilder sb = new StringBuilder(height);
		int[][] indexes = new int[height][2];
		for (int h = 0; h < height; h++) {
			sb.append(grid[h][lastCol]);
			indexes[h]= new int[]{h, lastCol};
		}
		int[] matches=checkContains(sb.toString(), symbol);
		for(int i: matches) {
			if(i>=0) {
				int newVal=(int)(POINTS_FOR_THREE*wonPoints)-1 - checkEmptyCell(indexes[i][0], indexes[i][1]) ;
				if(newVal==0)
					newVal=1;
				if(newVal>value)
					value=newVal;
			}
		}
		return value;

	}

	private int checkSlashDiagonal(char symbol) {
		int value = 0;
		StringBuilder sb = new StringBuilder(height);
		int[][] indexes = new int[height][2];
		for (int h = 0; h < height; h++) {
			int w = lastCol + lastTop - h;
			if (0 <= w && w < width) {
				sb.append(grid[h][w]);
				indexes[h]= new int[]{h, w};
			}
		}
		int[] matches=checkContains(sb.toString(), symbol);
		for(int i: matches) {
			if(i>=0) {
				int newVal=(int)(POINTS_FOR_THREE*wonPoints)-1 - checkEmptyCell(indexes[i][0], indexes[i][1]) ;
				if(newVal==0)
					newVal=1;
				if(newVal>value)
					value=newVal;
			}
		}
		return value;
	}

	private int checkBackslashDiagonal(char symbol) {

		int value = 0;
		StringBuilder sb = new StringBuilder(height);
		int[][] indexes = new int[height][2];
		for (int h = 0; h < height; h++) {
			int w = lastCol - lastTop + h;
			if (0 <= w && w < width) {
				sb.append(grid[h][w]);
				indexes[h]= new int[]{h, w};
			}
		}
		int[] matches=checkContains(sb.toString(), symbol);
		for(int i: matches) {
//			System.out.println(i);
			if(i>=0) {
				int newVal=
						(int)(POINTS_FOR_THREE*wonPoints)-1 - 
						checkEmptyCell(indexes[i][0], indexes[i][1]) ;
				if(newVal==0)
					newVal=1;
				if(newVal>value)
					value=newVal;
			}
		}
		return value;
		
	}

	

	private int[] checkContains(String s, char sym){
		int[] values=new int[4];
		String streak = "" + sym + sym + sym + ".";
		values[0]=s.indexOf(streak);
		streak = "." + sym + sym + sym;
		values[1]=s.indexOf(streak);
		streak = "" + sym + "." + sym + sym;
		values[2]=s.indexOf(streak);
		streak = "" + sym + sym + "." + sym;
		values[3]=s.indexOf(streak);
		return values;
	}
	private int checkEmptyCell(int height,int width) {
		int emptyCells = 0;
		for (; height >= 0; height--) {
			if (grid[height][width] == '.') {
				emptyCells++;
			} else
				return emptyCells;
		}
		return emptyCells;
	}

	@Override
	public String toString() {
		return "ThreeEval";
	}

}
