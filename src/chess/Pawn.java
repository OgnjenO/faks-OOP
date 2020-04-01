package chess;

public class Pawn extends Piece {

	public Pawn(String color) {
		super("Pawn", 'P', color);
	}

	@Override
	boolean movePiece(int[] oldPos, int[] newPos) {
		if((this.getColor() == "White" && oldPos[0] == 1 && newPos[0]-oldPos[0] == 2) || (this.getColor() == "Black" && oldPos[0] == 6 && newPos[0]-oldPos[0] == -2))
			return true;
		else if((this.getColor() == "White" && newPos[0]-oldPos[0] == 1) || (this.getColor() == "Black" && newPos[0]-oldPos[0] == -1))
			return true;
		else
			return false;
	}

	
}
