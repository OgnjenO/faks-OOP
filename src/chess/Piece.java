package chess;

public abstract class Piece {
	private String name;
	private char mark;
	private String color;
	
	public Piece(String name, char mark, String color) {
		super();
		this.name = name;
		this.mark = mark;
		this.color = color;
	}

	abstract boolean movePeace(int[] oldPos, int[] newPos);

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public char getMark() {
		return mark;
	}

	public void setMark(char mark) {
		this.mark = mark;
	}
	
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public boolean checkDiagonal(int[] oldPos, int[] newPos) {
		if(Math.abs(oldPos[0]-newPos[0]) == Math.abs(oldPos[1]-newPos[1]))
			return true;
		else
			return false;
	}
	
	public boolean checkStraight(int[] oldPos, int[] newPos) {
		if((oldPos[0] == newPos[0] || oldPos[1] == newPos[1]))
			return true;
		else
			return false;
	}
	
	public int absDiff(int x, int y) {
		return Math.abs(x-y);
	}

	@Override
	public String toString() {
		return "Piece " + name + "[" + mark + "]";
	}
}