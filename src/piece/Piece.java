package piece;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public abstract class Piece {
    protected boolean isWhite;
    protected ImageView imageView;

    public Piece(boolean isWhite) {
        this.isWhite = isWhite;
        String color = isWhite ? "white" : "black";
        String name = getClass().getSimpleName().toLowerCase();
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/pieces/" + color + name + ".png")));
        imageView = new ImageView(image);
        imageView.setFitWidth(60);
        imageView.setFitHeight(60);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public abstract boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board);
}
