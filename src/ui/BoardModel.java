package ui;

import piece.Piece;

public class BoardModel {
    private final Piece[][] board;

    public BoardModel() {
        board = new Piece[8][8];
    }

    public Piece getPiece(int row, int col) {
        if (isValidPosition(row, col)) {
            return board[row][col];
        }
        return null;
    }

    public void placePiece(Piece piece, int row, int col) {
        if (isValidPosition(row, col)) {
            board[row][col] = piece;
        }
    }

    public void movePiece(int fromRow, int fromCol, int toRow, int toCol) {
        if (!isValidPosition(fromRow, fromCol) || !isValidPosition(toRow, toCol)) return;

        Piece piece = board[fromRow][fromCol];
        board[fromRow][fromCol] = null;
        board[toRow][toCol] = piece;
    }

    public boolean isOccupied(int row, int col) {
        return getPiece(row, col) != null;
    }

    public boolean isValidPosition(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }
}
