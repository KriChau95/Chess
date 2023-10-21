package chess;
import java.lang.Math.*;
public class King extends Piece{

    private String symbol = "K";
    public King(boolean white){
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

        //Check castling
        //Check if threatened
        //Check if within 1 space
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
                        if (board.board[r][c].isValid(r,c, 7 ,4, board)){
                            //System.out.println("can't castle");
                            return false;
                        }
                        if (board.board[r][c].isValid(r,c, 7 ,5, board)){
                            //System.out.println("can't castle");
                            return false;
                        }
                        if (board.board[r][c].isValid(r,c, 7 ,6, board)){
                            //System.out.println("can't castle");
                            return false;
                        }
                    }
                }
            }
            if (!(board.board[7][5] == null && board.board[7][6] == null)){
                //System.out.println("can't castle. pieces in the way");
                return false;
            }
            if (!(board.board[7][7] instanceof Rook && board.board[7][7].white)){
                //System.out.println("Must have white rook to castle with");
                return false;
            }
            return true;
        }

        if (startR == 7 && startC == 4 && endR == 7 && endC == 2 && board.wqs){ // white queen side castling attempt
            for (int r = 0; r < 8; r++){
                for (int c = 0; c < 8; c++){
                    if (board.board[r][c] != null && !(board.board[r][c].white)){ // this piece is black
                        if (board.board[r][c].isValid(r,c, 7 ,4, board)){
                            //System.out.println("can't castle");
                            return false;
                        }
                        if (board.board[r][c].isValid(r,c, 7 ,3, board)){
                            //System.out.println("can't castle");
                            return false;
                        }
                        if (board.board[r][c].isValid(r,c, 7 ,2, board)){
                            //System.out.println("can't castle");
                            return false;
                        }
                    }
                }
            }
            if (!(board.board[7][1] == null && board.board[7][2] == null && board.board[7][3] == null)){
                //System.out.println("can't castle. pieces in the way");
                return false;
            }
            if (!(board.board[7][0] instanceof Rook && board.board[7][0].white)){
                //System.out.println("Must have white rook to castle with");
                return false;
            }
            return true;
        }

        if (startR == 0 && startC == 4 && endR == 0 && endC == 6 && board.bks){ // black king side castling attempt
            for (int r = 0; r < 8; r++){
                for (int c = 0; c < 8; c++){
                    if (board.board[r][c] != null && board.board[r][c].white){ // this piece is white
                        if (board.board[r][c].isValid(r,c, 0 ,4, board)){
                            //System.out.println("can't castle");
                            return false;
                        }
                        if (board.board[r][c].isValid(r,c, 0 ,5, board)){
                            //System.out.println("can't castle");
                            return false;
                        }
                        if (board.board[r][c].isValid(r,c, 0 ,6, board)){
                            //System.out.println("can't castle");
                            return false;
                        }
                    }
                }
            }
            if (!(board.board[0][5] == null && board.board[0][6] == null)){
                //System.out.println("can't castle. pieces in the way");
                return false;
            }
            if (!(board.board[0][7] instanceof Rook && !(board.board[0][7].white))){
                //System.out.println("Must have black rook to castle with");
                return false;
            }
            return true;
        }

        if (startR == 0 && startC == 4 && endR == 0 && endC == 2 && board.bqs){ // black queen side castling attempt
            for (int r = 0; r < 8; r++){
                for (int c = 0; c < 8; c++){
                    if (board.board[r][c] != null && board.board[r][c].white){ // this piece is white
                        if (board.board[r][c].isValid(r,c, 0 ,4, board)){
                            //System.out.println("can't castle");
                            return false;
                        }
                        if (board.board[r][c].isValid(r,c, 0 ,3, board)){
                            //System.out.println("can't castle");
                            return false;
                        }
                        if (board.board[r][c].isValid(r,c, 0,2, board)){
                            //System.out.println("can't castle");
                            return false;
                        }
                    }
                }
            }
            if (!(board.board[0][1] == null && board.board[0][2] == null && board.board[0][3] == null)){
                //System.out.println("can't castle. pieces in the way");
                return false;
            }
            if (!(board.board[0][0] instanceof Rook && !(board.board[0][0].white))){
                //System.out.println("Must have black rook to castle with");
                return false;
            }
            return true;
        }

        //System.out.println("Illegal King Move");
        return false;
    }
}
