package chess;

public class Board {
    Piece[][] board;
    int[] whiteKingPosition;

    int[] blackKingPosition;

    String promoteTo = "";

    boolean wks = true;
    boolean wqs = true;
    boolean bks = true;
    boolean bqs = true;

    int[] enpassant;

    public Board(){
        board = new Piece[8][8];
        wks = true;
        wqs = true;
        bks = true;
        bqs = true;
        enpassant = new int[2];
        promoteTo = "";

        whiteKingPosition = new int[]{7,4};
        blackKingPosition = new int[]{0,4};

//        board[7][4] = new King(true);
//        board[7][0] = new Rook(true);
//        board[7][7] = new Rook(true);
//        board[7][3] = new Queen(true);
//
//        board[0][4] = new King(false);
//        board[0][0] = new Rook(false);
//        board[0][7] = new Rook(false);
//        board[0][3] = new Queen(false);


        board[7][0] = new Rook(true);
        board[7][1] = new Knight(true);
        board[7][2] = new Bishop(true);
        board[7][3] = new Queen(true);
        board[7][4] = new King(true);
        board[7][5] = new Bishop(true);
        board[7][6] = new Knight(true);
        board[7][7] = new Rook(true);

        board[0][0] = new Rook(false);
        board[0][1] = new Knight(false);
        board[0][2] = new Bishop(false);
        board[0][3] = new Queen(false);
        board[0][4] = new King(false);
        board[0][5] = new Bishop(false);
        board[0][6] = new Knight(false);
        board[0][7] = new Rook(false);
//
////         testing line - REMOVE LATER
////         board[2][6] = new Pawn(true);
////         board[5][1] = new Pawn(false);
//
        for (int c = 0; c < 8; c++){
            board[6][c] = new Pawn(true);
            board[1][c] = new Pawn(false);
        }


    }

