// Krishaan Chaudhary & Preston Clawson

package chess;

import java.util.ArrayList;

class ReturnPiece {
    static enum PieceType {WP, WR, WN, WB, WQ, WK,
        BP, BR, BN, BB, BK, BQ};
    static enum PieceFile {a, b, c, d, e, f, g, h};

    PieceType pieceType;
    PieceFile pieceFile;
    int pieceRank;  // 1..8
    public String toString() {
        return ""+pieceFile+pieceRank+":"+pieceType;
    }
    public boolean equals(Object other) {
        if (other == null || !(other instanceof ReturnPiece)) {
            return false;
        }
        ReturnPiece otherPiece = (ReturnPiece)other;
        return pieceType == otherPiece.pieceType &&
                pieceFile == otherPiece.pieceFile &&
                pieceRank == otherPiece.pieceRank;
    }
}

class ReturnPlay {
    enum Message {ILLEGAL_MOVE, DRAW,
        RESIGN_BLACK_WINS, RESIGN_WHITE_WINS,
        CHECK, CHECKMATE_BLACK_WINS,	CHECKMATE_WHITE_WINS,
        STALEMATE};

    ArrayList<ReturnPiece> piecesOnBoard;
    Message message;
}

public class Chess {

    public static ArrayList<ReturnPiece> myPieces;
    public static Board myBoard;

    public static boolean turn = true; // true if white, false if black

    enum Player { white, black }

