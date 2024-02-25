package chess;
import java.lang.Math.*;

public class Bishop extends Piece{

    private String symbol = "B";

    // Constructor for Bishop class
    public Bishop(boolean white){
        super(white);
        // set the symbol based on the color
        if (white){
            this.symbol = "w" + this.symbol;
        } else{
            this.symbol = "b" + this.symbol;
        }
    }

    // Override toString method to return the symbol of the bishop
    public String toString(){
        return this.symbol;
    }
    
    // Override isValid method to validate bishop moves
    @Override
    public boolean isValid(int startR, int startC, int endR, int endC, Board board) {

        int deltaR = endR - startR;
        int deltaC = endC - startC;
        
        // check if move is diagonal
        if (Math.abs(deltaR) != Math.abs(deltaC)){ 
            return false;
        }
        
        // It does lie on the diagonal. Must make sure no pieces in the way
        if (deltaR > 0 && deltaC > 0){
            int j = startR + 1;
            for (int i = startC + 1; i <= endC-1; i++){
                if (board.board[j][i] != null){
                    return false;
                }
                j++;
            }
        }
        if (deltaR > 0 && deltaC < 0){
            int j = startR + 1;
            for (int i = startC - 1; i >= endC+1; i--){
                if (board.board[j][i] != null){
                    return false;
                }
                j++;
            }
        }
        if (deltaR < 0 && deltaC < 0){
            int j = startR - 1;
            for (int i = startC - 1; i >= endC+1; i--){
                if (board.board[j][i] != null){
                    return false;
                }
                j--;
            }
        }
        if (deltaR < 0 && deltaC > 0){
            int j = startR - 1;
            for (int i = startC + 1; i <= endC-1; i++){
                if (board.board[j][i] != null){
                    //System.out.println("illegal bishop move");
                    return false;
                }
                j--;
            }
        }
        // If code makes it to here, it is a legal bishop move.
        return true;
    }
}
