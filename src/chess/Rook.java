package chess;

public class Rook extends Piece {

	public Rook(String color) {
		super("Rook", 'R', color);
	}

	@Override
	boolean movePeace(int[] oldPos, int[] newPos) {
		if(this.checkStraight(oldPos, newPos))
			return true;
		else
			return false;
	}

	
}
