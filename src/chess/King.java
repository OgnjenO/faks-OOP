package chess;

public class King extends Piece {

	public King(String color) {
		super("King", 'X', color);
	}

	@Override
	boolean movePiece(int[] oldPos, int[] newPos) {
		if(this.isCastling(oldPos, newPos)) {
			return true;
		}
		else if((Math.abs(oldPos[1]-newPos[1]) == 0 || Math.abs(oldPos[1]-newPos[1]) == 1) && (Math.abs(oldPos[0]-newPos[0]) == 0 || Math.abs(oldPos[0]-newPos[0]) == 1))
			return true;
		else
			return false;
	}

	public boolean isCastling(int[] oldPos, int[] newPos) {
		if(this.isBigCastling(oldPos, newPos) || this.isSmallCastling(oldPos, newPos)) {
			return true;
		}
		return false;
	}
	
	public boolean isBigCastling(int[] oldPos, int[] newPos) {
		if(((oldPos[0] == 0 || oldPos[0] == 7) && oldPos[1] == 4) && oldPos[0] == newPos[0] && newPos[1] == 2) {
			return true;
		}
		return false;
	}
	
	public boolean isSmallCastling(int[] oldPos, int[] newPos) {
		if(((oldPos[0] == 0 || oldPos[0] == 7) && oldPos[1] == 4) && oldPos[0] == newPos[0] && newPos[1] == 6) {
			return true;
		}
		return false;
	}
}
