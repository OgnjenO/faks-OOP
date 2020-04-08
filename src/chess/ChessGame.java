package chess;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ChessGame {
	private Table t = new Table();

	private Scanner sc;
	private boolean moveWithMethods = false;
	
	public boolean startGame() {
		this.moveWithMethods = false;
		sc = new Scanner(System.in);
		Player[] players = t.getPlayers();
		if(players[0] == null || players[1] == null) {
			System.err.println("You need 2 players to start the game");
			return false;
		}
		char choice;
		
		System.out.println("Manual setup ? (y/n)");
		choice = sc.next().charAt(0);
		if(Character.toLowerCase(choice) == 'y') while(!this.manualSetupGame());
		else if(Character.toLowerCase(choice) == 'n') {
			System.out.println("The game configuration for game setup : ");
			System.out.println("Moving pieces with conosle (Commands)");
			System.out.println("Random colors");
			System.out.println("");
			while(!this.movePieceWithMethods('n'));
			while(!this.colorAssignment('y'));
			this.initiateGame();
		}
		else {
			System.err.println("You need to input either \"Y\" or \"N\" !");
			return false;
		}
		
		return true;
	}
	
	public Player[] getPlayers() {
		return t.getPlayers();
	}

	public void setPlayers(Player[] players) {
		t.setPlayers(players);
	}
	
	public void setPlayers(Player player1, Player player2) {
		t.setPlayers(player1, player2);
	}
	
	public boolean addPlayer(Player player) throws Exception {
		return t.addPlayer(player);
	}
	
	public boolean removePlayer(int index) throws Exception {
		return t.removePlayer(index);
	}
	
	public boolean manualSetupGame() {
		System.out.println("Random color assignment ? (y/n)");
		char choice;
		choice = sc.next().charAt(0);
		if(!colorAssignment(choice)) return false;
		System.out.println("Move pieces with methods instead of console ? (y/n)");
		choice = sc.next().charAt(0);
		if(!movePieceWithMethods(choice)) return false;
		sc.nextLine();
		this.initiateGame();
		return true;
	}
	
	public boolean colorAssignment(char choice) {
		Player[] players = t.getPlayers();
		if(Character.toLowerCase(choice) == 'y') {
			int index = (int) Math.round(Math.random());
			System.out.println("WHITE : " + players[index].getName());
			players[index].setColor("White");
			players[index].setKingPos(0, 0);
			players[index].setKingPos(1, 4);
			System.out.println("BLACK : " + players[1-index].getName());
			players[1-index].setColor("Black");
			players[1-index].setKingPos(0, 7);
			players[1-index].setKingPos(1, 4);
			t.setTurn(index);
			t.setupPlayers();
			return true;
		}
		else if(Character.toLowerCase(choice) == 'n') {
			System.out.println("Choose which player should be WHITE : ");
			System.out.println("1 - " + players[0].getName());
			System.out.println("2 - " + players[1].getName());
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
					System.out.println("WHITE : " + players[in].getName());
					players[in].setColor("White");
					players[in].setKingPos(0, 0);
					players[in].setKingPos(1, 4);
					System.out.println("BLACK : " + players[1-in].getName());
					players[1-in].setColor("Black");
					players[1-in].setKingPos(0, 7);
					players[1-in].setKingPos(1, 4);
					t.setTurn(in);
					t.setupPlayers();
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
	
	public boolean movePieceWithMethods(char choice) {
		if(Character.toLowerCase(choice) == 'y') {
			System.out.println("Use makeAMove(\"A2 A4\") method to make a move");
			this.moveWithMethods = true;
			return true;
		}
		else if(Character.toLowerCase(choice) == 'n') {
			System.out.println("Moving pieces with console is enabled");
			sc.nextLine();
			this.moveWithMethods = false;
			return true;
		}
		else {
			System.err.println("You need to input either \"Y\" or \"N\" !");
			return false;
		}
	}
	
	public void initiateGame() {
//		Player[] players = t.getPlayers();
		System.out.println("\n\nTo move a piece specify the starting position and then the ending position (eg. A2 A4)");
		t.displayInfo();
		System.out.println("\n\n----------------------------------------------------\n----------------------------------------------------\n\n");
		if(this.moveWithMethods) return;
//		System.out.println("Waiting for " + players[this.turn].getName() + " (" + players[this.turn].getColor() + ") to make a turn (eg. A2 A4)");
		boolean isValidMove = false;
		while(true) {
			isValidMove = this.movePiece("");
			while(!isValidMove) {
				if(t.checkCheckMate()) {
					System.err.println("CHECKMATE");
				}
				isValidMove = this.movePiece("");
			}
			t.changeTurn();
			t.displayInfo();
//			System.out.println("Waiting for " + players[this.turn].getName() + " (" + players[this.turn].getColor() + ") to make a turn (eg. A2 A4)");
		}
	}
	
	public void makeAMove(String input) {
//		Player[] players = t.getPlayers();
//		System.out.println("Waiting for " + players[this.turn].getName() + " (" + players[this.turn].getColor() + ") to make a turn (eg. A2 A4)");
//		System.out.println("Press any button to make the next move");
//		sc.nextLine();
		System.out.println(input);
		boolean isValidMove = this.movePiece(input);
		if(isValidMove) {
			t.changeTurn();
			t.displayInfo();
		}
		if(t.checkCheckMate()) {
			System.err.println("CHECKMATE");
		}
	}
	
	public String[] getMoveFromScanner() {
		return sc.nextLine().split(" ");
	}
	
	public String[] getMoveFromString(String move) {
		return move.split(" ");
	}
	
	public boolean movePiece() {
		String[] input = getMoveFromScanner();
		return this.movePieceOnTable(input);
	}
	
	public boolean movePiece(String move) {
		String[] input;
		input = getMoveFromString(move);
		return this.movePieceOnTable(input);
	}
	
	public boolean movePieceOnTable(String[] input) {
		if(
				input.length != 2 || input[0].length() != 2 || input[1].length() != 2
		) {
			System.err.println("Invalid format ("+ Arrays.toString(input) +"). Please use format A2 A4");
			return false;
		}
			
		char[] in = {Character.toLowerCase(input[0].charAt(0)), input[0].charAt(1), Character.toLowerCase(input[1].charAt(0)), input[1].charAt(1)};
		int[] oldPos = {(int) in[1]-(int) '1', (int) in[0] - (int) 'a'};
		int[] newPos = {(int) in[3]-(int) '1', (int) in[2] - (int) 'a'};
		
		return t.movePiece(oldPos, newPos);
	}
}
