package chess;

public abstract class Piece {
    public boolean white = true;
    public boolean firstMove = true;
    public abstract boolean isValid(int startR, int startC, int endR, int endC, Board board);
    public Piece(boolean color){
        white = color;
    }
}