    public void update(int sr, int sc, int er, int ec){
        enpassant[0] = 0;
        enpassant[1] = 0;
        if (board[sr][sc] instanceof King && Math.abs(ec-sc) > 1){ // castling happened
            if(sr == 7){ // white king
                if (ec == 6){ // kingside castling
                    //System.out.println("I RECOGNIZED CASTLING");
                    board[7][6] = new King(true);
                    board[7][4] = null;
                    board[7][5] = new Rook(true);
                    board[7][7] = null;
                } else if (ec == 2){ // queenside castling
                    board[7][2] = new King(true);
                    board[7][4] = null;
                    board[7][3] = new Rook(true);
                    board[7][0] = null;
                }
                wks = false;
                wqs = false;
            } else if (sr == 0){ // black king
                if (ec == 6){ // kingside castling
                    //System.out.println("I RECOGNIZED CASTLING");
                    board[0][6] = new King(false);
                    board[0][4] = null;
                    board[0][5] = new Rook(false);
                    board[0][7] = null;
                } else if (ec == 2){ // queenside castling
                    board[0][2] = new King(false);
                    board[0][4] = null;
                    board[0][3] = new Rook(false);
                    board[0][0] = null;
                }
                bks = false;
                bqs = false;
            }
            if (sr == whiteKingPosition[0] && sc == whiteKingPosition[1]){
                whiteKingPosition[0] = er;
                whiteKingPosition[1] = ec;
                wks = false;
                wqs = false;
            } else if (sr == blackKingPosition[0] && sc == blackKingPosition[1]){
                blackKingPosition[0] = er;
                blackKingPosition[1] = ec;
                bks = false;
                bqs = false;
            }
        }
        else if (promoteTo.length() > 0){ // handle promotion case
            if (promoteTo.equals("Q")){
                board[er][ec] = new Queen(board[sr][sc].white);
            } else if (promoteTo.equals("R")){
                board[er][ec] = new Rook(board[sr][sc].white);
            } else if (promoteTo.equals("N")){
                board[er][ec] = new Knight(board[sr][sc].white);
            } else if (promoteTo.equals("B")){
                board[er][ec] = new Bishop(board[sr][sc].white);
            }
            board[sr][sc] = null;
            promoteTo = "";
        } else if (board[sr][sc] instanceof Pawn && Math.abs(ec-sc) == 1 && board[er][ec] == null){ // enpassant
            if (board[sr][sc].white){
                board[er][ec] = board[sr][sc];
                board[sr][sc] = null;
                board[er+1][ec] = null;
            } else {
                board[er][ec] = board[sr][sc];
                board[sr][sc] = null;
                board[er-1][ec] = null;
            }
        } else if (board[sr][sc] instanceof Pawn && Math.abs(er - sr) == 2){ // modify enpassant array if 2 space pawn advance
            if (board[sr][sc].white){ // white pawn two space advance
                enpassant[0] = 5;
                enpassant[1] = sc;
            } else { // black pawn two space advance
                enpassant[0] = 2;
                enpassant[1] = sc;
            }
            board[er][ec] = board[sr][sc];
            board[sr][sc] = null;
        } else {
            board[er][ec] = board[sr][sc];
            board[sr][sc] = null;
            if (sr == whiteKingPosition[0] && sc == whiteKingPosition[1]){
                whiteKingPosition[0] = er;
                whiteKingPosition[1] = ec;
                wks = false;
                wqs = false;
            } else if (sr == blackKingPosition[0] && sc == blackKingPosition[1]){
                blackKingPosition[0] = er;
                blackKingPosition[1] = ec;
                bks = false;
                bqs = false;
            }
        }
        if ((sr == 7 && sc == 7) || (er == 7 && ec == 7)){ // movement of white king side rook
            wks = false;
        }
        if ((sr == 7 && sc == 0) || (er == 7 && ec == 0)){ // movement of white queen side rook
            wqs = false;
        }
        if ((sr == 0 && sc == 7) || (er == 0 && ec == 7)){ // movement of black king side rook
            bks = false;
        }
        if ((sr == 0 && sc == 0) || (er == 0 && ec == 0)){ // movement of black queen side rook
            bqs = false;
        }

    }

    public Board copy() {
        Board result = new Board();

        // Copy the board state
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] != null) {
                    if (board[i][j] instanceof Rook) {
                        result.board[i][j] = new Rook(((Rook) board[i][j]).white);
                    } else if (board[i][j] instanceof Knight) {
                        result.board[i][j] = new Knight(((Knight) board[i][j]).white);
                    } else if (board[i][j] instanceof Bishop) {
                        result.board[i][j] = new Bishop(((Bishop) board[i][j]).white);
                    } else if (board[i][j] instanceof Queen) {
                        result.board[i][j] = new Queen(((Queen) board[i][j]).white);
                    } else if (board[i][j] instanceof King) {
                        result.board[i][j] = new King(((King) board[i][j]).white);
                    } else if (board[i][j] instanceof Pawn) {
                        result.board[i][j] = new Pawn(((Pawn) board[i][j]).white);
                    }
                } else {
                    result.board[i][j] = null;
                }
            }
        }

        // Copy other fields
        result.whiteKingPosition[0] = whiteKingPosition[0];
        result.whiteKingPosition[1] = whiteKingPosition[1];

        result.blackKingPosition[0] = blackKingPosition[0];
        result.blackKingPosition[1] = blackKingPosition[1];

        result.promoteTo = promoteTo;
        result.enpassant[0] = enpassant[0];
        result.enpassant[1] = enpassant[1];

        result.wks = wks;
        result.wqs = wqs;
        result.bks = bks;
        result.bqs = bqs;

        return result;
    }


    public void printBoard(){
        for (int r = 0; r < 8; r++){
            for (int c = 0; c < 8; c++){
                if (board[r][c] == null){
                    System.out.print("-- ");
                } else {
                    System.out.print(board[r][c] + " ");
                }
            }
            System.out.println();
        }
    }
}
