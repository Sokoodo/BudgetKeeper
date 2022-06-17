package it.unicam.cs.pa.jbudget105371.View;

import it.unicam.cs.pa.jbudget105371.control.ModelControl;
import it.unicam.cs.pa.jbudget105371.model.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;

/**
 * Classe che implementa Initializable e ControllerFX che gestisce l'interfaccia grafica della finestra fxml di
 * creazione di transazioni e di conseguenza anche tag e movimenti
 */
public class AddTransactionControllerFX implements Initializable, ControllerFX {

    @FXML
    private Label nMovementsLbl;
    @FXML
    private Button addBtn;
    @FXML
    private Label errorMovementLbl;
    @FXML
    private TextField amountTxF;
    @FXML
    private TextArea descriptionTxA;
    @FXML
    private ComboBox<String> accountCBox;
    @FXML
    private ComboBox<MovementType> typeCBox;
    @FXML
    private AnchorPane transactionPane;
    @FXML
    private AnchorPane movementPane;
    @FXML
    private Label errorLbl;
    @FXML
    private Button cancelBtn;
    @FXML
    private TextArea tagsTransactionTxA;
    @FXML
    private TextArea tagsMovementTxA;
    @FXML
    private DatePicker datePick;

    private List<IMovement> movements;
    private List<IAccount> accounts;
    private HashSet<ITag> transactionTags;
    private HashSet<ITag> movementTags;
    private HashSet<ITag> tagsForLedger;
    private ModelControl modelControl;

    /**
     * Metodo che inizializza tutte le liste e i set necessari e riempie la combobox dei tipi di movimenti,
     * se la lista dei movimenti è vuota disattiva il bottone di add transazione
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        accounts = new ArrayList<>();
        tagsForLedger = new HashSet<>();
        movements = new ArrayList<>();
        transactionTags = new HashSet<>();
        movementTags = new HashSet<>();
        typeCBox.getItems().addAll(MovementType.values());
        createNumericTextField();
        addBtn.setDisable(movements.isEmpty());
    }

    /**
     * Metodo che mi setta il model controller
     *
     * @param modelControl controller del blocco model
     */
    @Override
    public void setModelControl(ModelControl modelControl) {
        this.modelControl = modelControl;
    }

    /**
     * Metodo che alla pressione del bottone di cancel su transazioni mi chiude la finestra e torna quindi alla
     * finestra iniziale.
     */
    public void handleCancelBtn() {
        Stage s = (Stage) cancelBtn.getScene().getWindow();
        s.close();
    }

    /**
     * Metodo che alla pressione del bottone di aggiunta transazione mi va a creare una transazione, aggiunge i tag
     * al ledger ed aggiunge i movimenti agli account, tutto attraverso il modelControl, e alla fine esce dalla finestra
     */
    public void handleAddBtn() {
        modelControl.createTransaction(transactionTags, movements, datePick.getValue());
        modelControl.addTagsToLedger(tagsForLedger);
        modelControl.setMovementToAccount(movements, accounts);
        handleCancelBtn();
    }

    /**
     * Metodo che premuto il bottone per passare alla creazione movimenti, mi fa un controllo di data e tag se è
     * tutto ok passa appunto alla schermata di creazione movimento, crea anche i tag della transazione
     */
    public void handleMovementsCreation() {
        if (checkTransaction()) {
            transactionPane.setVisible(false);
            movementPane.setVisible(true);
            setupAccountComboBox();
            errorLbl.setVisible(false);
            tagsTransactionTxA.setDisable(true);
            datePick.setDisable(true);
            createTags(tagsTransactionTxA.getText(), transactionTags);
        }
    }

    /**
     * Metodo che alla pressione del bottone di cancel sui movimenti mi annulla la creazione movimento e torna
     * alla schermata della transazione, ri-inizializzando i movementTags (non più validi)
     */
    public void handleCancelMovementBtn() {
        transactionPane.setVisible(true);
        movementPane.setVisible(false);
        resetMovementView();
        movementTags = new HashSet<>();
    }

