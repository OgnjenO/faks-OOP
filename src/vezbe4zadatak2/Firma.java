package vezbe4zadatak2;

public class Firma extends Proizvodjac {
	private String PIB;

	public Firma(String naziv, String pIB) {
		super(naziv);
		PIB = pIB;
	}
	
	public float getCena(String p) {
		return super.getCena(p)*1.2f;
	}
	
	@Override
	public String toString() {
		String lines[] = super.toString().split("(?<=\\n)");
		
		String output = lines[0];
		output += "PIB : " + this.PIB + "\n";
		for(int i=1; i<4; i++) output += lines[i];
		
		for(String proizvod:super.getProizvodi()) {
			output += "\t- " + proizvod + " : " + this.getCena(proizvod) + "\n";
		}
		
		output += lines[lines.length-1];
		return output;
	}
}
