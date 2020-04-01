package chess;

public class Main {

	public static void main(String[] args) throws Exception {
		Table t = new Table();
		Player p;
		p = new Player("Pera Peric");
		t.addPlayer(p);
		p = new Player("Mika Mikic");
		t.addPlayer(p);
		t.setupGame();
		t.displayInfo();
	}

}
