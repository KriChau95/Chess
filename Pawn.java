package chess;

public class Pawn extends Piece{
    private String symbol = "p";

    // Pawn class constructor
    public Pawn (boolean white){
        super(white);
        if (white){
            this.symbol = "w" + this.symbol;
        } else{
            this.symbol = "b" + this.symbol;
        }
    }

    // Returns string to represent pawn
    public String toString(){
        return this.symbol;
    }

    // Overriding the Piece class' isValid method to check for legal pawn moves
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
                        return true;
                    } else { // no room for one space white pawn advance
                        return false;
                    }
                } else if(Math.abs(endC - startC) == 1){ // attempt at diagonal capture one row forward
                    if (board.board[endR][endC] != null && !(board.board[endR][endC].white)){ // if not empty and black piece
                        // white pawn successfully captured something
                        return true;
                    } else {
                        // white pawn trying to diagonally move into ally/empty space
                        return false;
                    }
                } else {
                    // other illegal white pawn motion
                    return false;
                }
            } else if (endR == startR - 2 && startC == endC){ // attempting 2 space pawn advance - only allowed on first move
                if (startR == 6){ // on appropriate white starting row
                    if (board.board[startR-1][startC] == null && board.board[endR][endC] == null){ // clear path
                        return true;
                    } else {
                        // pieces in the way of white pawn 2 space advance
                        return false;
                    }
                } else {
                    // Some other illegal pawn move
                    return false;
                }
            } else {
                // Some other illegal pawn move
                return false;
            }

        }
        else{ // black pawn
            if (endR == startR + 1){ //  move results in one row advanced
                if (endC == startC){ // move one space straight forward
                    if(board.board[endR][endC] == null){ // make sure there is room - empty space
                        return true;
                    } else {
                        // no room for one space black pawn advance
                        return false;
                    }
                } else if(Math.abs(endC - startC) == 1){ // attempt at diagonal capture one row forward
                    if (board.board[endR][endC] != null && board.board[endR][endC].white){ // if not empty and white piece
                        // black pawn successful capture
                        return true;
                    } else {
                        // black pawn can't diagonally move into ally/empty square
                        return false;
                    }
                } else {
                    // illegal black pawn move
                    return false;
                }
            } else if (endR == startR + 2 && startC == endC){ // attempting 2 space pawn advance - only allowed on first move
                if (startR == 1){ // on appropriate black starting row
                    if (board.board[startR+1][startC] == null && board.board[endR][endC] == null){ // clear path
                        // successful black pawn 2 space advance
                        return true;
                    } else {
                        // pieces in the way of black pawn 2 space advance
                        return false;
                    }
                } else {
                    // some other illegal black pawn move;
                    return false;
                }
            }  else {
                // some other illegal black pawn move;
                return false;
            }
        }

    }
}
