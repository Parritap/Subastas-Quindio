package utilities;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.concurrent.Callable;

public class AnimatedPropertiesTest extends Application {

    @Override
    public void start(Stage primaryStage) {
        final StackPane root = new StackPane();
        final Button button = new Button("Click Me");

        final Color startColor = Color.web("#e08090");
        final Color endColor = Color.web("#80e090");

        final ObjectProperty<Color> color = new SimpleObjectProperty<Color>(startColor);

        // String that represents the color above as a JavaFX CSS function:
        // -fx-body-color: rgb(r, g, b);
        // with r, g, b integers between 0 and 255
        final StringBinding cssColorSpec = Bindings.createStringBinding(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return String.format("-fx-body-color: rgb(%d, %d, %d);",
                        (int) (256*color.get().getRed()),
                        (int) (256*color.get().getGreen()),
                        (int) (256*color.get().getBlue()));
            }
        }, color);

        // bind the button's style property
        button.styleProperty().bind(cssColorSpec);

        final Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(color, startColor)),
                new KeyFrame(Duration.seconds(1), new KeyValue(color, endColor)));

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                timeline.play();
            }
        });

        // Create a rotating rectangle and set it as the graphic for the button
        final Rectangle rotatingRect = new Rectangle(5,5,10,6);
        rotatingRect.setFill(Color.CORNFLOWERBLUE);
        final Pane rectHolder = new Pane();
        rectHolder.setMinSize(20, 16);
        rectHolder.setPrefSize(20, 16);
        rectHolder.setMaxSize(20, 16);
        rectHolder.getChildren().add(rotatingRect);
        final RotateTransition rotate = new RotateTransition(Duration.seconds(1), rotatingRect);
        rotate.setByAngle(360);
        rotate.setCycleCount(Animation.INDEFINITE);
        rotate.setInterpolator(Interpolator.LINEAR);

        button.setGraphic(rectHolder);

        // make the rectangle rotate when the mouse hovers over the button
        button.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                rotate.play();
            }
        });

        button.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                rotate.stop();
                rotatingRect.setRotate(0);
            }
        });


        root.getChildren().addAll(button);

        final Scene scene = new Scene(root, 300, 175);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}