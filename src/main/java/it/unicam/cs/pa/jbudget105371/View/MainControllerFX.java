package it.unicam.cs.pa.jbudget105371.View;

import it.unicam.cs.pa.jbudget105371.control.ModelControl;
import it.unicam.cs.pa.jbudget105371.model.*;
import it.unicam.cs.pa.jbudget105371.utils.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Classe che implementa ControllerFX che gestisce l'interfaccia grafica della pagina principale: dove si sceglie se caricare un file
 * esistente o crearne uno nuovo, ci sono le tabelle con i dati relativi ad account, transazioni, tags e movimenti e
 * si possono rimuovere transazioni
 */
public class MainControllerFX implements ControllerFX {

    @FXML
    private Pane removeTranPane;
    @FXML
    private ComboBox<String> idTransactionCBox;
    @FXML
    private Label errLbl;
    @FXML
    private TextField enterMovementTxF;
    @FXML
    private Button searchMovementBtn;
    @FXML
    private AnchorPane initialPane;
    @FXML
    private BorderPane mainPane;
    @FXML
    private Label chooseOperationLbl;
    @FXML
    private TableView<AdapterAccount> accountsTable;
    @FXML
    private TableView<AdapterTransaction> transactionsTable;
    @FXML
    private TableView<AdapterTags> tagsTable;
    @FXML
    private TableView<AdapterMovement> movementsTable;
    @FXML
    private Label typeLbl;

    private File myFile;
    private ModelControl modelControl;

