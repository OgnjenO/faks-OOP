package vezbe4zadatak2;

import java.util.HashMap;
import java.util.Set;

public class Proizvodjac {
	private String naziv;
	private String adresa;
	private HashMap<String, Float> proizvodi = new HashMap<String, Float>();
	
	public Proizvodjac(String naziv) {
		super();
		this.naziv = naziv;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}
	
	public float getCena(String p) {
		return proizvodi.get(p);
	}
	
	public Set<String> getProizvodi() {
		return proizvodi.keySet();
	}
	
	public int countProizvodi() {
		return proizvodi.size();
	}
	
	public void removeProizvod(String p) {
		proizvodi.remove(p);
	}
	
	public void addProizvod(String p, float c) {
		proizvodi.put(p, c);
	}

	@Override
	public String toString() {
		String output;
		output = "===========================================================\n";
		output += "Naziv : " + naziv + "\n";
		output += "Adresa : " + adresa + "\n";
		output += "Proizvodi : \n";
		for(String key:proizvodi.keySet()) {
			output += "\t- " + key + " : " + proizvodi.get(key) + "\n";
		}
		output += "===========================================================";
		return output;
	}
}
