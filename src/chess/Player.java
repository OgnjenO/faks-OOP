package chess;

public class Player {
	private String name;
	private String color;
	private boolean[][] canMove = new boolean[8][8];
	
	public Player(String name) {
		super();
		this.name = name;
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
	
	public void setupPlayer() {
		for(int i=0; i<8; i++)
			for(int j=0; j<8; j++)
				canMove[i][j] = true;
	}

	@Override
	public String toString() {
		String output;
		output = "Name : " + name + "\n";
		output = "Color : " + color + "\n";
		return output;
	}
}