    /**
     * Metodo che alla pressione del bottone di add movimento mi va a fare un controllo di validità dei dati immessi
     * e se passa questo controllo mi va a creare un movimento e ad inserire i tag al movimento, se il movimento è
     * null darà un errore, fatto ciò ri-inizializza i movement tag e torna alla schermata della transazione
     * incrementando la label col numero di tag della transazione
     */
    public void handleAddMovementBtn() {
        if (checkSingleMovement()) {
            IMovement m = modelControl.createMovement(datePick.getValue(), movementTags, accountCBox.getSelectionModel().getSelectedItem(),
                    typeCBox.getSelectionModel().getSelectedItem(), descriptionTxA.getText().trim(), amountTxF.getText(),
                    movements, accounts);
            if (m != null) {
                createTags(tagsMovementTxA.getText() + "," + tagsTransactionTxA.getText(), movementTags);
                for (ITag t : movementTags) {
                    m.addTag(t);
                }
            } else {
                setErrorLbl("Non si hanno crediti sufficienti!");
            }
            handleCancelMovementBtn();
            addBtn.setDisable(false);
            nMovementsLbl.setText(String.valueOf(movements.size()));
            movementTags = new HashSet<>();
        }
    }

    /**
     * Metodo che mi va a creare i tag estraendoli dalla stringa e li aggiunge ad una lista di tag e controlla se
     * i tag sono già esistenti nel ledger attraverso il modelControl
     *
     * @param tempString stringa dalla quale estrarre i tag
     * @param iTagList   lista dove aggiungere i tag
     */
    private void createTags(String tempString, HashSet<ITag> iTagList) {
        String string = tempString.replaceAll(" ", "");
        String[] sArray = string.split(",");
        for (String s : sArray) {
            ITag tag1 = new Tag(s);
            modelControl.alreadyExistingTag(tag1, iTagList, tagsForLedger);
        }
    }

    /**
     * Metodo che effettua tutti i controlli necessari sugli input della transazione e restituisce true se va a
     * buon fine, altrimenti restituisce false e scrive un messaggio d'errore
     *
     * @return true se i controlli vanno tutti a buon fine, false altrimenti
     */
    private boolean checkTransaction() {
        if (datePick.getValue() == null) {
            setErrorLbl("Errore inserimento Data!");
            return false;
        }
        if (!checkData()) {
            return false;
        }
        if (tagsTransactionTxA.getText().trim().length() == 0 || tagsTransactionTxA.getText().trim().length() > 100) {
            setErrorLbl("Errore inserimento Tags!");
            return false;
        }
        if (checkNullTags(tagsTransactionTxA.getText())) {
            setErrorLbl("Sono presenti tag nulli!");
            return false;
        }
        if (checkDuplicateTags(tagsTransactionTxA.getText())) {
            setErrorLbl("Non ci possono essere 2 tag uguali!");
            return false;
        }
        return true;
    }

    /**
     * Metodo che effettua tutti i controlli necessari sugli input del movimento e restituisce true se va a buon fine,
     * altrimenti restituisce false e scrive un messaggio d'errore
     *
     * @return true se i controlli vanno tutti a buon fine, false altrimenti
     */
    private boolean checkSingleMovement() {
        if (typeCBox.getSelectionModel().isEmpty()) {
            setErrorMovementLbl("Errore inserimento Tipo!");
            return false;
        }
        if (tagsMovementTxA.getText().trim().length() == 0 || tagsMovementTxA.getText().trim().length() > 100) {
            setErrorMovementLbl("Errore inserimento Tags!");
            return false;
        }
        if (checkNullTags(tagsMovementTxA.getText())) {
            setErrorMovementLbl("Sono presenti tag nulli!");
            return false;
        }
        if (checkDuplicateTags(tagsTransactionTxA.getText() + "," + tagsMovementTxA.getText())) {
            setErrorMovementLbl("Non puoi inserire 2 tag uguali!");
            return false;
        }
        if (descriptionTxA.getText().trim().length() == 0 || descriptionTxA.getText().trim().length() > 25) {
            setErrorMovementLbl("Errore inserimento Descrizione!");
            return false;
        }
        if (accountCBox.getSelectionModel().isEmpty()) {
            setErrorMovementLbl("Errore inserimento Account!");
            return false;
        }
        if (amountTxF.getText().length() == 0 || amountTxF.getText().startsWith(".") || amountTxF.getText().startsWith("0")) {
            setErrorMovementLbl("Errore inserimento Importo!");
            return false;
        }
        return true;
    }

