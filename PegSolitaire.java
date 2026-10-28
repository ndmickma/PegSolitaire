/**
 *	PegSolitaire game.
 *
 *	@author	Sanvitti Shah
 *	@since	October 15th, 2026	
 *
 */

import java.util.ArrayList;

public class PegSolitaire {
	
	// fields
	private PegBoard pegBoard; //board object that manages pegs and grid spaces
		
	/** constructor */
	public PegSolitaire() { 
		pegBoard = new PegBoard(); //initialize peg board when a game is created
	}
	
	/** methods */
	public static void main(String [] args) {
		PegSolitaire game = new PegSolitaire(); //create a new game instance\
		game.play();
	}

	/**
	 * Coordinates flow of the game
	 */
	public void play(){
		printIntroduction(); //show game title and rules

		boolean isPlaying = true; //controls game loop

		while(isPlaying) { //main game loop
			pegBoard.printBoard(); //prin the current state of the board

			//check if there are any valid moves remaining on the board
			if(!hasValidMoves()) {
				System.out.println("No more moves possible!");
				isPlaying = false; //set flag to false to exit loop
			}
			else {
				Location jumper = getJumperLocation(); //prompt user for jumper peg location or 'q' for quit
				if(jumper == null) {//if user entered 'q', quit the game
					isPlaying = false; //exit loop
				}
				else {
					processJump(jumper); //process the jump for the selected peg

					//check if only 1 peg remains (winning condition)
					if(pegBoard.pegCount() == 1) {
						pegBoard.printBoard();
						System.out.println("Congratualations! You won with only 1 peg remaining!");
						isPlaying = false; //exit loop
					}
				}
			}
		}
		//print final score when game ends
		System.out.println("Your score: " + pegBoard.pegCount() + " pegs remaining");
		System.out.println("Thanks for playing Peg Solitaire!");
	}

	/**
	 * checks if there are any valid moves left anywhere on the board
	 * @return true if at least one peg can make a jump, false otherwise
	 */
	public boolean hasValidMoves() {
		for(int r = 0; r < pegBoard.getBoardSize(); r++) {
			for(int c = 0; c < pegBoard.getBoardSize(); c++) {
				if(pegBoard.isPeg(r, c)) { //if there is a peg at this location
					Location loc = new Location(r, c);
					if(getJumpLocations(loc).size() > 0) { //check if it has any valid jump destinations
						return true; //found at least one valid move
					}
				}
			}
		}
		return false; //no valid moves left anywhere
	}

	/**
	 * prompts user for a jumper peg location or 'q' to quit
	 * parses input and returns a Location object or null if quitting
	 */
	public Location getJumperLocation() {
		while(true) { //creates an infinite loop until valid input or quit is received
			String input = Prompt.getString("Jumper peg: row col (ex. 3 5) or type 'q' to quit");
			input = input.trim(); //remove extra spaces from start or end
			if(input.equalsIgnoreCase("q")) { //check if user entered q
				return null; //signals quitting game
			}

			String [] values = input.split("\\s+"); //split the input by spaces into array of strings
			if(values.length == 2) { //make sure that exactly 2 values were entered
				try {
					int row = Integer.parseInt(values[0]); //first val is parsed as row int
					int col = Integer.parseInt(values[1]); //second val is parsed as col int

					//check if the location is on board and has a peg
					if(pegBoard.isPeg(row, col)){
						return new Location(row, col); //return the valid Location object
					}
					else {
						System.out.println("Invalid jumper peg -> " + input); //print error message if not valid peg location
					}
				} catch(NumberFormatException e) {
					//catch block handles cases where the user typed non-numbers like "hello"
					System.out.println("Invalid jumper peg -> " + input); //print error message
				}
			}
			else {
				System.out.println("Invalid jumper peg -> " + input); //print error message if user did not enter two numbers
			}
		}
	}

