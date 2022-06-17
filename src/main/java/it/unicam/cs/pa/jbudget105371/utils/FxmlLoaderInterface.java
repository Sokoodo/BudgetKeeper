package it.unicam.cs.pa.jbudget105371.utils;

import it.unicam.cs.pa.jbudget105371.control.ModelControl;
import javafx.scene.Parent;

/**
 * Questa interfaccia è implementata dalle classi che hanno la responsabilità di reperire un file .fxml e di aprirlo.
 */
public interface FxmlLoaderInterface {

    void getPage(String fileName, String title, ModelControl modelControl);

    void showDialog(Parent root, String title);

}
