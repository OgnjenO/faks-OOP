package chess;

public class Shadow extends Piece {
	// Piece for En passant
	private int[] pos = new int[2];
	private int timer;

	public Shadow() {
		super("Shadow", ' ', "None");
		this.pos[0] = 5;
		this.pos[1] = 7;
		this.timer = -1;
	}
	
	public int getPos(int index) {
		return pos[index];
	}
	
	public void setPos(int index, int value) {
		this.pos[index] = value;
	}
	
	public void setPos(int[] pos) {
		this.pos = pos;
	}
	
	public int[] getPos() {
		return this.pos;
	}
	
	public void resetTimer() {
		this.timer = 0;
	}
	
	public void incTimer() {
		this.timer++;
	}
	
	public int getTimer() {
		return this.timer;
	}
	
	public void setTimer(int timer) {
		this.timer = timer;
	}

	@Override
	boolean movePiece(int[] oldPos, int[] newPos) {
		return false;
	}
}
