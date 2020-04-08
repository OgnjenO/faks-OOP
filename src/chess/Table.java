package chess;

public class Table {
	private Player[] players = new Player[2];
	private Piece[][] table = new Piece[8][8];
	private int turn;
	private Piece shadow = new Shadow();
	
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
	
	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public void setupTable() {
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
	
	public boolean checkCheckMate() {
		if(Check.checkCheckMate(this.players[this.turn], this)) {
			return true;
		}
		
		return false;
	}
	
	public void setupPlayers() {
		this.players[0].setupPlayer();
		this.players[1].setupPlayer();
	}
	
	public boolean movePiece(int[] oldPos, int[] newPos) {
		if(oldPos[0] < 0 || oldPos[0] > 7 || oldPos[1] < 0 || oldPos[1] > 7 || newPos[0] < 0 || newPos[0] > 7 || newPos[1] < 0 || newPos[1] > 7) {
			System.err.println("Invalid input. Please use A-H and 1-8");
			return false;
		}
		
		if(!this.canMove(oldPos, newPos)) return false;
		
		if(table[oldPos[0]][oldPos[1]].getMark() == 'X') {
			if(Check.checkEnemyKingAround(this.players[this.turn], newPos, table)) {
				System.err.println("Invalid input. Enemy kings can not stand next to each other");
				return false;
			}
			
			if(((King) table[oldPos[0]][oldPos[1]]).isCastling(oldPos, newPos)) {
				if(((King) table[oldPos[0]][oldPos[1]]).isBigCastling(oldPos, newPos)) {
					if(this.players[this.turn].getBigCastle()) {
						return this.doBigCastling(oldPos);
					}
					else {
						System.err.println("Invalid input. You are not allowed to do big castling");
						return false;
					}
				}
				else if(((King) table[oldPos[0]][oldPos[1]]).isSmallCastling(oldPos, newPos)) {
					if(this.players[this.turn].getSmallCastle()) {
						return this.doSmallCastling(oldPos);
					}
					else {
						System.err.println("Invalid input. You are not allowed to do small castling");
					}
				}
			}
		}
		
		if(table[oldPos[0]][oldPos[1]].getMark() == 'P') {
			this.checkPawn(oldPos, newPos);
		}
		
		Piece oldP = table[oldPos[0]][oldPos[1]];
		Piece newP = table[newPos[0]][newPos[1]];
		
		table[newPos[0]][newPos[1]] = table[oldPos[0]][oldPos[1]];
		table[oldPos[0]][oldPos[1]] = null;
		
		if(table[newPos[0]][newPos[1]].getMark() == 'X') {
			this.players[this.turn].setKingPos(newPos);
		}
		
		if(Check.checkCheck(this.players[this.turn], this.players[this.turn].getKingPos(), table)) {
			System.err.println("Invalid input. You can not move a piece in a way that makes you end up in check");
			if(table[newPos[0]][newPos[1]].getMark() == 'X') {
				this.players[this.turn].setKingPos(oldPos);
			}
			table[oldPos[0]][oldPos[1]] = oldP;
			table[newPos[0]][newPos[1]] = newP;
			
			return false;
		}
		
		System.out.print(oldP.getName() + " (" + oldP.getColor() + ") "/* + input[0] + " -> " + input[1]*/);
		if(newP != null && newP.getName() != "Shadow") {
			System.out.println("   |   Took " + newP.getName());
		}
		// for(int i=0; i<40; i++) System.out.println();
		
		this.checkShadow();
		
		if(((oldPos[0] == 0 || oldPos[0] == 7) && (oldPos[1] == 0 || oldPos[1] == 4))) {
			this.players[this.turn].setBigCastle(false);
		}
		
		if(((oldPos[0] == 0 || oldPos[0] == 7) && (oldPos[1] == 7 || oldPos[1] == 4))) {
			this.players[this.turn].setSmallCastle(false);
		}
		
		if(table[newPos[0]][newPos[1]].getMark() == 'P') {
			if(this.players[this.turn].getColor() == "White" && newPos[0] == 7) {
				System.out.println("Queen promotion");
				table[newPos[0]][newPos[1]] = new Queen("White");
			}
			else if(this.players[this.turn].getColor() == "Black" && newPos[0] == 0) {
				System.out.println("Queen promotion");
				table[newPos[0]][newPos[1]] = new Queen("Black");
			}
		}
		
		return true;
	}
	
	public boolean canMove(int[] oldPos, int[] newPos) {
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
		
		return true;
	}
	
	public boolean canDoBigCastling(int[] pos) {
		int[] tp = pos;
		Player p = this.players[this.turn];
		if(Check.checkCheck(p, tp, table)) {
			System.err.println("Invalid input. You can not do big castling while under check");
			return false;
		}
		Piece tempPiece = table[tp[0]][tp[1]];
		table[tp[0]][tp[1]] = null;
		
		tp[1]--;
		if(Check.checkCheck(p, tp, table)) {
			System.err.println("Invalid input. You can not do big castling while squar D" + (tp[0]+1) + " is under attack");
			table[pos[0]][pos[1]] = tempPiece;
			return false;
		}
		
		tp[1]--;
		if(Check.checkCheck(p, tp, table)) {
			System.err.println("Invalid input. You can not do big castling while squar C" + (tp[0]+1) + " is under attack");
			table[pos[0]][pos[1]] = tempPiece;
			return false;
		}
		
		return true;
	}
	
	public boolean doBigCastling(int[] pos) {
		int[] tp = pos;

		Piece tempPiece = table[tp[0]][tp[1]];
		table[tp[0]][tp[1]] = null;
		
		if(!this.canDoBigCastling(pos)) return false;
		table[tp[0]][tp[1]] = tempPiece;
		table[tp[0]][tp[1]+1] = table[tp[0]][0];
		table[tp[0]][0] = null;
		
		System.out.println("King  (" + tempPiece.getColor() + ") -> Big castling");
		
		this.checkShadow();
		
		return true;
	}
	
	public boolean canDoSmallCastling(int[] pos) {
		int[] tp = pos;
		Player p = this.players[this.turn];
		if(Check.checkCheck(p, tp, table)) {
			System.err.println("Invalid input. You can not do big castling while under check");
			return false;
		}
		Piece tempPiece = table[tp[0]][tp[1]];
		table[tp[0]][tp[1]] = null;
		
		tp[1]++;
		if(Check.checkCheck(p, tp, table)) {
			System.err.println("Invalid input. You can not do big castling while squar F" + (tp[0]+1) + " is under attack");
			table[pos[0]][pos[1]] = tempPiece;
			return false;
		}
		
		tp[1]++;
		if(Check.checkCheck(p, tp, table)) {
			System.err.println("Invalid input. You can not do big castling while squar G" + (tp[0]+1) + " is under attack");
			table[pos[0]][pos[1]] = tempPiece;
			return false;
		}
		
		return true;
	}
	
	public boolean doSmallCastling(int[] pos) {
		int[] tp = pos;

		Piece tempPiece = table[tp[0]][tp[1]];
		table[tp[0]][tp[1]] = null;
		
		if(!this.canDoSmallCastling(pos)) return false;
		table[tp[0]][tp[1]] = tempPiece;
		table[tp[0]][tp[1]-1] = table[tp[0]][7];
		table[tp[0]][7] = null;
		
		System.out.println("King  (" + tempPiece.getColor() + ") -> Small castling");
		
		this.checkShadow();
		
		return true;
	}
	
	public void checkShadow() {
		Shadow s = (Shadow) this.shadow;
		if(s.getTimer() == -1) return;
		s.incTimer();
		if(s.getTimer() > 1) {
			s.setTimer(-1);
			if(table[s.getPos(0)][s.getPos(1)] != null && table[s.getPos(0)][s.getPos(1)].getName() == "Shadow") table[s.getPos(0)][s.getPos(1)] = null;
		}
	}
	
	public void checkPawn(int[] oldPos, int[] newPos) {
		int moveWay = oldPos[0]-newPos[0];
		if(table[newPos[0]][newPos[1]] != null && table[newPos[0]][newPos[1]].getName() == "Shadow") {
			table[newPos[0]][newPos[1]] = table[newPos[0]+moveWay][newPos[1]];
			table[newPos[0]+moveWay][newPos[1]] = null;
		}
			
		if(moveWay == 2 || moveWay == -2) {
			Shadow s = (Shadow) this.shadow;
			if(table[s.getPos(0)][s.getPos(1)] != null && table[s.getPos(0)][s.getPos(1)].getName() == "Shadow") table[s.getPos(0)][s.getPos(1)] = null;
			table[newPos[0]+moveWay/2][newPos[1]] = this.shadow;
			s.resetTimer();
			s.setPos(0, newPos[0]+moveWay/2);
			s.setPos(1, newPos[1]);
			
		}
	}
	
	public void changeTurn() {
		this.turn = 1 - this.turn;
	}
	
	public boolean checkLine(int[] oldPos, int[] newPos) {
		Piece p = table[oldPos[0]][oldPos[1]];
		if(p.getMark() == 'K' || p.getMark() == 'X' || p.getMark() == 'P') {
			return true;
		}
		
		if(p.getMark() == 'R') return this.checkStraightLine(oldPos, newPos);
		if(p.getMark() == 'B') return this.checkDiagonalLine(oldPos, newPos);
		if(p.getMark() == 'Q') return this.checkStraightLine(oldPos, newPos) || this.checkDiagonalLine(oldPos, newPos);
		
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
			return true;
		}
		else if(oldPos[1] == newPos[1]) { 
			from = oldPos[0]<newPos[0] ? oldPos[0] : newPos[0];
			to = oldPos[0]>newPos[0] ? oldPos[0] : newPos[0]; 
			for(int j=from+1; j<to; j++) {
				if(table[j][oldPos[1]] != null) return false;
			}
			return true;
		}
		
		return false;
	}
	
