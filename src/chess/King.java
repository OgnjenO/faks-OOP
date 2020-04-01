package chess;

public class King extends Piece {

	public King(String color) {
		super("King", 'X', color);
	}

	@Override
	boolean movePiece(int[] oldPos, int[] newPos) {
		if((Math.abs(oldPos[0]-newPos[0]) == 0 || Math.abs(oldPos[0]-newPos[0]) == 1) && (Math.abs(oldPos[1]-newPos[1]) == 0 || Math.abs(oldPos[1]-newPos[1]) == 1))
			return true;
		else
			return false;
	}

	
}