	/**
	 * Finds all valid jump destinations for a given jumper peg
	 * @param jumper The Location of the peg we want to move
	 * @return An ArrayList of valid desination Locations
	 */
	public ArrayList<Location> getJumpLocations(Location jumper) {
		ArrayList<Location> destinations = new ArrayList<Location>(); //create a list to store valid destination spots

		int r = jumper.getRow(); 
		int c = jumper.getCol();

		//define the 4 directions as row and col offsets" {rowOffset, colOffset}
		//{-2, 0} = UP; {2, 0} = DOWN; {0, -2} = LEFT; {0, 2} = RIGHT
		int[][] directions = { {-2, 0}, {2, 0}, {0, -2}, {0, 2}}; //moving -2 rows means jumping 2 spaces up
							//moving -2 cols means jumping 2 spaces to the left
		for(int i = 0; i < directions.length; i++) { //loop through each of the 4 directions (i = 0 for up, i = 1 for down, i = 2 for left, i = 3 for right)
			int rOffSet = directions[i][0]; //get the row jump distance
			int cOffSet = directions[i][1]; //get the col jump distance

			int targetRow = r + rOffSet; //calculate landing row (2 spaces away)
			int targetCol = c + cOffSet; //calculate landing col (2 spaces away)

			int midRow = r + (rOffSet / 2); //calculate the row of the peg being jumped over (1 space away)
			int midCol = c + (cOffSet / 2); //calculate the col of the peg being jumped over (1 space away)

			//check if landing spot is valid, empty, and there is a peg in between to jump over
			if(pegBoard.isValidLocation(targetRow, targetCol) && 
					!pegBoard.isPeg(targetRow, targetCol) && pegBoard.isPeg(midRow, midCol)){

				destinations.add(new Location(targetRow, targetCol)); //add the valid landing location to our list
			}
		}
		return destinations; //return the list of valid destinations
	}

	/**
	 * Processes the jump for a selected jumper peg
	 * -If 0 moves. prints invalid messagee
	 * -If 1 move, executes it immediately
	 * -If multiple moves, prompts use to choose which one
	 * @param jumper The Location of the peg chosen to jump
	 * @return true if a move was successfully made, false otherwise
	 */
	public boolean processJump(Location jumper) {
		ArrayList<Location> destinations = getJumpLocations(jumper); //find all valid landing spots for this peg

		if(destinations.size() == 0) { //Check if peg has no possible moves
			System.out.println("Invalid jumper peg -> " + jumper); //print invalid message
			return false; //since no move was made
		}
		else if(destinations.size() == 1) { //check if there is exactly one possible jump move
			executeMove(jumper, destinations.get(0)); //execute that single move immediately
			return true; //return true since a move was made
		}
		else { //if there are multiple possible jump locations
			System.out.println("Possible peg jump locations:"); //header for choices
			for(int i = 0; i < destinations.size(); i++) { //loop through each available destination option
				System.out.println(" " + i + " " + destinations.get(i)); //print index number and the location coordinates
			}

			//prompt user to pick a valid choice number using Promp.getInt()
			int choice = Prompt.getInt("Enter location: ", 0, destinations.size() - 1);

			executeMove(jumper, destinations.get(choice)); //execute the move chosen by the user
			return true;
		}
	}

	/**
	 * Executes a physical jump on the board
	 * -removes the peg from starting location
	 * -removes the jumped peg from the middle space
	 * -places a peg at the final landing destination
	 * @param start The starting location of the jumper peg
	 * @param end The landing destination location
	 */
	public void executeMove(Location start, Location end) {
		pegBoard.removePeg(start.getRow(), start.getCol()); //remove peg from its original starting spot

		//calculate the middle location between start and end (the jumped peg)
		int midRow = (start.getRow() + end.getRow()) / 2;
		int midCol = (start.getCol() + end.getCol()) / 2;
		pegBoard.removePeg(midRow, midCol); //remove the jumped peg from the board

		pegBoard.putPeg(end.getRow(), end.getCol()); //place the jumper peg into its final destination spot
	}
	
	/**
	 *	Print the introduction to the game
	 */
	public void printIntroduction() {
		System.out.println("  _____              _____       _ _ _        _ ");
		System.out.println(" |  __ \\            / ____|     | (_) |      (_)");
		System.out.println(" | |__) |__  __ _  | (___   ___ | |_| |_ __ _ _ _ __ ___ ");
		System.out.println(" |  ___/ _ \\/ _` |  \\___ \\ / _ \\| | | __/ _` | | '__/ _ \\");
		System.out.println(" | |  |  __/ (_| |  ____) | (_) | | | || (_| | | | |  __/");
		System.out.println(" |_|   \\___|\\__, | |_____/ \\___/|_|_|\\__\\__,_|_|_|  \\___|");
		System.out.println("             __/ |");
		System.out.println("            |___/");
		System.out.println("\nWelcome to Peg Solitaire!!!\n");
		System.out.println("Peg Solitaire is a game for one player. The " +
							"goal is to remove all\n" +
							"but one of the 32 pegs from a special board. " +
							"The board is a 7x7\n" +
							"grid with the corners cut out (shown below)." +
							" Pegs are placed in all");
		System.out.println("grid locations except the center which is " +
							"left empty. Pegs jump\n" +
							"over other pegs either horizontally or " +
							"vertically into empty\n" +
							"locations and the jumped peg is removed. Play " +
							"continues until\n" +
							"there are no more jumps possible, or there " +
							"is one peg remaining.");
		System.out.println("\nLet's play!!!\n");
	}
	
}
