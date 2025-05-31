package piece;

public class Queen extends Piece {
    public Queen(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board) {
        int dx = toRow - fromRow;
        int dy = toCol - fromCol;

        // Check if move is vertical, horizontal or diagonal
        boolean isDiagonal = Math.abs(dx) == Math.abs(dy);
        boolean isStraight = (dx == 0 && dy != 0) || (dy == 0 && dx != 0);

        if (!isDiagonal && !isStraight) {
            return false; // Not a valid queen move
        }

        int stepX = Integer.compare(dx, 0); // will be -1, 0, or 1
        int stepY = Integer.compare(dy, 0); // will be -1, 0, or 1

        int x = fromRow + stepX;
        int y = fromCol + stepY;

        // Check all squares between source and destination are empty
        while (x != toRow || y != toCol) {
            if (board[x][y] != null) {
                return false; // path blocked
            }
            x += stepX;
            y += stepY;
        }

        // Check target square: empty or contains opponent's piece
        Piece target = board[toRow][toCol];
        return target == null || target.isWhite() != isWhite;
    }
}
