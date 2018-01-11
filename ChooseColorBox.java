import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class ChooseColorBox {

    static String color;

    public static String display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Color Selection");
        window.setMinWidth(250);

        Label label = new Label();
        label.setText("Pick a color");

        GridPane grid = new GridPane();


        Button blue_button = new Button("Blue");
        blue_button.setOnAction(e -> {
            color = "blue";
            window.close();
        });
        grid.setConstraints(blue_button, 0, 0);

        Button red_button = new Button("Red");
        red_button.setOnAction(e -> {
            color = "red";
            window.close();
        });
        grid.setConstraints(red_button, 0, 1);

        Button yellow_button = new Button("Yellow");
        yellow_button.setOnAction(e -> {
            color = "yellow";
            window.close();
        });
        grid.setConstraints(yellow_button, 1, 0);

        Button green_button = new Button("Green");
        green_button.setOnAction(e -> {
            color = "green";
            window.close();
        });
        grid.setConstraints(green_button, 1, 1);

        grid.getChildren().addAll(blue_button, red_button, yellow_button, green_button);
        grid.setAlignment(Pos.CENTER);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, grid);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return color;
    }

}
