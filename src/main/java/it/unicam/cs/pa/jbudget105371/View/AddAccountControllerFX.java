package it.unicam.cs.pa.jbudget105371.View;

import it.unicam.cs.pa.jbudget105371.control.ModelControl;
import it.unicam.cs.pa.jbudget105371.model.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Classe che implementa Initializable e ControllerFX che mi gestisce l'interfaccia grafica della finestra fxml in cui
 * vado a creare un nuovo account
 */
public class AddAccountControllerFX implements Initializable, ControllerFX {

    @FXML
    private Label errorLbl;
    @FXML
    private Button cancelBtn;
    @FXML
    private ComboBox<AccountType> typeCBox;
    @FXML
    private TextArea descriptionTxA;
    @FXML
    private TextField nameTxF;
    @FXML
    private TextField openingBalanceTxF;

    private ModelControl modelControl;

    /**
     * Metodo che riempie la comboBox dei tipi di account e crea una textbox numerica attraverso l'opportuno metodo
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        typeCBox.getItems().addAll(AccountType.values());
        createNumericTextField();
    }

    /**
     * Metodo che mi va a settare il model control
     *
     * @param modelControl controller del blocco model su cui lavorare
     */
    @Override
    public void setModelControl(ModelControl modelControl) {
        this.modelControl = modelControl;
    }

    /**
     * Alla pressione del tasto cancel mi esce dalla finestra di creazione Account
     */
    public void handleCancelBtn() {
        Stage s = (Stage) cancelBtn.getScene().getWindow();
        s.close();
    }

    /**
     * Metodo che alla pressione del tasto add mi va a creare un account passando i parametri al modelControl
     * e poi esce dalla finestra di creazione account
     */
    public void handleAddBtn() {
        if (checkAccount()) {
            modelControl.createAccount(nameTxF.getText().trim(), typeCBox.getSelectionModel().getSelectedItem(),
                    descriptionTxA.getText().trim(), openingBalanceTxF.getText());
            handleCancelBtn();
        }
    }

    /**
     * Metodo che effettua tutti i controlli per verificare la validità dei paremaetri dell'account se va tutto bene
     * restituirà true
     *
     * @return true se tutti i contolli vanno a buon fine, false altrimenti
     */
    private boolean checkAccount() {
        if (!checkName()) return false;
        if (descriptionTxA.getText().trim().length() == 0 || descriptionTxA.getText().trim().length() > 25) {
            setErrorLbl("Errore inserimento Descrizione!");
            return false;
        }
        if (openingBalanceTxF.getText().length() == 0 || openingBalanceTxF.getText().startsWith(".") || openingBalanceTxF.getText().startsWith("0")) {
            setErrorLbl("Errore inserimento OpeningBalance!");
            return false;
        }
        if (typeCBox.getSelectionModel().isEmpty()) {
            setErrorLbl("Errore inserimento Type!");
            return false;
        }
        return true;
    }

    /**
     * Metodo che controlla se nome esiste e se è minore di 16 caratteri e controlla se è già presente
     * un account con lo stesso nome nel ledger
     *
     * @return true se i controlli sono andati a buon fine, false altrimenti
     */
    private boolean checkName() {
        if (nameTxF.getText().trim().length() == 0 || nameTxF.getText().trim().length() > 16) {
            setErrorLbl("Errore inserimento Nome!");
            return false;
        }
        for (IAccount a : modelControl.getLedger().getAccounts()) {
            if (nameTxF.getText().trim().toUpperCase().equals(a.getName().toUpperCase())) {
                setErrorLbl("Errore nome già esistente!");
                return false;
            }
        }
        return true;
    }

    /**
     * Metodo che preso un messaggio di errore fa apparire la label con l'errore passato
     *
     * @param errMessage messaggio di errore da mostrare
     */
    @Override
    public void setErrorLbl(String errMessage) {
        errorLbl.setVisible(true);
        errorLbl.setText(errMessage);
    }

    /**
     * Cambia le proprietà del textField dell'opening balance in modo da poter immettere solo numeri
     * (fino a 9 prima del . e 2 numeri dopo il punto ES. 123456789.99)
     */
    public void createNumericTextField() {
        openingBalanceTxF.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,8}([.]\\d{0,2})?")) {
                openingBalanceTxF.setText(oldValue);
            }
        });
    }

}