    /**
     * Plays the next move for whichever player has the turn.
     *
     * @param move String for next move, e.g. "a2 a3"
     *
     * @return A ReturnPlay instance that contains the result of the move.
     *         See the section "The Chess class" in the assignment description for details of
     *         the contents of the returned ReturnPlay instance.
     */
    public static ReturnPlay play(String move) {

        // removing all whitespace in the move so it can be processed
        String newStr = "";
        for (int i = 0; i < move.length(); i++){
            if (move.charAt(i) != ' '){
                newStr += move.charAt(i);
            }
        }

        // if move is resign, end game - this takes precedence over all other possible inputs
        if (newStr.equals("resign")){
            ReturnPlay result = new ReturnPlay();
            if (turn){
                result.message = ReturnPlay.Message.RESIGN_BLACK_WINS;
            } else {
                result.message = ReturnPlay.Message.RESIGN_WHITE_WINS;
            }
            result.piecesOnBoard = myPieces;
            return result;
        }

        // variables to that will hold the integer values that will represent the move for our Board object
        int mStartRow;
        int mStartCol;
        int mEndRow;
        int mEndCol;

        boolean drawRequested = newStr.contains("draw?");
        boolean pawnPromotion = newStr.contains("Q") || newStr.contains("R") || newStr.contains("B") || newStr.contains("N");

        // convert move from rank file notation to our Board (0-7 for columns left to right, 0-7 for rows top to bottom)
        mStartRow = 8 - Character.getNumericValue(newStr.charAt(1));
        mStartCol = newStr.charAt(0) - 'a';
        mEndRow = 8 - Character.getNumericValue(newStr.charAt(3));
        mEndCol = newStr.charAt(2) - 'a';

        // temporary display print statement for debugging
        //System.out.println("" + mStartRow + " " + mStartCol + " " + mEndRow + " " + mEndCol);

        // Addressing illegal turns - 1. start cell is empty, 2. start cell has opponents piece, 3. end cell has allied piece
        if (myBoard.board[mStartRow][mStartCol] == null){ // 1. start cell is empty
            ReturnPlay result = new ReturnPlay();
            result.piecesOnBoard = myPieces;
            result.message = ReturnPlay.Message.ILLEGAL_MOVE;
            //System.out.println("no piece there");
            return result;
        }

        if (myBoard.board[mStartRow][mStartCol].white != turn){ // 2. start cell has opponents piece
            ReturnPlay result = new ReturnPlay();
            result.piecesOnBoard = myPieces;
            result.message = ReturnPlay.Message.ILLEGAL_MOVE;
            //System.out.println("not your turn");
            return result;
        }

        if (myBoard.board[mEndRow][mEndCol] != null){
            if(myBoard.board[mStartRow][mStartCol].white == myBoard.board[mEndRow][mEndCol].white){ // 3. end cell has allied piece
                ReturnPlay result = new ReturnPlay();
                result.piecesOnBoard = myPieces;
                result.message = ReturnPlay.Message.ILLEGAL_MOVE;
                //System.out.println("friendly fire");
                return result;
            }
        }

        boolean validMove = true;

        // legal turn
        if (myBoard.board[mStartRow][mStartCol].isValid(mStartRow, mStartCol, mEndRow, mEndCol, myBoard)){ // using isValid methods for each piece to check validity of move

            Board testBoard = myBoard.copy();
            testBoard.update(mStartRow, mStartCol, mEndRow, mEndCol); // update the Board so it represents the moved piece

//            System.out.println("test board-------");
//            for (int r = 0; r < 8; r++){
//                for (int c = 0; c < 8; c++){
//                    if (testBoard.board[r][c] == null){
//                        System.out.print("-- ");
//                    } else {
//                        System.out.print(testBoard.board[r][c] + " ");
//                    }
//                }
//                System.out.println();
//            }
//            System.out.println("----------------");


            if(turn){ // white tried to endanger themselves
                for (int r = 0; r < 8; r++){
                    for (int c = 0; c < 8; c++){
                        if (testBoard.board[r][c] != null && !(testBoard.board[r][c].white)){
                            if(testBoard.board[r][c].isValid(r,c,testBoard.whiteKingPosition[0], testBoard.whiteKingPosition[1], testBoard)){
                                ReturnPlay result = new ReturnPlay();
                                result.piecesOnBoard = myPieces;
                                result.message = ReturnPlay.Message.ILLEGAL_MOVE;
                                //System.out.println("white tried self-endangerment");
                                return result;
                            }
                        }
                    }
                }
            } else { // black tried to endanger themselves
                for (int r = 0; r < 8; r++){
                    for (int c = 0; c < 8; c++){
                        if (testBoard.board[r][c] != null && testBoard.board[r][c].white){
                            if(testBoard.board[r][c].isValid(r,c,testBoard.blackKingPosition[0], testBoard.blackKingPosition[1], testBoard)){
                                ReturnPlay result = new ReturnPlay();
                                result.piecesOnBoard = myPieces;
                                result.message = ReturnPlay.Message.ILLEGAL_MOVE;
                                //System.out.println("black tried self-endangerment");
                                return result;
                            }
                        }
                    }
                }
            }

            if (mStartRow == 1 && mEndRow == 0 && myBoard.board[mStartRow][mStartCol] instanceof Pawn){
                pawnPromotion = true;
            }

            if (mStartRow == 6 && mEndRow == 7 && myBoard.board[mStartRow][mStartCol] instanceof Pawn){
                pawnPromotion = true;
            }

            // if we got to here, it is a legal move unless trying to promote on a non promoting row
            if (pawnPromotion){
                if (turn){ // white
                    if (mStartRow == 1 && mEndRow == 0 && myBoard.board[mStartRow][mStartCol] instanceof Pawn){ // if white pawn moving to end row
                        if (newStr.contains("Q") || newStr.contains("R") || newStr.contains("B") || newStr.contains("N")){
                            myBoard.promoteTo = newStr.charAt(4) + "";
                        } else {
                            myBoard.promoteTo = "Q";
                        }
                        //System.out.println("white Pawn Promotion");
                    } else {
                        ReturnPlay result = new ReturnPlay();
                        result.piecesOnBoard = myPieces;
                        result.message = ReturnPlay.Message.ILLEGAL_MOVE;
                        //System.out.println("white can't promote that");
                        return result;
                    }

                } else { // black
                    if (mStartRow == 6 && mEndRow == 7 && myBoard.board[mStartRow][mStartCol] instanceof Pawn){ // if black pawn moving to end row
                        if (newStr.contains("Q") || newStr.contains("R") || newStr.contains("B") || newStr.contains("N")){
                            myBoard.promoteTo = newStr.charAt(4) + "";
                        } else {
                            myBoard.promoteTo = "Q";
                        }
                        //System.out.println("black Pawn Promotion");
                    } else {
                        ReturnPlay result = new ReturnPlay();
                        result.piecesOnBoard = myPieces;
                        result.message = ReturnPlay.Message.ILLEGAL_MOVE;
                        //System.out.println("black can't promote that");
                        return result;
                    }
                }
            }

            myBoard.update(mStartRow, mStartCol, mEndRow, mEndCol); // update the Board so it represents the moved piece

            boolean whiteInCheck = false;
            boolean blackInCheck = false;

            // check if white checked black
            if (turn){
                for (int r = 0; r < 8; r++){
                    for (int c = 0; c < 8; c++){
                        if (myBoard.board[r][c] != null && myBoard.board[r][c].white){ // this piece is white
                            if (myBoard.board[r][c].isValid(r,c, myBoard.blackKingPosition[0], myBoard.blackKingPosition[1], myBoard)){
                                blackInCheck = true;
                            }
                        }
                    }
                }
            } else {
                for (int r = 0; r < 8; r++){
                    for (int c = 0; c < 8; c++){
                        if (myBoard.board[r][c] != null && !(myBoard.board[r][c].white)){ // this piece is black
                            if (myBoard.board[r][c].isValid(r,c, myBoard.whiteKingPosition[0], myBoard.whiteKingPosition[1], myBoard)){
                                whiteInCheck = true;
                            }
                        }
                    }
                }
            }


            //System.out.println("White king at " + myBoard.whiteKingPosition[0] + ", " + myBoard.whiteKingPosition[1]);
            //System.out.println("Black king at " + myBoard.blackKingPosition[0] + ", " + myBoard.blackKingPosition[1]);
            ReturnPlay result = updateReturnPlay(); // call a method that updates myPieces, and returns the ReturnPlay object
            if (drawRequested){
                result.message = ReturnPlay.Message.DRAW;
            } else if (blackInCheck){
                //System.out.println("I checked for black in checkmate");
                if (isCheckmate(false)){
                    result.message = ReturnPlay.Message.CHECKMATE_WHITE_WINS;
                } else {
                    result.message = ReturnPlay.Message.CHECK;
                }
            } else if (whiteInCheck){
                //System.out.println("I checked for white in checkmate");
                if (isCheckmate(true)){
                    result.message = ReturnPlay.Message.CHECKMATE_BLACK_WINS;
                } else {
                    result.message = ReturnPlay.Message.CHECK;
                }
            } else {
                result.message = null;
            }

            turn = !turn; // update to next players turn
            if (turn){
                //System.out.println("white's turn");
            } else{
                //System.out.println("black's turn");
            }

            return result;
        } else{
            ReturnPlay result = new ReturnPlay();
            result.piecesOnBoard = myPieces;
            result.message = ReturnPlay.Message.ILLEGAL_MOVE;
            return result;
        }

    }

