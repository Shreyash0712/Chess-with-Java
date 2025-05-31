package piece;

public class Rook extends Piece {
    public Rook(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board) {
        int dx = toRow - fromRow;
        int dy = toCol - fromCol;

        if (dx != 0 && dy != 0) {
            return false;
        }

        int stepX = Integer.compare(dx, 0);
        int stepY = Integer.compare(dy, 0);

        int x = fromRow + stepX;
        int y = fromCol + stepY;

        while (x != toRow || y != toCol) {
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
