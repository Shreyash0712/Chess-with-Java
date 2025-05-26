package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import piece.*;
import ui.BoardView;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        BoardView boardView = new BoardView();

        // ==== ICON ====
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/icon.png"))));

        // ==== CENTER: Chessboard inside StackPane ====
        StackPane boardContainer = new StackPane(boardView.getView());
        boardContainer.setStyle("-fx-padding: 20px;");

        // ==== Layout ====
        BorderPane root = new BorderPane();
        root.setCenter(boardContainer);

        Scene scene = new Scene(root);
        primaryStage.setTitle("Chess");
        primaryStage.setScene(scene);

        // === Set MIN window size to fit board and UI ===
        int minWidth = 8 * 80 + 100;
        int minHeight = 8 * 80 + 100;
        primaryStage.setMinWidth(minWidth);
        primaryStage.setMinHeight(minHeight);

        primaryStage.setFullScreen(false);
        setupInitialPieces(boardView);
        primaryStage.show();
    }

    private void setupInitialPieces(BoardView boardView) {
        // Black side
        for (int col = 0; col < 8; col++) {
            boardView.placePiece(new Pawn(false), 1, col);
        }
        boardView.placePiece(new Rook(false), 0, 0);
        boardView.placePiece(new Rook(false), 0, 7);
        boardView.placePiece(new Knight(false), 0, 1);
        boardView.placePiece(new Knight(false), 0, 6);
        boardView.placePiece(new Bishop(false), 0, 2);
        boardView.placePiece(new Bishop(false), 0, 5);
        boardView.placePiece(new King(false), 0, 4);
        boardView.placePiece(new Queen(false), 0, 3);

        // White side
        for (int col = 0; col < 8; col++) {
            boardView.placePiece(new Pawn(true), 6, col);
        }
        boardView.placePiece(new Rook(true), 7, 0);
        boardView.placePiece(new Rook(true), 7, 7);
        boardView.placePiece(new Knight(true), 7, 1);
        boardView.placePiece(new Knight(true), 7, 6);
        boardView.placePiece(new Bishop(true), 7, 2);
        boardView.placePiece(new Bishop(true), 7, 5);
        boardView.placePiece(new King(true), 7, 4);
        boardView.placePiece(new Queen(true), 7, 3);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
