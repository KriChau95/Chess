package chess;
import java.lang.Math.*;
import java.util.ArrayList;
public class Knight extends Piece{

    private String symbol = "N";
    public Knight(boolean white){
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

        if (Math.abs(endR - startR) == 2 && Math.abs(endC - startC) == 1){
            //System.out.println("legal knight move");
            return true;
        }
        if (Math.abs(endR - startR) == 1 && Math.abs(endC - startC) == 2){
            //System.out.println("legal knight move");
            return true;
        }
        //System.out.println("illegal knight move");
        return false;
    }



}
