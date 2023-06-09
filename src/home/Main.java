package home;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../resource/home.fxml"));
        //Scene
        Scene scene = new Scene(root);
        // CSS styling
        scene.getStylesheets().add(getClass().getResource("../resource/home.css").toExternalForm());
        //Stage
        Image icon = new Image("images/icon.png");
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("GoMoku");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}