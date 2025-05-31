package piece;

public class King extends Piece {
    public King(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board) {
        int dx = Math.abs(toRow - fromRow);
        int dy = Math.abs(toCol - fromCol);

        // Only allow 1-square move in any direction
        if (dx <= 1 && dy <= 1 && (dx != 0 || dy != 0)) {
            Piece target = board[toRow][toCol];
            return target == null || target.isWhite() != isWhite;
        }

        return false;
    }

}
