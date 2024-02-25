package chess;
import java.lang.Math.*;
import java.util.ArrayList;
public class Knight extends Piece{

    private String symbol = "N";

    // Constructor for Knight class
    public Knight(boolean white){
        super(white);
        if (white){
            this.symbol = "w" + this.symbol;
        } else{
            this.symbol = "b" + this.symbol;
        }
    }

    // Overrides toString method with appropriate display string
    public String toString(){
        return this.symbol;
    }

    @Override 
    // Overriding the Piece class' isValid method to check for valid Knight Move
    public boolean isValid(int startR, int startC, int endR, int endC, Board board) {

        if (Math.abs(endR - startR) == 2 && Math.abs(endC - startC) == 1){
            return true;
        }
        if (Math.abs(endR - startR) == 1 && Math.abs(endC - startC) == 2){
            return true;
        }
        // if we made it here, it must be an illegal knight move
        return false;
    }

}
