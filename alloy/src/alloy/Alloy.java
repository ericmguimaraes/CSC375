package alloy;

public class Alloy {

	public class Matrix implements Cloneable {
		
		Atom[][] atom;
		
		int lenght;

		public Matrix() {
			atom = new Atom[w][];
			for (int i = 0; i < w; i++) {
				atom[i] = new Atom[h];
			}
			lenght = w*h;
			init();
		}

		private void init() {
			for (int i = 0; i < w; i++) {
				for (int j = 0; j < h; j++) {
					atom[i][j] = new Atom(0);
				}
			}
			atom[0][0] = new Atom(s);
			atom[w-1][h-1] = new Atom(t);
		}
		
		private void resetTag() {
			for (int i = 0; i < w; i++) {
				for (int j = 0; j < h; j++) {
					atom[i][j].tag = false;
				}
			}
		}
		
		public Matrix clone() throws CloneNotSupportedException {
			Matrix matrix = new Matrix();
			Atom[][] atom = new Atom[w][];
			for (int i = 0; i < w; i++) {
				atom[i] = new Atom[h];
			}
			for (int i = 0; i < w; i++) {
				for (int j = 0; j < h; j++) {
					atom[i][j] = new Atom(0,this.atom[i][j].metals);
				}
			}
			atom[0][0] = new Atom(s,this.atom[0][0].metals);
			atom[w-1][h-1] = new Atom(t,this.atom[w-1][h-1].metals);
			matrix.atom = atom;
			return matrix;
		}
	}

	int index;
	
	int s, t, w, h;
	float constants[];

	private Matrix[] alloys = new Matrix[2];

	public Alloy(int w, int h,int s,int t,float c1,float c2,float c3) {
		index = 0;
		this.s=s;
		this.t=t;
		constants = new float[3];
		constants[0]=c1;
		constants[1]=c2;
		constants[2]=c3;
		this.w = w;
		this.h = h;
		alloys[0] = new Matrix();
		try {
			alloys[1] = alloys[0].clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getIndexOrigin() {
		return index;
	}

	public int getIndexDestination() {
		if (index == 0)
			return 1;
		return 0;
	}

	public Matrix getOrigin() {
		return alloys[getIndexOrigin()];
	}

	public Matrix getDestination() {
		return alloys[getIndexDestination()];
	}
	
	public Atom getOrigin(int x, int y){
		return getOrigin().atom[x][y];
	}
	
	public Atom getDestination(int x, int y){
		return getDestination().atom[x][y];
	}

	public int flipIndex() {
		if (index == 0) {
			index = 1;
		} else {
			index = 0;
		}
		resetTags();
		return index;
	}

	private void resetTags() {
		alloys[0].resetTag();
		alloys[1].resetTag();
	}
	
	public void print(){
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				System.out.print(" : "+getOrigin(i, j).getTemperature()+" : "); 
			}
			System.out.println("");
		}
		System.out.println("");
		System.out.println("*************************************************");
		System.out.println("");
	}
	
	public boolean converged(){
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				if(getOrigin(i, j).getTemperature()!=getDestination(i, j).getTemperature()){
					return false;
				}
			}
		}
		return true;
	}

}

