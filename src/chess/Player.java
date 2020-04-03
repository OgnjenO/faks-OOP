package chess;

public class Player {
	private String name;
	private String color;
	private boolean[][] canMove = new boolean[8][8];
	private int[] kingPos = new int[2];
	
	public Player(String name) {
		super();
		this.name = name;
		this.kingPos[0] = 0;
		this.kingPos[1] = 1;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	public void setKingPos(int[] kingPos) {
		this.kingPos = kingPos;
	}
	
	public void setKingPos(int index, int value) {
		this.kingPos[index] = value;
	}
	
	public int[] getKingPos() {
		return this.kingPos;
	}
	
	public int getKingPos(int index) {
		return this.kingPos[index];
	}
	
	public void setupPlayer() {
		for(int i=0; i<8; i++)
			for(int j=0; j<8; j++)
				canMove[i][j] = true;
		if(this.color == "White") {
			for(int i=0; i<8; i++) {
				canMove[i][2] = false;
			}
		}
		else {
			for(int i=0; i<8; i++) {
				canMove[i][5] = false;
			}
		}
	}

	@Override
	public String toString() {
		String output;
		output = "Name : " + name + "\n";
		output += "Color : " + color;
		return output;
	}
}
