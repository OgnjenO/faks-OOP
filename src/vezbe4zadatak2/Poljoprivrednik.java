package vezbe4zadatak2;

public class Poljoprivrednik extends Proizvodjac {
	private int brojHektara;

	public Poljoprivrednik(String naziv, int brojHektara) {
		super(naziv);
		this.brojHektara = brojHektara;
	}

	public int getBrojHektara() {
		return brojHektara;
	}

	public void setBrojHektara(int brojHektara) {
		this.brojHektara = brojHektara;
	}

	public float getCena(String p) {
		return super.getCena(p)*(1-brojHektara*0.02f);
	}
	
	@Override
	public String toString() {
		String lines[] = super.toString().split("(?<=\\n)");
		
		String output = "";
		for(int i=0; i<3; i++) output += lines[i];
		output += "Broj hektara : " + this.brojHektara + "\n";
		output += lines[3];
		
		for(String proizvod:super.getProizvodi()) {
			output += "\t- " + proizvod + " : " + this.getCena(proizvod) + "\n";
		}
		
		output += lines[lines.length-1];
		
		return output;
	}
}
