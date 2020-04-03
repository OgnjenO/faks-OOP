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
}
