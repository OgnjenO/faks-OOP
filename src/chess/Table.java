package chess;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Table {
	private Player[] players = new Player[2];
	private Piece[][] table = new Piece[8][8];
	private int turn;
	private Scanner sc;
	
	public Table() {
		this.setupTable();
	}

	public Table(Player[] players) {
		this.setupTable();
		this.players = players;
	}

	public Table(Player player) {
		this.setupTable();
		this.players[0] = player;
	}

	public Table(Player player1, Player player2) {
		this.setupTable();
		this.players[0] = player1;
		this.players[1] = player2;
	}

	public Player[] getPlayers() {
		return players;
	}

	public void setPlayers(Player[] players) {
		this.players = players;
	}
	
	public void setPlayers(Player player1, Player player2) {
		this.players[0] = player1;
		this.players[1] = player2;
	}
	
	public boolean addPlayer(Player player) throws Exception {
		if(this.players[0] == null) 
			this.players[0] = player;
		else if(this.players[1] == null) 
			this.players[1] = player;
		else {
			System.out.println("There are already 2 players on this table");
			return false;
		}
		return true;
	}
	
	public boolean removePlayer(int index) throws Exception {
		if(index != 0 && index != 1) {
			System.out.println("Index must be 0 or 1 !");
			return false;
		}
		
		if(this.players[index] == null) {
			System.out.println("There is no player at index : " + index);
			return false;
		}
		
		if(index == 1) this.players[index] = null;
		else {
			this.players[0] = this.players[1];
			this.players[1] = null;
		}
		return true;
	}
	
	public void setupTable() {
		sc = new Scanner(System.in);
		this.turn = -1;
		for(int i=0; i<8; i++) {
			this.table[i][1] = new Pawn("White");
			this.table[i][6] = new Pawn("Black");
		}
		this.table[0][0] = new Rook("White");
		this.table[7][0] = new Rook("White");
		this.table[1][0] = new Knight("White");
		this.table[6][0] = new Knight("White");
		this.table[2][0] = new Bishop("White");
		this.table[5][0] = new Bishop("White");
		this.table[3][0] = new Queen("White");
		this.table[4][0] = new King("White");

		this.table[0][7] = new Rook("Black");
		this.table[7][7] = new Rook("Black");
		this.table[1][7] = new Knight("Black");
		this.table[6][7] = new Knight("Black");
		this.table[2][7] = new Bishop("Black");
		this.table[5][7] = new Bishop("Black");
		this.table[3][7] = new Queen("Black");
		this.table[4][7] = new King("Black");
		
		System.out.println("Table setup completed");
	}
	
	public boolean setupGame() {
		if(this.players[0] == null || this.players[1] == null) {
			System.out.println("You need 2 players to start the game");
			return false;
		}
		
		System.out.println("Random color assignment ? (y/n)");
		char choice;
		choice = sc.next().charAt(0);
		if(Character.toLowerCase(choice) == 'y') {
			int index = (int) Math.round(Math.random());
			System.out.println("WHITE : " + this.players[index].getName());
			this.players[index].setColor("White");
			System.out.println("BLACK : " + this.players[1-index].getName());
			this.players[1-index].setColor("Black");
			System.out.println("To move a piece specify the starting position and then the ending position (eg. A2 A4)");
			this.turn = index;
			this.setupPlayers();
			return true;
		}
		else if(Character.toLowerCase(choice) == 'n') {
			System.out.println("Choose which player should be WHITE : ");
			System.out.println("1 - " + this.players[1].getName());
			System.out.println("2 - " + this.players[2].getName());
			int in;
			while(true) {
				try {
					in = sc.nextInt();
					sc.nextLine();
					if(in != 1 && in != 2) {
						System.err.println("Please choose player 1 or player 2");
						return false;
					}
					in--;
					System.out.println("WHITE : " + this.players[in].getName());
					this.players[in].setColor("White");
					System.out.println("BLACK : " + this.players[1-in].getName());
					this.players[1-in].setColor("Black");
					System.out.println("To move a piece specify the starting position and then the ending position (eg. A2 A4)");
					this.turn = in;
					this.setupPlayers();
					return true;
				}
				catch (InputMismatchException e) {
					System.err.println("Please input a number");
					return false;
				}
			}
		}
		else {
			System.err.println("You need to input either \"Y\" or \"N\" !");
			return false;
		}
	}
	
	public void setupPlayers() {
		this.players[0].setupPlayer();
		this.players[1].setupPlayer();
		this.startGame();
	}
	
	public void startGame() {
		sc.nextLine();
		while(true) {
			this.displayInfo();
			System.out.println("Waiting for " + this.players[this.turn].getName() + " (" + this.players[this.turn].getColor() + ") to make a turn (eg. A2 A4)");
			while(!this.movePiece());
		}
	}
	
	public boolean movePiece() {
		String[] input = sc.nextLine().split(" ");
		if(
			input.length != 2 || input[0].length() != 2 || input[1].length() != 2
		) {
			System.out.println("Invalid format ("+ Arrays.toString(input) +"). Please use format A2 A4");
			return false;
		}
		
		char[] in = {Character.toLowerCase(input[0].charAt(0)), input[0].charAt(1), Character.toLowerCase(input[1].charAt(0)), input[1].charAt(1)};
		int[] oldPos = {(int) in[0]-(int) 'a', (int) in[1] - (int) '1'};
		int[] newPos = {(int) in[2]-(int) 'a', (int) in[3] - (int) '1'};
		if(oldPos[0] < 0 || oldPos[0] > 7 || oldPos[1] < 0 || oldPos[1] > 7 || newPos[0] < 0 || newPos[0] > 7 || newPos[1] < 0 || newPos[1] > 7) {
			System.out.println("Invalid input. Please use A-H and 1-8");
			return false;
		}
		
		return true;
	}
	
	public void displayInfo() {
		String output = "";
		if(this.players[0] == null)
			return;
		output += this.players[0].toString() + "\n";
		if(this.players[1] == null)
			return;
		output += this.players[1].toString() + "\n";
		for(int j=0; j<8; j++) {
			for(int i=0; i<8; i++) {
				if(table[i][j] != null)
					output += table[i][j].getMark() + " ";
				else
					output += "  ";
			}
			output += "\n";
		}
		System.out.println(output);
	}
	
	
}
