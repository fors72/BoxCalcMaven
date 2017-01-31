package ua.com.eliteupakovka;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = new FXMLLoader().load(getClass().getResourceAsStream("/fxml/edit_construction.fxml"));
        primaryStage.setTitle("BoxCalc");
        primaryStage.setMinWidth(798);
        primaryStage.setMinHeight(680);
        primaryStage.setScene(new Scene(root, 798, 660));
        primaryStage.show();
    }




    public static void main(String[] args) {
        launch(args);
    }
}
