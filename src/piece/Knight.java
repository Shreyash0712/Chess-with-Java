package piece;

public class Knight extends Piece {
    public Knight(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board) {
        int dx = Math.abs(fromRow - toRow);
        int dy = Math.abs(fromCol - toCol);

        if ((dx == 2 && dy == 1) || (dx == 1 && dy == 2)) {
            Piece target = board[toRow][toCol];
            return target == null || target.isWhite() != isWhite;
        }

        return false;
    }
}
