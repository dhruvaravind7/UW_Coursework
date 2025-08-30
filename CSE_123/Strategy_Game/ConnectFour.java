// Dhruv Aravind
// 10/16/2024
// CSE 123
// C1: Abstract Strategy Games
// TA: Laura Khotemlyansky

import java.util.*;

// A class to represent a game of Connect Four that extends the 
// AbstractStrategyGame class.

public class ConnectFour extends AbstractStrategyGame {
    private char[][] board;
    private boolean isXTurn;
    private final int BOARD_BOUNDS;

    // Constructs a new Connect Four game.
    public ConnectFour() {
        board = new char[][]{{'-', '-', '-', '-', '-', '-', '-'},
                             {'-', '-', '-', '-', '-', '-', '-'},
                             {'-', '-', '-', '-', '-', '-', '-'},
                             {'-', '-', '-', '-', '-', '-', '-'},
                             {'-', '-', '-', '-', '-', '-', '-'},
                             {'-', '-', '-', '-', '-', '-', '-'}};
        isXTurn = true;
        BOARD_BOUNDS = 3;
    }

    /* Behavior: 
     *  - Checks the board game to see if there is a winner or not.
     * Parameter:
     *  - N/A
     * Returns:
     *  - int: The winner of the game. 1 for player 1, 2 for player 2, 0 for a tie,
     *         and -1 if there is no winner yet.
     * Exceptions:
     *  - N/A
     */
    public int getWinner() {
        boolean fullBoard = true;
        for (int col = 0; col < this.board[0].length; col ++){
            if (this.board[0][col] == '-'){
                fullBoard = false;
            }
        }
        if (fullBoard){
            return (0);
        }

        // Check horizontal
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length - BOARD_BOUNDS; col++) {
                if (board[row][col] == board[row][col + 1] && 
                    board[row][col] == board[row][col + 2] &&  
                    board[row][col] == board[row][col + 3] &&
                    board[row][col] != '-') {
                        
                    return (board[row][col] == 'X' ? 1 : 2);
                }
            }
        }

        // Check vertical
        for (int col = 0; col < board[0].length; col++) {
            for (int row = 0; row < board.length - BOARD_BOUNDS; row++) {
                if (board[row][col] == board[row + 1][col] && 
                    board[row][col] == board[row + 2][col] && 
                    board[row][col] == board[row + 3][col] &&
                    board[row][col] != '-') {
                    return (board[row][col] == 'X' ? 1 : 2);
                }
            }
        }

        // Check diagonal (bottom-left to top-right)
        for (int row = BOARD_BOUNDS; row < board.length; row++) {
            for (int col = 0; col < board[0].length - BOARD_BOUNDS; col++) {
                if (board[row][col] == board[row - 1][col + 1] && 
                    board[row][col] == board[row - 2][col + 2] && 
                    board[row][col] == board[row - 3][col + 3] &&
                    board[row][col] != '-') {
                    return (board[row][col] == 'X' ? 1 : 2);
                }
            }
        }

        // Check diagonal (top-left to bottom-right)
        for (int row = 0; row < board.length - BOARD_BOUNDS; row++) {
            for (int col = 0; col < board[0].length - BOARD_BOUNDS; col++) {
                if (board[row][col] == board[row + 1][col + 1] && 
                    board[row][col] == board[row + 2][col + 2] && 
                    board[row][col] == board[row + 3][col + 3] &&
                    board[row][col] != '-') {
                    return (board[row][col] == 'X' ? 1 : 2);
                }
            }
        }
        return (-1);
    }

    /* Behavior:
     *  - Gets the player whose turn it is.
     * Parameters:
     *  - N/A
     * Returns: 
     *  - int: -1 if the game is over. 1 if it is player 1's turn and 2 if it player 2's turn. 
     * Exceptions:
     *  - N/A
     */
    public int getNextPlayer() {
        if (isGameOver()) {
            return -1;
        }
        return isXTurn ? 1 : 2;
    }

    /* Behavior:
     *  - Makes the move the player wants it to make by entering in the desired column number
     *    and adds it to the game board.
     * Parameter:
     *  - input: A scanner that takes in the user's inputted column
     *           Must be a number that is between 1 through 7 and not representing a column that 
     *           is filled
     * Returns:
     *  - N/A
     * Exceptions:
     *  - IllegalArgumentException(): If the column number selected is not between 1 through 7.
     *                                If the chosen column is already full. 
     */
    public void makeMove(Scanner input) {
        char currPlayer = isXTurn ? 'X' : 'O';

        System.out.print("Column? ");
        int col = input.nextInt();

        finishMove(col, currPlayer);
        isXTurn = !isXTurn;
    }

    /* Behavior:
     *  - Private helper method that checks if the move is valid and then adds it to the board 
     * Parameters:
     *  - col: The column that the user wants to place the piece in.
     *         Must be a number that is between 1 through 7 and not representing a column that 
     *         is filled
     *  - currPlayer: The character that represents which player is making the move. 
     *                X = Player 1 and O = Player 2.
     * Returns:
     *  - N/A
     * Exceptions:
     *  - IllegalArgumentException(): If the column number selected is not between 1 through 7.
     *                                If the chosen column is already full. 
     */
    private void finishMove(int col, char currPlayer) {
        if (col <= 0 || col > 7) {
            throw new IllegalArgumentException("Need to choose a column between 1 and 7");
        }
        if (this.board[0][col-1] != '-') {
            throw new IllegalArgumentException("Need to pick a column that is not full");
        }
        int currRow = -1;
        while (currRow < this.board.length-1 && this.board[currRow+1][col-1] == '-'){
            currRow++;
        }
        this.board[currRow][col-1] = currPlayer;
    }

    // Returns a String containing instructions to play the game.
    public String instructions() {
        String result = "";
        result += "Player 1 is X and goes first. Choose where to play by entering a column\n";
        result += "number, from 1 - 7. Spaces show as - in the 7x6 grid are empty. The game \n";
        result += "ends when a player marks four connected spaces in any direction, in which\n";
        result += " case that player wins, or when the board is full, the game ends in a tie\n";
        return result;
    }

    // Returns a String representation of the current state of the board.
    public String toString() {
        String result = "";
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                result += board[i][j] + " ";
            }
            result += "\n";
        }
        return result;
    }

    
}