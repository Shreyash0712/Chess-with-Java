package piece;

public class Bishop extends Piece {
    public Bishop(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board) {
        int dx = toRow - fromRow;
        int dy = toCol - fromCol;

        if (Math.abs(dx) != Math.abs(dy)) {
            return false;
        }

        int stepX = dx > 0 ? 1 : -1;
        int stepY = dy > 0 ? 1 : -1;

        int x = fromRow + stepX;
        int y = fromCol + stepY;

        while (x != toRow && y != toCol) {
            if (x < 0 || x >= 8 || y < 0 || y >= 8) {
                return false;
            }
            if (board[x][y] != null) {
                return false;
            }
            x += stepX;
            y += stepY;
        }

        Piece target = board[toRow][toCol];
        return target == null || target.isWhite() != isWhite;
    }
}
