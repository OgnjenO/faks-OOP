package chess;

public class Pawn extends Piece {

	public Pawn(String color) {
		super("Rook", 'R', color);
	}

	@Override
	boolean movePeace(int[] oldPos, int[] newPos) {
		if((this.getColor() == "White" && newPos[1]-oldPos[1] == 1) || (this.getColor() == "Black" && newPos[1]-oldPos[1] == -1))
			return true;
		else
			return false;
	}

	
}
