package chess;

public class Bishop extends Piece {

	public Bishop(String color) {
		super("Bishop", 'B', color);
	}

	@Override
	boolean movePiece(int[] oldPos, int[] newPos) {
		if(this.checkDiagonal(oldPos, newPos))
			return true;
		else
			return false;
	}

	
}
