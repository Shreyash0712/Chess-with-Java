package piece;

public class Pawn extends Piece {
    public Pawn(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board) {
        int dir = isWhite ? -1 : 1;
        int startRow = isWhite ? 6 : 1;

        Piece target = board[toRow][toCol];

        // Move forward
        if (fromCol == toCol) {
            if (toRow == fromRow + dir && target == null) return true;
            if (fromRow == startRow && toRow == fromRow + 2 * dir && target == null && board[fromRow + dir][fromCol] == null) {
                return true;
            }
        }

        // Capture
        return Math.abs(fromCol - toCol) == 1 && toRow == fromRow + dir && target != null && target.isWhite() != isWhite;
    }
}