	public boolean checkDiagonalLine(int[] oldPos, int[] newPos) {
		int from[] = new int[2];
		if(this.absDiff(oldPos[0], newPos[0]) == this.absDiff(oldPos[1], newPos[1])) {
			from[0] = oldPos[0];
			from[1] = oldPos[1];
			int iinc = oldPos[0] < newPos[0] ? 1 : -1;
			int jinc = oldPos[1] < newPos[1] ? 1 : -1;
			for(int i=1; i<this.absDiff(oldPos[0], newPos[0]); i++) {
				if(table[from[0]+i*iinc][from[1]+i*jinc] != null) {
					return false;	
				}
			}
			return true;
		}
		
		return false;
	}

	public int absDiff(int x, int y) {
		return Math.abs(x-y);
	}
	
	public Piece[][] getTable() {
		return this.table;
	}
	
	public void displayInfo() {
		String output = "\n\n----------------------------------------------------\n";
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
				if(table[i][j] != null && table[i][j].getName() != "Shadow")
					output += "|" + table[i][j].getColorMark() + " " + table[i][j].getMark() + "| ";
				else
					output += "|   | ";
			}
			output += "\n       ---   ---   ---   ---   ---   ---   ---   ---\n";
		}
		output += "\n\n        ";
		
		for(int j=0; j<8; j++) {
			output += (char) (j+(int) 'A') + "     ";
		}
		
		output += "\nTurn : " + this.players[this.turn].getColor();
		
		System.out.println(output);
	}
	
	
}
