package chess;

import java.util.ArrayList;
import java.util.Scanner;


// Main class that initiates Chess gameplay
public class PlayChess {

    public static void main(String[] args) {

        // Initialize a scanner
        Scanner sc = new Scanner(System.in);

        // invoke start method on Chess to initialize game variables and state
        Chess.start();
        String line = sc.nextLine();

        // game loop
        while (!line.equals("quit")) {

            // if game is reset, go back to starting variables and game state
            if (line.equals("reset")) {
                Chess.start();
                System.out.println();
                line = sc.nextLine();
                continue;
            }
            
            // move
            ReturnPlay res = Chess.play(line);

            // print result message
            if (res.message != null) {
                System.out.println("\n"+res.message);
            }
            System.out.println();

            // print result board
            printBoard(res.piecesOnBoard);

            System.out.println();

            // next line
            line = sc.nextLine();
        }
        sc.close();
    }

    // method that takes in an ArrayList of ReturnPieces and returns and 8x8 visual display of the chess board
    static void printBoard(ArrayList<ReturnPiece> pieces) {
        String[][] board = makeBlankBoard();
        if (pieces != null) {
            printPiecesOnBoard(pieces, board);
        }
        for (int r=0; r < 8; r++) {
            for (int c=0; c < 8; c++) {
                System.out.print(board[r][c] + " ");
            }
            System.out.println((8-r));
        }
        System.out.println(" a  b  c  d  e  f  g  h");
    }

    // a method that creates and returns a blank chess board
    static String[][] makeBlankBoard() {
        String[][] board = new String[8][8];
        for (int r=0; r < 8; r++) {
            for (int c=0; c < 8; c++) {
                if (r % 2 == 0) {
                    board[r][c] = c % 2 == 0 ? "  " : "##";
                } else {
                    board[r][c] = c % 2 == 0 ? "##" : "  ";
                }
            }
        }
        return board;
    }

    // method that prints all the pieces on the board
    static void printPiecesOnBoard(
            ArrayList<ReturnPiece> pieces, String[][] board) {
        for (ReturnPiece rp: pieces) {
            int file = (""+rp.pieceFile).charAt(0) - 'a';
            String pieceStr = "" + rp.pieceType;
            String ppstr = "";
            ppstr += Character.toLowerCase(pieceStr.charAt(0));
            ppstr += pieceStr.charAt(1) == 'P' ? 'p' : pieceStr.charAt(1);
            board[8-rp.pieceRank][file] = ppstr;
        }
    }

}
