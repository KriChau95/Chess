package chess;
import java.lang.Math.*;
public class King extends Piece{

    private String symbol = "K";

    // Constructor to initialize King based on color
    public King(boolean white){
        super(white);
        if (white){
            this.symbol = "w" + this.symbol;
        } else{
            this.symbol = "b" + this.symbol;
        }
    }

    // Overrides toString method to return String representation of King
    public String toString(){
        return this.symbol;
    }

    @Override
    // Overriding the isValid method from Piece to determine if a king move is valid
    public boolean isValid(int startR, int startC, int endR, int endC, Board board) {

        // Ensure move is only 1 square from starting square
        if (Math.abs(endC-startC) <=1 && Math.abs(endR-startR) <=1){
            if (!(startR == endR && startC == endC)){
                firstMove = false;
                return true;
            }
        }

        // castling
        if (startR == 7 && startC == 4 && endR == 7 && endC == 6 && board.wks){ // white king side castling attempt
            for (int r = 0; r < 8; r++){
                for (int c = 0; c < 8; c++){
                    if (board.board[r][c] != null && !(board.board[r][c].white)){ // this piece is black
                        // checking if a black piece is threatening one of the squares the king must castle through
                        if (board.board[r][c].isValid(r,c, 7 ,4, board)){
                            return false;
                        }
                        if (board.board[r][c].isValid(r,c, 7 ,5, board)){
                            return false;
                        }
                        if (board.board[r][c].isValid(r,c, 7 ,6, board)){
                            return false;
                        }
                    }
                }
            }
            // checking if pieces in the way of castling attempt
            if (!(board.board[7][5] == null && board.board[7][6] == null)){
                return false;
            }
            // checking if corresponding rook exists
            if (!(board.board[7][7] instanceof Rook && board.board[7][7].white)){
                return false;
            }
            return true;
        }

        if (startR == 7 && startC == 4 && endR == 7 && endC == 2 && board.wqs){ // white queen side castling attempt
            for (int r = 0; r < 8; r++){
                for (int c = 0; c < 8; c++){
                    if (board.board[r][c] != null && !(board.board[r][c].white)){ // this piece is black
                        // checking if a black piece is threatening one of the squares the king must castle through
                        if (board.board[r][c].isValid(r,c, 7 ,4, board)){
                            return false;
                        }
                        if (board.board[r][c].isValid(r,c, 7 ,3, board)){
                            return false;
                        }
                        if (board.board[r][c].isValid(r,c, 7 ,2, board)){
                            return false;
                        }
                    }
                }
            }
            // checking if pieces in the way of castle
            if (!(board.board[7][1] == null && board.board[7][2] == null && board.board[7][3] == null)){
                return false;
            }
            // checking if corresponding rook exists
            if (!(board.board[7][0] instanceof Rook && board.board[7][0].white)){
                return false;
            }
            return true;
        }

        if (startR == 0 && startC == 4 && endR == 0 && endC == 6 && board.bks){ // black king side castling attempt
            for (int r = 0; r < 8; r++){
                for (int c = 0; c < 8; c++){
                    // checking if a white piece is threatening one of the squares the king must castle through
                    if (board.board[r][c] != null && board.board[r][c].white){ // this piece is white
                        if (board.board[r][c].isValid(r,c, 0 ,4, board)){
                            return false;
                        }
                        if (board.board[r][c].isValid(r,c, 0 ,5, board)){
                            return false;
                        }
                        if (board.board[r][c].isValid(r,c, 0 ,6, board)){
                            return false;
                        }
                    }
                }
            }
            // checking if piece in the way
            if (!(board.board[0][5] == null && board.board[0][6] == null)){
                return false;
            }
            // checking if corresponding rook exists
            if (!(board.board[0][7] instanceof Rook && !(board.board[0][7].white))){
                return false;
            }
            return true;
        }

        if (startR == 0 && startC == 4 && endR == 0 && endC == 2 && board.bqs){ // black queen side castling attempt
            for (int r = 0; r < 8; r++){
                for (int c = 0; c < 8; c++){
                    if (board.board[r][c] != null && board.board[r][c].white){ // this piece is white
                        // checking if a white piece is threatening one of the squares the king must castle through
                        if (board.board[r][c].isValid(r,c, 0 ,4, board)){
                            return false;
                        }
                        if (board.board[r][c].isValid(r,c, 0 ,3, board)){
                            return false;
                        }
                        if (board.board[r][c].isValid(r,c, 0,2, board)){
                            return false;
                        }
                    }
                }
            }
            // checking if pieces in the way of castling
            if (!(board.board[0][1] == null && board.board[0][2] == null && board.board[0][3] == null)){
                return false;
            }
            // checking if corresponding rook exists
            if (!(board.board[0][0] instanceof Rook && !(board.board[0][0].white))){
                return false;
            }
            return true;
        }

        // If we have made it to here, then all the legal king rook moves have been checked for, and it must be an illegal king move
        return false;
    }
}
