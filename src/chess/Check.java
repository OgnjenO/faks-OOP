package chess;

public class Check {
	public static boolean checkCheck(Player p, int[] pos, Piece[][] table) {
		return checkAttackerDiagonalLine(p, pos, table) || checkAttackerKnights(p, pos, table) || checkAttackerStraightLine(p, pos, table);
	}
	
	public static boolean checkAttackerKnights(Player p, int[] pos, Piece[][] table) {
		int i, j;
		i = pos[0];
		j = pos[1];
		
		i += 2; j += 1;
		if(isInBounds(i, j) && table[i][j] != null && table[i][j].getColor() != p.getColor() && table[i][j].getMark() == 'K') return true;
		i -= 2; j -= 1;
		
		i += 2; j -= 1;
		if(isInBounds(i, j) && table[i][j] != null && table[i][j].getColor() != p.getColor() && table[i][j].getMark() == 'K') return true;
		i -= 2; j += 1;
		
		i -= 2; j += 1;
		if(isInBounds(i, j) && table[i][j] != null && table[i][j].getColor() != p.getColor() && table[i][j].getMark() == 'K') return true;
		i += 2; j -= 1;
		
		i -= 2; j -= 1;
		if(isInBounds(i, j) && table[i][j] != null && table[i][j].getColor() != p.getColor() && table[i][j].getMark() == 'K') return true;
		i += 2; j += 1;
		
		i += 1; j += 2;
		if(isInBounds(i, j) && table[i][j] != null && table[i][j].getColor() != p.getColor() && table[i][j].getMark() == 'K') return true;
		i -= 1; j -= 2;
		
		i += 1; j -= 2;
		if(isInBounds(i, j) && table[i][j] != null && table[i][j].getColor() != p.getColor() && table[i][j].getMark() == 'K') return true;
		i -= 1; j += 2;
		
		i -= 1; j += 2;
		if(isInBounds(i, j) && table[i][j] != null && table[i][j].getColor() != p.getColor() && table[i][j].getMark() == 'K') return true;
		i += 1; j -= 2;
		
		i -= 1; j -= 2;
		if(isInBounds(i, j) && table[i][j] != null && table[i][j].getColor() != p.getColor() && table[i][j].getMark() == 'K') return true;
		i += 1; j += 2;
		
		return false;
	}
	
	public static boolean checkAttackerDiagonalLine(Player p, int[] pos, Piece[][] table) {
		int yinc = p.getColor() == "White" ? 1 : -1;
		
		int x = pos[1];
		int y = pos[0]+yinc;
		
		if(isInBounds(x+1, y) && table[y][x+1] != null && table[y][x+1].getMark() == 'P' && table[y][x+1].getColor() != p.getColor()) return true;
		if(isInBounds(x-1, y) && table[y][x-1] != null && table[y][x-1].getMark() == 'P' && table[y][x-1].getColor() != p.getColor()) return true;
		
		for(int i=pos[0]+1, j=pos[1]+1; i<8 && j<8; i++, j++) {
			if(table[i][j] != null)
				if(table[i][j].getColor() != p.getColor() && (table[i][j].getMark() == 'Q' || table[i][j].getMark() == 'B'))
					return true;
				else break;
		}
		
		for(int i=pos[0]-1, j=pos[1]-1; i>-1 && j>-1; i--, j--) {
			if(table[i][j] != null)
				if(table[i][j].getColor() != p.getColor() && (table[i][j].getMark() == 'Q' || table[i][j].getMark() == 'B'))
					return true;
				else break;
		}
		
		for(int i=pos[0]+1, j=pos[1]-1; i<8 && j>-1; i++, j--) {
			if(table[i][j] != null)
				if(table[i][j].getColor() != p.getColor() && (table[i][j].getMark() == 'Q' || table[i][j].getMark() == 'B'))
					return true;
				else break;
		}
		
		for(int i=pos[0]-1, j=pos[1]+1; i>-1 && j<8; i--, j++) {
			if(table[i][j] != null)
				if(table[i][j].getColor() != p.getColor() && (table[i][j].getMark() == 'Q' || table[i][j].getMark() == 'B'))
					return true;
				else break;
		}
		
		return false;
	}
	
	public static boolean checkAttackerStraightLine(Player p, int[] pos, Piece[][] table) {		
		for(int i=pos[0]+1; i<8; i++)
			if(table[i][pos[1]] != null)
				if(table[i][pos[1]].getColor() != p.getColor() && (table[i][pos[1]].getMark() == 'Q' || table[i][pos[1]].getMark() == 'R'))
					return true;
				else
					break;
		
		for(int i=pos[0]-1; i>-1; i--)
			if(table[i][pos[1]] != null)
				if(table[i][pos[1]].getColor() != p.getColor() && (table[i][pos[1]].getMark() == 'Q' || table[i][pos[1]].getMark() == 'R'))
					return true;
				else
					break;
		
		for(int j=pos[1]+1; j<8; j++)
			if(table[pos[0]][j] != null)
				if(table[pos[0]][j].getColor() != p.getColor() && (table[pos[0]][j].getMark() == 'Q' || table[pos[0]][j].getMark() == 'R'))
					return true;
				else
					break;
		
		for(int j=pos[1]-1; j>-1; j--)
			if(table[pos[0]][j] != null)
				if(table[pos[0]][j].getColor() != p.getColor() && (table[pos[0]][j].getMark() == 'Q' || table[pos[0]][j].getMark() == 'R'))
					return true;
				else
					break;
		
		return false;
	}
	
