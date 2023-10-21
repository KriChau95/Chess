package chess;
import java.lang.Math.*;
public class Bishop extends Piece{

    private String symbol = "B";
    public Bishop(boolean white){
        super(white);
        if (white){
            this.symbol = "w" + this.symbol;
        } else{
            this.symbol = "b" + this.symbol;
        }
    }

    public String toString(){
        return this.symbol;
    }

    @Override
    public boolean isValid(int startR, int startC, int endR, int endC, Board board) {

        int deltaR = endR - startR;
        int deltaC = endC - startC;
        if (Math.abs(deltaR) != Math.abs(deltaC)){ // check if it lies on the diagonal
            //System.out.println("illegal bishop move");
            return false;
        }
        // It does lie on the diagonal. Must make sure no pieces in the way
        if (deltaR > 0 && deltaC > 0){
            int j = startR + 1;
            for (int i = startC + 1; i <= endC-1; i++){
                if (board.board[j][i] != null){
                    //System.out.println("illegal bishop move");
                    return false;
                }
                j++;
            }
        }
        if (deltaR > 0 && deltaC < 0){
            int j = startR + 1;
            for (int i = startC - 1; i >= endC+1; i--){
                if (board.board[j][i] != null){
                    //System.out.println("illegal bishop move");
                    return false;
                }
                j++;
            }
        }
        if (deltaR < 0 && deltaC < 0){
            int j = startR - 1;
            for (int i = startC - 1; i >= endC+1; i--){
                if (board.board[j][i] != null){
                    //System.out.println("illegal bishop move");
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
        //System.out.println("legal bishop move");
        return true;
    }
}
