/**
 *	PegBoard for the Peg Solitaire game.
 *
 *	@author	Sanvitti Shah
 *	@since	October 15th, 2026
 *
 *	This is the English version of the board.
 *	It is a 7x7 board without the corners. The game starts with pegs in
 *	all the locations except the center, as shown below.
 *
 *  col 0   1   2   3   4   5   6
 * row        -------------
 *  0         | P | P | P |
 *            -------------
 *  1         | P | P | P |
 *    -----------------------------
 *  2 | P | P | P | P | P | P | P |
 *    -----------------------------
 *  3 | P | P | P |   | P | P | P |
 *    -----------------------------
 *  4 | P | P | P | P | P | P | P |
 *    -----------------------------
 *  5         | P | P | P |
 *            -------------
 *  6         | P | P | P |
 *            -------------
 *
 */

public class PegBoard {
	
	private char[][] board;				// the peg board of characters
	
	private final int BOARD_SIZE = 7;	// the side length of the square board
	
	/* constructor */
	public PegBoard() {
		// initialize board
		board = new char[BOARD_SIZE][BOARD_SIZE];
		for(int row = 0; row < BOARD_SIZE; row++) {
			for(int col = 0; col < BOARD_SIZE; col++) {
				if(isValidLocation(row, col)) {
					board[row][col] = 'P'; //if valid location put a 'P' in it
				}
				else 
					board[row][col] = ' '; //if not a valid location it is an empty space
			}
		}
		board[3][3] = ' '; //the slot at 3 3 always starts blank
	}
	
	/**
	 *	Print the peg board to the screen.
	 */
	public void printBoard() { //already done
		System.out.println();
		System.out.println(" col 0   1   2   3   4   5   6");
		System.out.println("row        -------------");
		System.out.print(" 0         |");
		for (int a = 2; a < 5; a++) System.out.printf(" %c |", board[0][a]);
		System.out.println("\n           -------------");
		System.out.print(" 1         |");
		for (int a = 2; a < 5; a++) System.out.printf(" %c |", board[1][a]);
		System.out.println("\n   -----------------------------");
		System.out.print(" 2 |");
		for (int a = 0; a < 7; a++) System.out.printf(" %c |", board[2][a]);
		System.out.println("\n   -----------------------------");
		System.out.print(" 3 |");
		for (int a = 0; a < 7; a++) System.out.printf(" %c |", board[3][a]);
		System.out.println("\n   -----------------------------");
		System.out.print(" 4 |");
		for (int a = 0; a < 7; a++) System.out.printf(" %c |", board[4][a]);
		System.out.println("\n   -----------------------------");
		System.out.print(" 5         |");
		for (int a = 2; a < 5; a++) System.out.printf(" %c |", board[5][a]);
		System.out.println("\n           -------------");
		System.out.print(" 6         |");
		for (int a = 2; a < 5; a++) System.out.printf(" %c |", board[6][a]);
		System.out.println("\n           -------------");
		System.out.println();
	}
	
	/**
	 * returns the number of remaining pegs on the board
	 */
	public int pegCount() {
		int count = 0; //keeps track of total pegs
		for(int row = 0; row < BOARD_SIZE; row++) { //loop through every row 0-6
			for(int col = 0; col < BOARD_SIZE; col++) { //loop through every col 0-6
				if(isPeg(row, col)){//check if there's a peg at current row and col location
					count++; //if there is a peg increase the count
				}

			}
		}
		return count;
	}
	
	/**
	 * returns true if the row and col vals are on the board
	 */
	public boolean isValidLocation(int row, int col) {
		return(row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE) &&
				((row >= 2 && row <= 4) || (col >= 2 && col <= 4));
	}
	
	/**
	 * places a peg into the (row, col) location
	 */
	public void putPeg(int row, int col) {
		if(isValidLocation(row, col)){ //check if target row and col are on valid board
			board[row][col] = 'P'; //if valid place a 'P' at this position
		}

	}
	
	/**
	 * removes the peg from the (row, col) location
	 */
	public void removePeg(int row, int col) { 
		if (isValidLocation(row, col)){ //check if point is on the board
			board[row][col] = ' '; //if valid clear peg by replacing it with blank space
		}
	}
	
	/**
	 * returns true if peg is in location (row, col); false otherwise
	 */
	public boolean isPeg(int row, int col) {
		//returns true only if the location is valid AND the character at that spot is a 'P'
		return(isValidLocation(row, col) && board[row][col] == 'P');
	}
	
	/** @return		size of the board */
	public int getBoardSize() { return BOARD_SIZE; }
}
