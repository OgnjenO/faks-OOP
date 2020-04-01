package chess;

public class Knight extends Piece {

	public Knight(String color) {
		super("Knight", 'K', color);
	}

	@Override
	boolean movePeace(int[] oldPos, int[] newPos) {
		if(this.absDiff(oldPos[0], newPos[0]) == 2) {
			if(this.absDiff(oldPos[1], newPos[1]) == 1)
				return true;
		}
		else if(this.absDiff(oldPos[1], newPos[1]) == 2) {
			if(this.absDiff(oldPos[0], newPos[0]) == 1)
				return true;
		}
		
		return false;
	}

	
}
