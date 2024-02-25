package chess;

// Abstract class to store common data for all Pieces and treat Pieces similarly
public abstract class Piece {
    
    public boolean white = true;
    public boolean firstMove = true;

    // abstract isValid method - to be implemented by child subclasses
    public abstract boolean isValid(int startR, int startC, int endR, int endC, Board board);

    // constructor that initializes piece color
    public Piece(boolean color){
        white = color;
    }
    
}
