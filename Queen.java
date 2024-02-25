package chess;

import java.lang.Math.*;

public class Queen extends Piece{

    private String symbol = "Q";

    // Constructor for Queen class
    public Queen(boolean white){
        super(white);
        if (white){
            this.symbol = "w" + this.symbol;
        } else{
            this.symbol = "b" + this.symbol;
        }
    }

    // Overrides toString method and returns corresponding Queen string representation
    public String toString(){
        return this.symbol;
    }

    @Override
    // Overrides Piece class' isValid method to determine legal queen move
    public boolean isValid(int startR, int startC, int endR, int endC, Board board) {

        int deltaR = endR - startR;
        int deltaC = endC - startC;

        if (Math.abs(deltaR) != Math.abs(deltaC)){ // check if it does not lie on the diagonal
            // check if it might be an orthogonal move
            if (startR != endR && startC != endC) {
                // illegal motion in terms of direction
                return false;
            }

            int rowIncrement = (startR != endR) ? ((endR > startR) ? 1 : -1) : 0;
            int colIncrement = (startC != endC) ? ((endC > startC) ? 1 : -1) : 0;
            
            // checking if piece in the way of move
            for (int i = startR + rowIncrement, j = startC + colIncrement; i != endR || j != endC; i += rowIncrement, j += colIncrement) {
                if (board.board[i][j] != null) {
                    // Illegal orthogonal move: Piece in the way
                    return false;
                }
            }
            // legal orthogonal move
            return true;
        }
        
        // It does lie on the diagonal. Must make sure no pieces in the way
        if (deltaR > 0 && deltaC > 0){
            int j = startR + 1;
            for (int i = startC + 1; i <= endC-1; i++){
                if (board.board[j][i] != null){
                    // illegal diagonal move
                    return false;
                }
                j++;
            }
        }
        
        if (deltaR > 0 && deltaC < 0){
            int j = startR + 1;
            for (int i = startC - 1; i >= endC+1; i--){
                if (board.board[j][i] != null){
                    // illegal diagaonal move
                    return false;
                }
                j++;
            }
        }
        
        if (deltaR < 0 && deltaC < 0){
            int j = startR - 1;
            for (int i = startC - 1; i >= endC+1; i--){
                if (board.board[j][i] != null){
                    // illegal diagaonal move
                    return false;
                }
                j--;
            }
        }
        
        if (deltaR < 0 && deltaC > 0){
            int j = startR - 1;
            for (int i = startC + 1; i <= endC-1; i++){
                if (board.board[j][i] != null){
                    illegal diagonal move
                    return false;
                }
                j--;
            }
        }
        // legal diagonal move
        return true;
    }
}
