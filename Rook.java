package chess;
import java.lang.Math.*;
public class Rook extends Piece{

    private String symbol = "R";
    public Rook(boolean white){
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

        if (startR != endR && startC != endC) {
            //System.out.println("Illegal rook move: Must move along a rank or file.");
            return false;
        }

        int rowIncrement = (startR != endR) ? ((endR > startR) ? 1 : -1) : 0;
        int colIncrement = (startC != endC) ? ((endC > startC) ? 1 : -1) : 0;

        for (int i = startR + rowIncrement, j = startC + colIncrement; i != endR || j != endC; i += rowIncrement, j += colIncrement) {
            if (board.board[i][j] != null) {
                //System.out.println("Illegal rook move: Piece in the way.");
                return false;
            }
        }

        //System.out.println("Legal rook move");
        return true;

    }
}
