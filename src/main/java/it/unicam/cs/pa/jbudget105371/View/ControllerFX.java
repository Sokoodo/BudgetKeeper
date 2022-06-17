package it.unicam.cs.pa.jbudget105371.View;

import it.unicam.cs.pa.jbudget105371.control.ModelControl;

/**
 * Questa interfaccia è implementata dalle classi che fanno da controller a una pagina .fxml
 */
public interface ControllerFX {

     void setModelControl(ModelControl modelControl);

     void setErrorLbl(String errMess);

     void createNumericTextField();

}
