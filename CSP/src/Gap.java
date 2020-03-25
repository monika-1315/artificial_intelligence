
public class Gap {
	int row0;
	int col0;
	int length;
	boolean isHorizontal;
	
	public Gap(int row0, int col0, int length, boolean isHorizontal) {
		super();
		this.row0 = row0;
		this.col0 = col0;
		this.length = length;
		this.isHorizontal = isHorizontal;
	}
	
	public String getValue(char[][] puzzle) {
//		System.out.println(this.toString());
		char[] value= new char[length];
		for(int i =0; i<length; i++) {
			if(!isHorizontal)
				value[i]=puzzle[row0+i][col0];
			else
				value[i]=puzzle[row0][col0+i];
		}
		return value.toString();
	}

	@Override
	public String toString() {
		return "Gap [row0=" + row0 + ", col0=" + col0 + ", length=" + length + ", isHorizontal=" + isHorizontal + "]";
	}
	

}
