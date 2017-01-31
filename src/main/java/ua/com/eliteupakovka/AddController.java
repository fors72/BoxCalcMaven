package ua.com.eliteupakovka;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class AddController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void editConstrution(){
        Stage primaryStage = new Stage();
        AnchorPane root = null;
        try {
            root = new FXMLLoader().load(getClass().getResourceAsStream("/fxml/edit_construction.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setTitle("Конструкции");
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(900);
        primaryStage.setScene(new Scene(root, 800, 900));
        primaryStage.show();
    }

    public void editSize(){

    }

    public void editMaterial(){

    }
}
