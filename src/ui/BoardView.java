package ui;

import javafx.geometry.Pos;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import piece.Piece;

public class BoardView {
    private static final int TILE_SIZE = 80;
    private static final int WIDTH = 8;
    private static final int HEIGHT = 8;

    private final GridPane grid;
    private final StackPane[][] tiles = new StackPane[HEIGHT][WIDTH];
    private final BoardModel boardModel = new BoardModel();

    private Piece selectedPiece = null;
    private int selectedRow = -1;
    private int selectedCol = -1;

    private boolean whiteTurn = true; // white starts

    public BoardView() {
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);

        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                Rectangle square = new Rectangle(TILE_SIZE, TILE_SIZE);
                square.setFill((row + col) % 2 == 0 ? Color.BEIGE : Color.BROWN);

                StackPane stack = new StackPane(square);
                final int r = row, c = col;

                stack.setOnMouseClicked(_ -> handleClick(r, c));

                stack.setOnDragDetected(event -> {
                    Piece piece = boardModel.getPiece(r, c);
                    if (piece != null && piece.isWhite() == whiteTurn) {
                        selectedRow = r;
                        selectedCol = c;
                        selectedPiece = piece;

                        showPossibleMoves(r, c, piece);

                        Dragboard db = stack.startDragAndDrop(TransferMode.MOVE);
                        ClipboardContent content = new ClipboardContent();
                        content.putString(r + "," + c);
                        db.setContent(content);

                        db.setDragView(piece.getImageView().getImage(), TILE_SIZE / 2.0, TILE_SIZE / 2.0);
                    }
                    event.consume();
                });

                stack.setOnDragOver(event -> {
                    if (event.getGestureSource() != stack && event.getDragboard().hasString()) {
                        event.acceptTransferModes(TransferMode.MOVE);
                    }
                    event.consume();
                });

                stack.setOnDragDropped(event -> {
                    Dragboard db = event.getDragboard();
                    boolean success = false;
                    if (db.hasString()) {
                        String[] from = db.getString().split(",");
                        int fromRow = Integer.parseInt(from[0]);
                        int fromCol = Integer.parseInt(from[1]);

                        Piece movingPiece = boardModel.getPiece(fromRow, fromCol);
                        if (movingPiece != null && movingPiece.isWhite() == whiteTurn &&
                                movingPiece.isValidMove(fromRow, fromCol, r, c, getBoardState())) {

                            movePiece(fromRow, fromCol, r, c);
                            whiteTurn = !whiteTurn;
                            success = true;
                        }
                    }
                    clearMoveHighlights();
                    selectedPiece = null;
                    selectedRow = -1;
                    selectedCol = -1;
                    event.setDropCompleted(success);
                    event.consume();
                });

                tiles[row][col] = stack;
                grid.add(stack, col, row);
            }
        }

        grid.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        grid.setMaxSize(GridPane.USE_PREF_SIZE, GridPane.USE_PREF_SIZE);
    }

    public GridPane getView() {
        return grid;
    }

    public void placePiece(Piece piece, int row, int col) {
        if (!boardModel.isValidPosition(row, col)) return;
        boardModel.placePiece(piece, row, col);
        tiles[row][col].getChildren().add(piece.getImageView());
    }

    private void handleClick(int row, int col) {
        Piece clickedPiece = boardModel.getPiece(row, col);

        // No piece selected yet
        if (selectedPiece == null) {
            if (clickedPiece != null && clickedPiece.isWhite() == whiteTurn) {
                selectedRow = row;
                selectedCol = col;
                selectedPiece = clickedPiece;
                showPossibleMoves(row, col, clickedPiece);
            }
            return;
        }

        // Clicking same tile again
        if (selectedRow == row && selectedCol == col) {
            clearMoveHighlights();
            selectedPiece = null;
            return;
        }

        // Clicking another own piece
        if (clickedPiece != null && clickedPiece.isWhite() == selectedPiece.isWhite()) {
            if (clickedPiece.isWhite() == whiteTurn) {
                selectedRow = row;
                selectedCol = col;
                selectedPiece = clickedPiece;
                showPossibleMoves(row, col, clickedPiece);
            }
            return;
        }

        // Attempting move
        if (selectedPiece.isWhite() == whiteTurn &&
                selectedPiece.isValidMove(selectedRow, selectedCol, row, col, getBoardState())) {
            movePiece(selectedRow, selectedCol, row, col);
            whiteTurn = !whiteTurn;
        }

        clearMoveHighlights();
        selectedPiece = null;
    }

    private Piece[][] getBoardState() {
        Piece[][] state = new Piece[HEIGHT][WIDTH];
        for (int r = 0; r < HEIGHT; r++) {
            for (int c = 0; c < WIDTH; c++) {
                state[r][c] = boardModel.getPiece(r, c);
            }
        }
        return state;
    }

    private void movePiece(int fromRow, int fromCol, int toRow, int toCol) {
        Piece piece = boardModel.getPiece(fromRow, fromCol);
        if (piece == null) return;

        tiles[fromRow][fromCol].getChildren().remove(piece.getImageView());

        Piece target = boardModel.getPiece(toRow, toCol);
        if (target != null) {
            tiles[toRow][toCol].getChildren().remove(target.getImageView());
        }

        boardModel.movePiece(fromRow, fromCol, toRow, toCol);
        tiles[toRow][toCol].getChildren().add(piece.getImageView());
    }

    private void showPossibleMoves(int row, int col, Piece piece) {
        clearMoveHighlights();
        Piece[][] state = getBoardState();

        for (int r = 0; r < HEIGHT; r++) {
            for (int c = 0; c < WIDTH; c++) {
                if (piece.isValidMove(row, col, r, c, state)) {
                    Piece target = state[r][c];
                    Color color = (target == null) ? Color.rgb(30, 144, 255, 0.5) // blue
                            : Color.rgb(255, 0, 0, 0.5); // red if capturing

                    Circle hint = new Circle(TILE_SIZE / 6.0, color);
                    tiles[r][c].getChildren().add(hint);
                }
            }
        }
    }

    private void clearMoveHighlights() {
        for (int r = 0; r < HEIGHT; r++) {
            for (int c = 0; c < WIDTH; c++) {
                tiles[r][c].getChildren().removeIf(node -> node instanceof Circle);
            }
        }
    }
}
