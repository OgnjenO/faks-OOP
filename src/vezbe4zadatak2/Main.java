package vezbe4zadatak2;

public class Main {

	public static void main(String[] args) {
		Poljoprivrednik rr = new Poljoprivrednik("Ratomir Rakic", 2);
		Firma f = new Firma("Fiona", "20133");
		
		rr.addProizvod("Grasak", 50);
		rr.addProizvod("Repa", 100);
		
		f.addProizvod("Secer", 400);
		f.addProizvod("Repa", 100);
		
		Proizvodjac[] niz = new Proizvodjac[2];
		niz[0] = rr;
		niz[1] = f;
		
		for(int i=0; i<2; i++) {
			System.out.println(niz[i].toString());
		}
		
		System.out.println("\nRatomirovi proizvodi :");
		for(String proizvod:niz[0].getProizvodi()) {
			System.out.println("\t- " + proizvod + " : " + niz[0].getCena(proizvod));
		}
	}

}