    public static boolean isCheckmate(boolean whiteTurn){
        for (int sr = 0; sr < 8; sr++){
            for (int sc = 0; sc < 8; sc++){
                if (myBoard.board[sr][sc] != null && myBoard.board[sr][sc].white == whiteTurn){
                    for (int er = 0; er < 8; er++){
                        for (int ec = 0; ec < 8; ec++){
                            if (myBoard.board[er][ec] == null || myBoard.board[er][ec].white != whiteTurn) {
                                if (myBoard.board[sr][sc].isValid(sr, sc, er, ec, myBoard)) {
                                    Board testBoard = myBoard.copy();
//                                    //System.out.println("test board before update-------");
//                                    for (int r = 0; r < 8; r++) {
//                                        for (int c = 0; c < 8; c++) {
//                                            if (testBoard.board[r][c] == null) {
//                                                System.out.print("-- ");
//                                            } else {
//                                                System.out.print(testBoard.board[r][c] + " ");
//                                            }
//                                        }
//                                        System.out.println();
//                                    }
//                                    System.out.println("----------------");
                                    testBoard.update(sr, sc, er, ec);
                                    if (!isChecked(whiteTurn, testBoard)) {
//                                        System.out.println("test board-------");
//                                        for (int r = 0; r < 8; r++) {
//                                            for (int c = 0; c < 8; c++) {
//                                                if (testBoard.board[r][c] == null) {
//                                                    System.out.print("-- ");
//                                                } else {
//                                                    System.out.print(testBoard.board[r][c] + " ");
//                                                }
//                                            }
//                                            System.out.println();
//                                        }
//                                        System.out.println("----------------");
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    public static boolean isChecked(boolean whiteTurn, Board board){
        int kingRow = whiteTurn ? board.whiteKingPosition[0] : board.blackKingPosition[0];
        int kingCol = whiteTurn ? board.whiteKingPosition[1] : board.blackKingPosition[1];

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (board.board[row][col] != null && board.board[row][col].white != whiteTurn) {
                    if (board.board[row][col].isValid(row, col, kingRow, kingCol, board)) {
                        return true; // The king is in check
                    }
                }
            }
        }

        return false; // The king is not in check
    }

    // method that loops through myBoard.board to create a ReturnPlay object that encapsulates all the information on the board
    private static ReturnPlay updateReturnPlay() {
        ReturnPlay play = new ReturnPlay();
        play.piecesOnBoard = new ArrayList<>();

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if (myBoard.board[r][c] != null) {
                    ReturnPiece piece = new ReturnPiece();
                    piece.pieceType = getPieceType(myBoard.board[r][c]);
                    piece.pieceFile = ReturnPiece.PieceFile.values()[c];
                    piece.pieceRank = 8 - r;
                    play.piecesOnBoard.add(piece);
                }
            }
        }
        myPieces = play.piecesOnBoard;
        return play;

    }

    // helper method for updateReturnPlay()
    private static ReturnPiece.PieceType getPieceType(Piece piece) {
        if (piece instanceof Pawn) {
            return piece.white ? ReturnPiece.PieceType.WP : ReturnPiece.PieceType.BP;
        } else if (piece instanceof Rook) {
            return piece.white ? ReturnPiece.PieceType.WR : ReturnPiece.PieceType.BR;
        } else if (piece instanceof Knight) {
            return piece.white ? ReturnPiece.PieceType.WN : ReturnPiece.PieceType.BN;
        } else if (piece instanceof Bishop) {
            return piece.white ? ReturnPiece.PieceType.WB : ReturnPiece.PieceType.BB;
        } else if (piece instanceof Queen) {
            return piece.white ? ReturnPiece.PieceType.WQ : ReturnPiece.PieceType.BQ;
        } else if (piece instanceof King) {
            return piece.white ? ReturnPiece.PieceType.WK : ReturnPiece.PieceType.BK;
        } else {
            // Handle unknown piece type
            return null;
        }
    }

    /**
     * This method should reset the game, and start from scratch.
     */
    public static void start() {
        myPieces = new ArrayList<ReturnPiece>();
        myBoard = new Board();
        turn = true;

        ReturnPiece.PieceType[] whitePieceTypes = {
                ReturnPiece.PieceType.WR, ReturnPiece.PieceType.WN, ReturnPiece.PieceType.WB,
                ReturnPiece.PieceType.WQ, ReturnPiece.PieceType.WK, ReturnPiece.PieceType.WB,
                ReturnPiece.PieceType.WN, ReturnPiece.PieceType.WR
        };

        ReturnPiece.PieceType[] blackPieceTypes = {
                ReturnPiece.PieceType.BR, ReturnPiece.PieceType.BN, ReturnPiece.PieceType.BB,
                ReturnPiece.PieceType.BQ, ReturnPiece.PieceType.BK, ReturnPiece.PieceType.BB,
                ReturnPiece.PieceType.BN, ReturnPiece.PieceType.BR
        };

        ReturnPiece.PieceFile[] pieceFiles = {
                ReturnPiece.PieceFile.a, ReturnPiece.PieceFile.b, ReturnPiece.PieceFile.c,
                ReturnPiece.PieceFile.d, ReturnPiece.PieceFile.e, ReturnPiece.PieceFile.f,
                ReturnPiece.PieceFile.g, ReturnPiece.PieceFile.h
        };


        //Initializing white back row
        int rank = 1;

        for (int i = 0; i < 8; i++){
            ReturnPiece piece = new ReturnPiece();
            piece.pieceType = whitePieceTypes[i];
            piece.pieceFile = pieceFiles[i];
            piece.pieceRank = rank;
            myPieces.add(piece);
        }

        //Initializing black back row
        rank = 8;

        for (int i = 0; i < 8; i++){
            ReturnPiece piece = new ReturnPiece();
            piece.pieceType = blackPieceTypes[i];
            piece.pieceFile = pieceFiles[i];
            piece.pieceRank = rank;
            myPieces.add(piece);
        }

        // Initializing white pawns
        rank = 2;
        for (int i = 0; i < 8; i++){
            ReturnPiece pawn = new ReturnPiece();
            pawn.pieceType = ReturnPiece.PieceType.WP;
            pawn.pieceFile = ReturnPiece.PieceFile.values()[i];
            pawn.pieceRank = rank;
            myPieces.add(pawn);
        }

        // Initializing black pawns
        rank = 7;
        for (int i = 0; i < 8; i++){
            ReturnPiece pawn = new ReturnPiece();
            pawn.pieceType = ReturnPiece.PieceType.BP;
            pawn.pieceFile = ReturnPiece.PieceFile.values()[i];
            pawn.pieceRank = rank;
            myPieces.add(pawn);
        }

    }

}
