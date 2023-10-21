package chess;

public class Pawn extends Piece{
    private String symbol = "p";
    public Pawn (boolean white){
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
    public boolean isValid(int startR, int startC, int endR, int endC, Board board) {

        // enpassant

        if (board.board[startR][startC].white){ // white pawn trying en passant
            if (startR == 3 && endR == 2){
                if (Math.abs(endC - startC) == 1){
                    if(board.board[endR][endC] == null){
                        if (endR == board.enpassant[0] && endC == board.enpassant[1]){
                            return true;
                        }
                    }
                }
            }
        }

        if (!(board.board[startR][startC].white)){ // black pawn trying en passant
            if (startR == 4 && endR == 5){
                if (Math.abs(endC - startC) == 1){
                    if(board.board[endR][endC] == null){
                        if (endR == board.enpassant[0] && endC == board.enpassant[1]){
                            return true;
                        }
                    }
                }
            }
        }

        if (board.board[startR][startC].white){ // white pawn
            if (endR == startR - 1){ //  move results in one row advanced
                if (endC == startC){ // move one space straight forward
                    if(board.board[endR][endC] == null){ // make sure there is room - empty space
                        //System.out.println("White pawn advance by one");
                        return true;
                        // consider promotion case
                    } else {
                        //System.out.println("no room for one space white pawn advance");
                        return false;
                    }
                } else if(Math.abs(endC - startC) == 1){ // attempt at diagonal capture one row forward
                    if (board.board[endR][endC] != null && !(board.board[endR][endC].white)){ // if not empty and black piece
                        //System.out.println("White pawn captured smth");
                        return true;
                        // consider promotion case
                    } else {
                        //System.out.println("White pawn can't diagonally move into ally/empty square");
                        return false;
                    }
                } else {
                    //System.out.println("White pawn cannot teleport like that");
                    return false;
                }
            } else if (endR == startR - 2 && startC == endC){ // attempting 2 space pawn advance - only allowed on first move
                if (startR == 6){ // on appropriate white starting row
                    if (board.board[startR-1][startC] == null && board.board[endR][endC] == null){ // clear path
                        //System.out.println("White pawn 2 space advance");
                        return true;
                        // consider promotion case
                    } else {
                        //System.out.println("pieces in the way of white pawn 2 space advance");
                        return false;
                    }
                } else {
                    //System.out.println("White pawn can only move 2 forward on starting row");
                    return false;
                }
            } else {
                //System.out.println("Some other illegal white pawn move");
                return false;
            }

        }
        else{ // black pawn
            if (endR == startR + 1){ //  move results in one row advanced
                if (endC == startC){ // move one space straight forward
                    if(board.board[endR][endC] == null){ // make sure there is room - empty space
                        //System.out.println("Black pawn advance by one");
                        return true;
                        // consider promotion case
                    } else {
                        //System.out.println("no room for one space black pawn advance");
                        return false;
                    }
                } else if(Math.abs(endC - startC) == 1){ // attempt at diagonal capture one row forward
                    if (board.board[endR][endC] != null && board.board[endR][endC].white){ // if not empty and white piece
                        //System.out.println("Black pawn captured smth");
                        return true;
                        // consider promotion case
                    } else {
                        //System.out.println("Black pawn can't diagonally move into ally/empty square");
                        return false;
                    }
                } else {
                    //System.out.println("Black pawn cannot teleport like that");
                    return false;
                }
            } else if (endR == startR + 2 && startC == endC){ // attempting 2 space pawn advance - only allowed on first move
                if (startR == 1){ // on appropriate black starting row
                    if (board.board[startR+1][startC] == null && board.board[endR][endC] == null){ // clear path
                        //System.out.println("Black pawn 2 space advance");
                        return true;
                        // consider promotion case
                    } else {
                        //System.out.println("pieces in the way of black pawn 2 space advance");
                        return false;
                    }
                } else {
                    //System.out.println("Black pawn can only move 2 forward on starting row");
                    return false;
                }
            }  else {
                //System.out.println("Some other illegal black pawn move");
                return false;
            }
        }

    }
}
