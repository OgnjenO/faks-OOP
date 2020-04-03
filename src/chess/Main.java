package chess;

public class Main {

	public static void main(String[] args) throws Exception {
		Table t = new Table();
		Player p;
		p = new Player("Pera Peric");
		t.addPlayer(p);
		p = new Player("Mika Mikic");
		t.addPlayer(p);
		t.startGame();
		// t.makeAMove("A2 A4");
		// 0 1 2 3 4 5 6 7
		// A B C D E F G H
		t.makeAMove("E2 E4");
		t.makeAMove("C7 C5");
		t.makeAMove("G1 F3");
		t.makeAMove("D7 D6");
		t.makeAMove("F1 B5");
		t.makeAMove("C8 E6");
		t.makeAMove("C8 D7");
		t.makeAMove("B5 C4");
		t.makeAMove("B8 C6");
		t.makeAMove("E1 G1");
		t.makeAMove("A7 A5");
		t.makeAMove("D2 D4");
		t.makeAMove("C5 D4");
		t.makeAMove("F3 D4");
		t.makeAMove("A5 A4");
		t.makeAMove("B2 B4");
		t.makeAMove("A4 B3");
		t.makeAMove("C1 E3");
		t.makeAMove("B3 B2");
		t.makeAMove("D1 F3");
		t.makeAMove("B2 A1");
		t.makeAMove("F3 F7");
	}

}
