package it.unicam.cs.pa.jbudget105371;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Classe che estende Application e si occupa di avviare l'interfaccia grafica utilizzando l'FXMLLoader
 */
public class AppFX extends Application {

    Stage window;

    @Override
    public void start(Stage primaryStage) throws IOException {
        window = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/mainPage.fxml"));
        window.setScene(new Scene(root));
        window.setTitle("BudgetKeeper");
        window.setResizable(false);
        window.show();
    }

}
