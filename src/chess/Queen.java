package chess;

public class Queen extends Piece {

	public Queen(String color) {
		super("Queen", 'Q', color);
	}

	@Override
	boolean movePeace(int[] oldPos, int[] newPos) {
		if(this.checkDiagonal(oldPos, newPos) || this.checkStraight(oldPos, newPos))
			return true;
		else
			return false;
	}

	
}
