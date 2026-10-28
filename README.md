PegSolitaire

What is it?
-Java version of the classic Peg Solitaire game
-Designed to let players navigate the board, jump pegs over one another, and try and leave as few pegs
remaining as possible

How to run it:
Download the PegSolitaire.jar from the releases page
Go to terminal and run game using command:
`java -jar PegSolitaire.jar`

Key Features:
-Fully playable Java logic
-Interactive, terminal base for user inputs and moves
-Makes sure user moves are valid
-Ensure players follow standard Peg Solitaire rules
-Game ends when computer finds that were is no moves possible or user enters quit

What I learned:
-How to use the split() method to parse and break down user string unput
-Using ArrayList to store and track valid destinations

What I struggled with:
-Handling invalid user inputs without causing the game to crash
-Figuring out the boundary conditions (isValidLocation()) and jump logic for valid peg movement
