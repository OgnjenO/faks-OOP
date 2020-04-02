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
			System.err.println("There are already 2 players on this table");
			return false;
		}
		return true;
	}
	
	public boolean removePlayer(int index) throws Exception {
		if(index != 0 && index != 1) {
			System.err.println("Index must be 0 or 1");
			return false;
		}
		
		if(this.players[index] == null) {
			System.err.println("There is no player at index : " + index);
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
			this.table[1][i] = new Pawn("White");
			this.table[6][i] = new Pawn("Black");
		}
		this.table[0][0] = new Rook("White");
		this.table[0][7] = new Rook("White");
		this.table[0][1] = new Knight("White");
		this.table[0][6] = new Knight("White");
		this.table[0][2] = new Bishop("White");
		this.table[0][5] = new Bishop("White");
		this.table[0][3] = new Queen("White");
		this.table[0][4] = new King("White");

		this.table[7][0] = new Rook("Black");
		this.table[7][7] = new Rook("Black");
		this.table[7][1] = new Knight("Black");
		this.table[7][6] = new Knight("Black");
		this.table[7][2] = new Bishop("Black");
		this.table[7][5] = new Bishop("Black");
		this.table[7][3] = new Queen("Black");
		this.table[7][4] = new King("Black");
		
		System.out.println("Table setup completed");
	}
	
	public boolean setupGame() {
		if(this.players[0] == null || this.players[1] == null) {
			System.err.println("You need 2 players to start the game");
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
			this.turn = 1 - this.turn;
		}
	}
	
	public boolean movePiece() {
		String[] input = sc.nextLine().split(" ");
		if(
			input.length != 2 || input[0].length() != 2 || input[1].length() != 2
		) {
			System.err.println("Invalid format ("+ Arrays.toString(input) +"). Please use format A2 A4");
			return false;
		}
		
		char[] in = {Character.toLowerCase(input[0].charAt(0)), input[0].charAt(1), Character.toLowerCase(input[1].charAt(0)), input[1].charAt(1)};
		int[] oldPos = {(int) in[1]-(int) '1', (int) in[0] - (int) 'a'};
		int[] newPos = {(int) in[3]-(int) '1', (int) in[2] - (int) 'a'};
		if(oldPos[0] < 0 || oldPos[0] > 7 || oldPos[1] < 0 || oldPos[1] > 7 || newPos[0] < 0 || newPos[0] > 7 || newPos[1] < 0 || newPos[1] > 7) {
			System.err.println("Invalid input. Please use A-H and 1-8");
			return false;
		}
		
		if(table[oldPos[0]][oldPos[1]] == null) {
			System.err.println("Invalid input. Selected space is empty, nothing to move");
			return false;
		}
		
		if(table[oldPos[0]][oldPos[1]].getColor() != this.players[this.turn].getColor()) {
			System.err.println("Invalid input. You can only move your (" + this.players[this.turn].getColor() + ") pieces");
			return false;
		}

		if(table[newPos[0]][newPos[1]] != null && table[newPos[0]][newPos[1]].getColor() == this.players[this.turn].getColor()) {
			System.err.println("Invalid input. You can not stack your pieces on top of each other");
			return false;
		}
		
		if(!table[oldPos[0]][oldPos[1]].movePiece(oldPos, newPos)) {
			System.err.println("Invalid input. You can not move your pieces that way");
			return false;
		}
		
		if(table[oldPos[0]][oldPos[1]].getMark() == 'P' && this.absDiff(oldPos[1], newPos[1]) == 1 && table[newPos[0]][newPos[1]] == null) {
			System.err.println("Invalid input. Pawn move diagonaly only when taking enemy piece");
			return false;
		}
		
		if(!this.checkLine(oldPos, newPos)) {
			System.err.println("Invalid input. Something is obstructing your way");
			return false;
		}
		
		Piece p = table[oldPos[0]][oldPos[1]];
		
		System.out.print(p.getName() + " (" + p.getColor() + ") " + input[0] + " -> " + input[1]);
		p = table[newPos[0]][newPos[1]];
		if(p != null) {
			System.out.print("   |   Took " + p.getName());
		}
		System.out.println();
		table[newPos[0]][newPos[1]] = table[oldPos[0]][oldPos[1]];
		table[oldPos[0]][oldPos[1]] = null;
		
		return true;
	}
	
	public boolean checkLine(int[] oldPos, int[] newPos) {
		Piece p = table[oldPos[0]][oldPos[1]];
		if(p.getMark() == 'K' || p.getMark() == 'X' || p.getMark() == 'P') {
			return true;
		}
		
		if(p.getMark() == 'R') return this.checkStraightLine(oldPos, newPos);
		if(p.getMark() == 'B') return this.checkDiagonalLine(oldPos, newPos);
		if(p.getMark() == 'Q') return this.checkStraightLine(oldPos, newPos) && this.checkDiagonalLine(oldPos, newPos);
		
		return true;
	}
	
	public boolean checkStraightLine(int[] oldPos, int[] newPos) {
		int from, to;
		if(oldPos[0] == newPos[0]) {
			from = oldPos[1]<newPos[1] ? oldPos[1] : newPos[1];
			to = oldPos[1]>newPos[1] ? oldPos[1] : newPos[1]; 
			for(int i=from+1; i<to; i++) {
				if(table[oldPos[0]][i] != null) return false;
			}
		}
		else { 
			from = oldPos[0]<newPos[0] ? oldPos[0] : newPos[0];
			to = oldPos[0]>newPos[0] ? oldPos[0] : newPos[0]; 
			for(int j=from+1; j<to; j++) {
				if(table[j][oldPos[1]] != null) return false;
			}
		}
		
		return true;
	}
	
	public boolean checkDiagonalLine(int[] oldPos, int[] newPos) {
		int from[] = new int[2];
		from[0] = oldPos[0]<newPos[0] ? oldPos[0] : newPos[0];
		from[1] = oldPos[1]<newPos[1] ? oldPos[1] : newPos[1];
		for(int i=1; i<this.absDiff(oldPos[0], newPos[0]); i++) {
			if(table[from[0]+i][from[1]+i] != null) return false;
		}
		
		return true;
	}

	public int absDiff(int x, int y) {
		return Math.abs(x-y);
	}
	
	public void displayInfo() {
		String output = "\n\n";
		if(this.players[0] == null)
			return;
		output += this.players[0].toString() + "\n";
		if(this.players[1] == null)
			return;
		output += this.players[1].toString() + "\n";
		output += "       ---   ---   ---   ---   ---   ---   ---   ---\n";
		for(int i=7; i>=0; i--) {
			output += i+1 + "     ";
			for(int j=0; j<8; j++) {
				if(table[i][j] != null)
					output += "| " + table[i][j].getMark() + " | ";
				else
					output += "|   | ";
			}
			output += "\n       ---   ---   ---   ---   ---   ---   ---   ---\n";
		}
		output += "\n\n        ";
		
		for(int j=0; j<8; j++) {
			output += (char) (j+(int) 'A') + "     ";
		}
		System.out.println(output);
	}
	
	
}
