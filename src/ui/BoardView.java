package ui;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class BoardView {
    private static final int TILE_SIZE = 80;
    private static final int WIDTH = 8;
    private static final int HEIGHT = 8;
    private final GridPane grid;

    public BoardView() {
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);

        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                Rectangle square = new Rectangle(TILE_SIZE, TILE_SIZE);
                Color color = (row + col) % 2 == 0 ? Color.BEIGE : Color.BROWN;
                square.setFill(color);
                StackPane stack = new StackPane(square);
                grid.add(stack, col, row);
            }
        }

        grid.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        grid.setMaxSize(GridPane.USE_PREF_SIZE, GridPane.USE_PREF_SIZE);

    }

    public GridPane getView() {
        return grid;
    }
}
