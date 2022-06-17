package it.unicam.cs.pa.jbudget105371.utils;

import it.unicam.cs.pa.jbudget105371.View.ControllerFX;
import it.unicam.cs.pa.jbudget105371.control.ModelControl;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Classe di appoggio che si occupa di reperire un file .fxml e di aprirlo con showDialog,
 * molto utile per l'estendibilità
 */
public class FxmlLoader implements FxmlLoaderInterface {

    /**
     * Metodo che si occupa di reperire un Parent, attraverso FXMLLoader, con un file.fxml passato
     *
     * @param fileName nome file da aprire
     * @param title    titolo del file
     */
    public void getPage(String fileName, String title, ModelControl modelControl) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fileName));
            Parent view = loader.load();
            ControllerFX c = loader.getController();
            c.setModelControl(modelControl);
            showDialog(view, title);
        } catch (Exception e) {
            System.err.println("No page " + fileName + " found!");
        }
    }

    /**
     * Metodo che si occupa di fare il display del Parent scelto aprendo una scena e inserendo come titolo
     * della finestra il titolo passato
     *
     * @param root  finestra che voglio mostrare a schermo
     * @param title titolo della finestra
     */
    public void showDialog(Parent root, String title) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.showAndWait();
    }
}