    /**
     * Metodo che alla pressione del bottone "New File" mi va a creare un FileChooser dove l'utente sceglie il path
     * dove inserire il file
     */
    public void chooseDestinationFolder() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chose path for .json file");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter(".json files", "*.json");
        fileChooser.getExtensionFilters().add(filter);
        myFile = fileChooser.showSaveDialog(initialPane.getScene().getWindow());
        if (myFile != null) {
            modelControl = new ModelControl(myFile);
            startBK();
        }
    }

    /**
     * Metodo che alla pressione del bottone "Load File" mi va a creare un FileChooser dove l'utente sceglie il file
     * da caricare da cui prendere il Ledger
     */
    public void loadFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load a .json file");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter(".json files", "*.json");
        fileChooser.getExtensionFilters().add(filter);
        myFile = fileChooser.showOpenDialog(initialPane.getScene().getWindow());
        if (myFile != null) {
            modelControl = new ModelControl(myFile);
            startBK();
        }
    }

    /**
     * Metodo che la pagina iniziale di "Benvenuto" e mi mostra la pagina successiva dove ci sono tutte le
     * funzionalità del programma, in piu mi setta il ledger e inizializza le tabelle
     */
    public void startBK() {
        initialPane.setVisible(false);
        mainPane.setVisible(true);
        typeLbl.setVisible(true);
        typeLbl.setText("Benvenuto!");
        createAccountTable();
        createTransactionTable();
        createTabTable();
        createMovementTable();
    }

    /**
     * Metodo che alla pressione del bottone "Add Account" mi carica la finestra addetta alla creazione degli
     * Account, poi aggiorna le tabelle
     */
    public void startAddAccountBtn() {
        FxmlLoader loader = new FxmlLoader();
        loader.getPage("/addAccount.fxml", "Add Account", this.modelControl);
        refreshAllTables();
        modelControl.saveLedger();
    }

    /**
     * Metodo che alla pressione del bottone "Add Transaction" mi carica la finestra addetta alla creazione delle
     * Transazioni, poi aggiorna le tabelle
     */
    public void startAddTransactionBtn() {
        if (!modelControl.getLedger().getAccounts().isEmpty()) {
            FxmlLoader loader = new FxmlLoader();
            loader.getPage("/addTransaction.fxml", "Add Transaction", this.modelControl);
            refreshAllTables();
            setupIdTransactionCBox();
            modelControl.saveLedger();
        } else {
            setErrorLbl("Devi prima inserire almeno 1 Account!");
        }
    }

    /**
     * Metodo che alla pressione del bottone degli Account mi imposta il border pane in modo da visualizzare
     * tutti gli strumenti necessari al display completo della tabella degli Account (oscurando le altre)
     */
    public void startShowAccountsBtn() {
        fillAccountTable();
        hideSearchTxF();
        typeLbl.setText("Accounts");
        accountsTable.setVisible(true);
        transactionsTable.setVisible(false);
        tagsTable.setVisible(false);
        movementsTable.setVisible(false);
        removeTranPane.setVisible(false);
    }

    /**
     * Metodo che alla pressione del bottone delle Transazioni mi imposta il border pane in modo da visualizzare
     * tutti gli strumenti necessari al display completo della tabella delle Transazioni (oscurando le altre)
     */
    public void startShowTransactionsBtn() {
        fillTransactionTable();
        hideSearchTxF();
        typeLbl.setText("Transactions");
        accountsTable.setVisible(false);
        transactionsTable.setVisible(true);
        tagsTable.setVisible(false);
        movementsTable.setVisible(false);
        removeTranPane.setVisible(false);
    }

    /**
     * Metodo che alla pressione del bottone dei tag mi imposta il border pane in modo da visualizzare
     * tutti gli strumenti necessari al display completo della tabella dei tag (oscurando le altre)
     */
    public void startShowTagsBtn() {
        fillTagsTable();
        hideSearchTxF();
        typeLbl.setText("Tags");
        accountsTable.setVisible(false);
        transactionsTable.setVisible(false);
        tagsTable.setVisible(true);
        movementsTable.setVisible(false);
        removeTranPane.setVisible(false);
    }

    /**
     * Metodo che alla pressione del bottone dei movimenti mi imposta il border pane in modo da visualizzare
     * tutti gli strumenti necessari al display completo della tabella dei movimenti (oscurando le altre)
     * e dela barra di ricerca per cercarli attraverso i tag
     */
    public void movementsByTagBtn() {
        showSearchTxF();
        typeLbl.setText("Movements");
        accountsTable.setVisible(false);
        transactionsTable.setVisible(false);
        tagsTable.setVisible(false);
        movementsTable.setVisible(true);
        removeTranPane.setVisible(false);
    }

    /**
     * Metodo che alla pressione del bottone fa il display della pagina che mi servirà appunto per rimuovere una
     * transazione
     */
    public void goToRemoveTransactionBtn() {
        if (!modelControl.getLedger().getTransactions().isEmpty()) {
            setupIdTransactionCBox();
            removeTranPane.setVisible(true);
            hideSearchTxF();
            typeLbl.setText("Remove Transaction");
            accountsTable.setVisible(false);
            transactionsTable.setVisible(false);
            tagsTable.setVisible(false);
            movementsTable.setVisible(false);
        } else {
            setErrorLbl("Devi prima inserire almeno una transazione!");
        }
    }

    /**
     * Metodo che alla pressione del bottone di rimozione transazione va a controllare che l'id transazione
     * è non nullo e lo passa a removeTransaction del ModelControl che andrà a rimuovere la transazione
     */
    public void handleRemoveTransactionBtn() {
        if (checkIdTransaction()) {
            String idTransaction = idTransactionCBox.getSelectionModel().getSelectedItem();
            modelControl.removeTransaction(idTransaction);
            startShowTransactionsBtn();
            modelControl.saveLedger();
            movementsTable.getItems().clear();
            enterMovementTxF.clear();
        }
    }

    /**
     * Metodo che controlla se la checkBox con l'id della transazione è vuoto
     *
     * @return true se il controlo è andato a buon fine, false altrimenti
     */
    private boolean checkIdTransaction() {
        if (idTransactionCBox.getSelectionModel().isEmpty()) {
            setErrorLbl("Errore inserimento Account!");
            return false;
        }
        return true;
    }

    /**
     * Metodo che inizializza la tabella degli account inserendo i parametri che voglio mostrare
     * (name,description,type,openingBalance,currentBalance,numeroMovimenti)
     * e imposto la larghezza delle colonne a mio piacimento
     */
    public void createAccountTable() {
        TableColumn<AdapterAccount, String> c1 = new TableColumn<>("Name");
        c1.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<AdapterAccount, String> c2 = new TableColumn<>("Description");
        c2.setCellValueFactory(new PropertyValueFactory<>("description"));
        TableColumn<AdapterAccount, AccountType> c3 = new TableColumn<>("Type");
        c3.setCellValueFactory(new PropertyValueFactory<>("type"));
        TableColumn<AdapterAccount, String> c4 = new TableColumn<>("Opening Balance");
        c4.setCellValueFactory(new PropertyValueFactory<>("openingBalance"));
        TableColumn<AdapterAccount, String> c5 = new TableColumn<>("Current Balance");
        c5.setCellValueFactory(new PropertyValueFactory<>("currentBalance"));
        TableColumn<AdapterAccount, Integer> c6 = new TableColumn<>("N.Movements");
        c6.setCellValueFactory(new PropertyValueFactory<>("movCount"));
        accountsTable.getColumns().addAll(c1, c2, c3, c4, c5, c6);
        accountsTable.resizeColumn(c4, 24);
        accountsTable.resizeColumn(c5, 20);
        accountsTable.resizeColumn(c6, 31);
    }

    /**
     * Metodo che inizializza la tabella delle transazioni inserendo i parametri che voglio mostrare
     * (data,totalAmount,numeroMovimenti,nomiTag)
     * e imposto la larghezza delle colonne a mio piacimento
     */
    private void createTransactionTable() {
        TableColumn<AdapterTransaction, String> c1 = new TableColumn<>("Date");
        c1.setCellValueFactory(new PropertyValueFactory<>("strDate"));
        TableColumn<AdapterTransaction, String> c2 = new TableColumn<>("Total Amount");
        c2.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        TableColumn<AdapterTransaction, Integer> c3 = new TableColumn<>("N.Movements");
        c3.setCellValueFactory(new PropertyValueFactory<>("movCount"));
        TableColumn<AdapterTransaction, String> c4 = new TableColumn<>("Tags");
        c4.setCellValueFactory(new PropertyValueFactory<>("tagNames"));
        transactionsTable.getColumns().addAll(c1, c2, c3, c4);
        transactionsTable.resizeColumn(c1, 45);
        transactionsTable.resizeColumn(c2, 45);
        transactionsTable.resizeColumn(c3, 45);
        transactionsTable.resizeColumn(c4, 100);
    }

    /**
     * Metodo che inizializza la tabella dei Tag inserendo i parametri che voglio mostrare
     * (name,description)
     * e imposto la larghezza delle colonne a mio piacimento
     */
    private void createTabTable() {
        TableColumn<AdapterTags, String> c1 = new TableColumn<>("Id");
        c1.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<AdapterTags, String> c2 = new TableColumn<>("Name");
        c2.setCellValueFactory(new PropertyValueFactory<>("name"));
        tagsTable.getColumns().addAll(c1, c2);
        tagsTable.resizeColumn(c1, 200);
        tagsTable.resizeColumn(c2, 55);
    }

    /**
     * Metodo che inizializza la tabella dei Movimenti inserendo i parametri che voglio mostrare
     * (nameAccount,type,date,amount,description)
     * e imposto la larghezza delle colonne a mio piacimento
     */
    private void createMovementTable() {
        TableColumn<AdapterMovement, String> c1 = new TableColumn<>("Account");
        c1.setCellValueFactory(new PropertyValueFactory<>("accountName"));
        TableColumn<AdapterMovement, MovementType> c2 = new TableColumn<>("Type");
        c2.setCellValueFactory(new PropertyValueFactory<>("type"));
        TableColumn<AdapterMovement, String> c3 = new TableColumn<>("Date");
        c3.setCellValueFactory(new PropertyValueFactory<>("strDate"));
        TableColumn<AdapterMovement, String> c4 = new TableColumn<>("Amount");
        c4.setCellValueFactory(new PropertyValueFactory<>("amount"));
        TableColumn<AdapterMovement, String> c5 = new TableColumn<>("Description");
        c5.setCellValueFactory(new PropertyValueFactory<>("description"));
        movementsTable.getColumns().addAll(c1, c2, c3, c4, c5);
        movementsTable.resizeColumn(c1, 40);
        movementsTable.resizeColumn(c2, 20);
        movementsTable.resizeColumn(c3, 20);
        movementsTable.resizeColumn(c4, 20);
        movementsTable.resizeColumn(c5, 55);
    }

    /**
     * Metodo che va ad aggiornare la tabella degli account prima facendo il clear e poi aggiungendo
     * tutti gli account ritornati dal convertAccounts() nella tabella Account
     */
    public void fillAccountTable() {
        ArrayList<AdapterAccount> accounts = modelControl.convertAccounts();
        accountsTable.getItems().clear();
        accountsTable.getItems().addAll(accounts);
    }

    /**
     * Metodo che va ad aggiornare la tabella delle transazioni prima facendo il clear e poi aggiungendo
     * tutte le transazioni ritornate dal convertTransaction() nella tabella Transazioni
     */
    public void fillTransactionTable() {
        ArrayList<AdapterTransaction> transactions = modelControl.convertTransactions();
        transactionsTable.getItems().clear();
        transactionsTable.getItems().addAll(transactions);
    }

    /**
     * Metodo che va ad aggiornare la tabella dei Tag prima facendo il clear e poi aggiungendo
     * tutti i Tags ritornati dal convertAccounts() nella tabella Tag
     */
    public void fillTagsTable() {
        HashSet<AdapterTags> tags = modelControl.convertTags();
        tagsTable.getItems().clear();
        tagsTable.getItems().addAll(tags);
    }

    /**
     * Metodo che alla pressione del bottone per la ricerca di un movimento mi va ad aggiungere tutti i movimenti
     * alla tabella dei movementsByTag
     */
    public void searchMovementBtn() {
        if (enterMovementTxF.getText().trim().length() > 0 || enterMovementTxF.getText() != null) {
            List<IMovement> movementsContainingTags = modelControl.movementsByTag(enterMovementTxF.getText().trim());
            movementsTable.getItems().clear();
            movementsTable.getItems().addAll(modelControl.convertMovements(movementsContainingTags));
        }
    }

    /**
     * Metodo che nasconde la textField dove cercare i movimenti attraverso i tag e il corrispondente bottone
     */
    public void hideSearchTxF() {
        searchMovementBtn.setVisible(false);
        enterMovementTxF.setVisible(false);
        chooseOperationLbl.setVisible(false);
        errLbl.setVisible(false);
    }

    /**
     * Metodo che mostra la textField dove cercare i movimenti attraverso i tag e il corrispondente bottone
     */
    public void showSearchTxF() {
        searchMovementBtn.setVisible(true);
        enterMovementTxF.setVisible(true);
        chooseOperationLbl.setVisible(false);
        errLbl.setVisible(false);
    }

    /**
     * Metodo che aggiorna le tabelle, utile dopo che ho aggiunto o rimosso una transazione o un account
     */
    private void refreshAllTables() {
        fillAccountTable();
        fillTagsTable();
        fillTransactionTable();
        errLbl.setVisible(false);
    }

    /**
     * Metodo che inizializza la comboBox con gli id delle transazioni effettuate sul ledger
     */
    public void setupIdTransactionCBox() {
        idTransactionCBox.getItems().clear();
        ArrayList<String> idTransactions = new ArrayList<>();
        for (ITransaction t : modelControl.getLedger().getTransactions()) {
            idTransactions.add(t.getId());
        }
        idTransactionCBox.getItems().addAll(idTransactions);
        idTransactionCBox.setVisibleRowCount(4);
    }

    /**
     * Metodo che va a creare un messaggio di errore utilizzando una label
     *
     * @param errMessage il messaggio di errore che vuole essere mostrato
     */
    @Override
    public void setErrorLbl(String errMessage) {
        errLbl.setVisible(true);
        errLbl.setText(errMessage);
    }

    /**
     * Metodo che imposta il controller del blocco model
     *
     * @param modelControl controller del model da utilizzare
     */
    @Override
    public void setModelControl(ModelControl modelControl) {
        this.modelControl = modelControl;
    }

    @Override
    public void createNumericTextField() {
    }

}
