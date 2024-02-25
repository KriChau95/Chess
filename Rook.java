package chess;

import java.lang.Math.*;

public class Rook extends Piece{

    private String symbol = "R";

    // Constructor for Rook
    public Rook(boolean white){
        super(white);
        if (white){
            this.symbol = "w" + this.symbol;
        } else{
            this.symbol = "b" + this.symbol;
        }
    }

    // Overriding toString method to return string representation of Rook
    public String toString(){
        return this.symbol;
    }

    @Override
    // Overriding Piece class' isValid method to determine if valid Rook Move
    public boolean isValid(int startR, int startC, int endR, int endC, Board board) {

        if (startR != endR && startC != endC) {
            // Illegal rook move - must move along rank/file
            return false;
        }

        int rowIncrement = (startR != endR) ? ((endR > startR) ? 1 : -1) : 0;
        int colIncrement = (startC != endC) ? ((endC > startC) ? 1 : -1) : 0;

        for (int i = startR + rowIncrement, j = startC + colIncrement; i != endR || j != endC; i += rowIncrement, j += colIncrement) {
            if (board.board[i][j] != null) {
                // Illegal rook move - piece in the way
                return false;
            }
        }

        // legal rook move
        return true;

    }
}
