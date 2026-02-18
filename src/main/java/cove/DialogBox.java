package cove;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class DialogBox extends HBox {

    private Label text;
    private ImageView displayPicture;

    // I used Claude AI to improve this DialogBox element, by asking it to put the texts in rounded boxes,
    // and vertically centre the texts.
    public DialogBox(String s, Image i) {
        text = new Label(s);
        displayPicture = new ImageView(i);

        // Style the label text
        text.setWrapText(true);
        text.setMaxWidth(250);

        // Wrap the label in a StackPane styled as a rounded bubble
        StackPane bubble = new StackPane(text);
        bubble.setStyle(
                "-fx-background-color: #ADD8E6;" +   // light blue
                        "-fx-background-radius: 15;" +
                        "-fx-padding: 10 14 10 14;"
        );
        bubble.setMaxWidth(270);

        // Style the image
        displayPicture.setFitWidth(60.0);
        displayPicture.setFitHeight(60.0);

        // Outer HBox alignment: centre vertically
        this.setAlignment(Pos.CENTER_RIGHT);
        this.setSpacing(8);
        this.setPadding(new Insets(5, 10, 5, 10));

        this.getChildren().addAll(bubble, displayPicture);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        this.setAlignment(Pos.TOP_LEFT);
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        FXCollections.reverse(tmp);
        this.getChildren().setAll(tmp);
    }

    public static DialogBox getUserDialog(String s, Image i) {
        return new DialogBox(s, i);
    }

    public static DialogBox getCoveDialog(String s, Image i) {
        var db = new DialogBox(s, i);
        db.flip();
        return db;
    }
}
