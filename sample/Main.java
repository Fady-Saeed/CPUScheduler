package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("CPU Scheduler");
//        primaryStage.getIcons().add(new Image("file:icon.png"));
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        Scene scene = new Scene(root, 1024, 768);
        scene.getStylesheets().add(getClass().getResource("input_response.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
