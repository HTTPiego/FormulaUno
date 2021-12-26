package it.unicam.cs.pa2021.formulaUno;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class fxmlView extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/Circuit.fxml"));
        Scene scene = new Scene(root, 750, 750);
        stage.setTitle("JPencilRace");
        stage.setScene(scene);
        stage.show();
    }


}
