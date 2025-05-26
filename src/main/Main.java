package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
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
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
