import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public final class Loader {

	private String fileName="";

	public Loader(String fileName) {
		super();
		this.fileName = fileName;
	}
	
	public Problem load() throws FileNotFoundException {
		
		Problem prob = new Problem();
		Scanner scan = new Scanner(new File(fileName));
		
		String line=scan.nextLine();
		String[] words;
		while (line!="EOF" && scan.hasNextLine()) {
			line=line.replace(":", "");
			line=line.replaceAll("\\s+"," ");
			//System.out.println(line);
			words=line.split("\\s");
			
			switch (words[0]) {
				case "NAME": 
					prob.setName(words[1]);
					break;
				case "COMMENT":
					String comm="";
					for (int i=1; i<words.length;i++) {
						comm+=words[i]+" ";
					}
					prob.setComment(comm);
					break;
				case "TYPE":
					prob.setType(words[1]);
					break;
				case "DIMENSION":
					prob.setDimension(Integer.parseInt(words[1]));
					break;
				case "EDGE_WEIGHT_TYPE":
					if (words[1]=="EUC_2D") prob.setEdgeWeightType(true);
					else if (words[1]=="GEO") prob.setEdgeWeightType(false);
					break;
				//case "DISPLAY_DATA_TYPE":
				case "NODE_COORD_SECTION":
					int dim=prob.getDimension();
					Node[] nodes= new Node[dim];
					int nodeNr=1;
					do {
						line=scan.nextLine();
						line=line.replaceAll("\\s+"," ");
						words=line.split("\\s");
						//for(String w:words) System.out.println(w);
						int wi=0;
						while(words[wi].equals(new String("")))
							wi++;
						int idx = Integer.parseInt(words[wi]);
						wi++;
						double x= Double.parseDouble(words[wi]);
						wi++;
						double y= Double.parseDouble(words[wi]);
						nodes[idx-1]=new Node(x,y);
						nodeNr++;
					}while(nodeNr<=dim && scan.hasNextLine());
					prob.setNodeCoord(nodes);
					break;
			}
			line=scan.nextLine();
		}
		return prob;
	}
	
}