    /**
     * Metodo che controlla se la data inserita è futura o passata
     *
     * @return true se data non è futura, false altrimenti
     */
    private boolean checkData() {
        Date dateToCheck = modelControl.takeData(datePick.getValue());
        Date now = new Date(System.currentTimeMillis());
        if (dateToCheck.after(now)) {
            setErrorLbl("Impossibile inserire data futura!");
            return false;
        } else return true;
    }

    /**
     * Metodo che controlla se nella stringa ci sono 2 o piu elementi uguali e se è cosi restituisce false
     *
     * @param tempString stringa da controllare
     * @return false se controllo va a buon fine, true altrimenti
     */
    private boolean checkDuplicateTags(String tempString) {
        String string = tempString.replaceAll(" ", "");
        String[] sArray = string.split(",");
        List<String> sList = Arrays.asList(sArray);
        for (int k = 0; k < sList.size() - 1; k++) {
            for (int i = k + 1; i < sList.size(); i++) {
                if (sList.get(k).toUpperCase().equals(sList.get(i).toUpperCase())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Controlla se i tag inseriti nella textArea sono validi e non nulli , se ne trova uno nullo restituisce false
     *
     * @param tempString stringa della textArea da controllare
     * @return false se il controllo è andato a buon fine, true altrimenti
     */
    public boolean checkNullTags(String tempString) {
        String string = tempString.replaceAll(" ", "");
        String[] sArray = string.split(",");
        for (String s : sArray) {
            if (s.length() == 0) {
                return true;
            }
        }
        return false;
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
     * Metodo che preso un messaggio di errore fa apparire la label errorMovementLabel con la stringa di errore passato
     *
     * @param errMessage messaggio di errore da mostrare nella label di errore nei movivimenti
     */
    public void setErrorMovementLbl(String errMessage) {
        errorMovementLbl.setVisible(true);
        errorMovementLbl.setText(errMessage);
    }

    /**
     * Metodo che mi va a creare una text field che accetta : MAX 9 numeri, il punto e MAX 2 numeri dopo il punto
     * (ES. 123456789.99)
     */
    @Override
    public void createNumericTextField() {
        amountTxF.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,8}([.]\\d{0,2})?")) {
                amountTxF.setText(oldValue);
            }
        });
    }

    /**
     * Metodo che inizializza la combobox degli account inserendo al suo interno i nomi degli Account stessi
     */
    public void setupAccountComboBox() {
        accountCBox.getItems().clear();
        ArrayList<String> accountNames = new ArrayList<>();
        for (IAccount a : modelControl.getLedger().getAccounts()) {
            accountNames.add(a.getName());
        }
        accountCBox.getItems().addAll(accountNames);
        accountCBox.setVisibleRowCount(4);
    }

    /**
     * Metodo che resetta i parametri dell'anchorPane di addMovement
     */
    private void resetMovementView() {
        typeCBox.getSelectionModel().clearSelection();
        amountTxF.clear();
        tagsMovementTxA.clear();
        descriptionTxA.clear();
        errorMovementLbl.setVisible(false);
    }

}