	public static boolean checkEnemyKingAround(Player p, int[] pos, Piece[][] table) {
		int i, j;
		i = pos[0];
		j = pos[1];
		
		i += 1;
		for(int t = -1; t<2; t++)
			if(isInBounds(i, j) && table[i][j+t] != null && table[i][j+t].getColor() != p.getColor() && table[i][j+t].getMark() == 'X') return true;
		i -= 2;
		for(int t = -1; t<2; t++)
			if(isInBounds(i, j) && table[i][j+t] != null && table[i][j+t].getColor() != p.getColor() && table[i][j+t].getMark() == 'X') return true;
		
		i += 1;
		
		j += 1;
		if(isInBounds(i, j) && table[i][j] != null && table[i][j].getColor() != p.getColor() && table[i][j].getMark() == 'X') return true;
		j -= 2;
		if(isInBounds(i, j) && table[i][j] != null && table[i][j].getColor() != p.getColor() && table[i][j].getMark() == 'X') return true;
		
		return false;
	}
	
	public static boolean isInBounds(int i, int j) {
		if(i>7 || i<0 || j>7 || i<0) return false;
		return true;
	}
	
	public static boolean isInBounds(int[] pos) {
		int i=pos[0];
		int j=pos[1];
		return isInBounds(i, j);
	}
	
	public static boolean checkCheckMate(Player p, Table t) {
		Piece[][] table = t.getTable();
		Piece oldP, newP;
		boolean isCheck = false;
		int[] oldPos = new int[2], newPos = new int[2];
		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++) {
				oldPos[0] = i;
				oldPos[1] = j;
				for(int ii=0; ii<8; ii++) {
					for(int jj=0; jj<8; jj++) {
						newPos[0] = ii;
						newPos[1] = jj;
						if(canMove(p, oldPos, newPos, t, table)) {
							oldP = table[oldPos[0]][oldPos[1]];
							newP = table[newPos[0]][newPos[1]];
							
							table[newPos[0]][newPos[1]] = table[oldPos[0]][oldPos[1]];
							table[oldPos[0]][oldPos[1]] = null;
							
							if(table[newPos[0]][newPos[1]].getMark() == 'X') {
								p.setKingPos(newPos);
							}
							
							isCheck = Check.checkCheck(p,  p.getKingPos(), table);
							
							if(table[newPos[0]][newPos[1]].getMark() == 'X') {
								p.setKingPos(oldPos);
							}
							table[oldPos[0]][oldPos[1]] = oldP;
							table[newPos[0]][newPos[1]] = newP;
							
							if(!isCheck) {
//								System.err.println(Arrays.toString(oldPos) + Arrays.toString(newPos));
								return false;
							}
						}
					}
				}
			}
		}
		return true;
	}
	
	public static boolean canMove(Player p, int[] oldPos, int[] newPos, Table t, Piece[][] table) {

		if(table[oldPos[0]][oldPos[1]] == null) {
			return false;
		}
		
		if(table[oldPos[0]][oldPos[1]].getColor() != p.getColor()) {
			return false;
		}

		if(table[newPos[0]][newPos[1]] != null && table[newPos[0]][newPos[1]].getColor() == p.getColor()) {
			return false;
		}
		
		if(!table[oldPos[0]][oldPos[1]].movePiece(oldPos, newPos)) {
			return false;
		}
		
		if(table[oldPos[0]][oldPos[1]].getMark() == 'P' && absDiff(oldPos[1], newPos[1]) == 1 && table[newPos[0]][newPos[1]] == null) {
			return false;
		}
		
		if(!t.checkLine(oldPos, newPos)) {
			return false;
		}
		
		if(table[oldPos[0]][oldPos[1]].getMark() == 'X') {
			if(Check.checkEnemyKingAround(p, newPos, table)) {
				return false;
			}
			
			if(((King) table[oldPos[0]][oldPos[1]]).isCastling(oldPos, newPos)) {
				if(((King) table[oldPos[0]][oldPos[1]]).isBigCastling(oldPos, newPos)) {
					if(p.getBigCastle()) {
						int[] tp = oldPos;
						if(Check.checkCheck(p, tp, table)) {
							return false;
						}
						Piece tempPiece = table[tp[0]][tp[1]];
						table[tp[0]][tp[1]] = null;
						
						tp[1]--;
						if(Check.checkCheck(p, tp, table)) {
							table[oldPos[0]][oldPos[1]] = tempPiece;
							return false;
						}
						
						tp[1]--;
						if(Check.checkCheck(p, tp, table)) {
							table[oldPos[0]][oldPos[1]] = tempPiece;
							return false;
						}
						
						return true;
					}
					else {
						return false;
					}
				}
				else if(((King) table[oldPos[0]][oldPos[1]]).isSmallCastling(oldPos, newPos)) {
					if(p.getSmallCastle()) {
						int[] tp = oldPos;
						if(Check.checkCheck(p, tp, table)) {
							return false;
						}
						Piece tempPiece = table[tp[0]][tp[1]];
						table[tp[0]][tp[1]] = null;
						
						tp[1]++;
						if(Check.checkCheck(p, tp, table)) {
							table[oldPos[0]][oldPos[1]] = tempPiece;
							return false;
						}
						
						tp[1]++;
						if(Check.checkCheck(p, tp, table)) {
							table[oldPos[0]][oldPos[1]] = tempPiece;
							return false;
						}
						
						return true;
					}
					else {
						return false;
					}
				}
			}
		}
		
		return true;
	}
	
	public static int absDiff(int x, int y) {
		return Math.abs(x-y);
	}
}
