package it.unicam.cs.pa.jbudget105371.control;

import it.unicam.cs.pa.jbudget105371.model.*;
import it.unicam.cs.pa.jbudget105371.utils.*;

import java.io.File;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

/**
 * Controller del blocco model che si occupa della "comunicazione" tra il blocco model e il blocco view,
 * in particolare del caricamento dei dati dal file e del suo salvataggio, creazione account, tranzasioni,
 * tags e movimenti
 */
public class ModelControl implements ModelControlInterface {

    private final ILedger ledger;
    private final StoreManager storeManager;
    private final File myFile;

    /**
     * Costruttore che crea un nuovo StoreManager e cerca il ledger attraverso il file passato
     *
     * @param myFile file da cui estrapolare il ledger
     */
    public ModelControl(File myFile) {
        this.storeManager = new StoreManager();
        this.myFile = myFile;
        this.ledger = findLedger(myFile);
    }

    /**
     * Metodo che va a cercare il ledger nel file .json
     *
     * @return il Ledger trovato nel file o un new Ledger() se il caricamento per qualche motivo non va a buon fine
     */
    @Override
    public ILedger findLedger(File f) {
        Ledger l;
        l = new StoreManager().load(f);
        if (l == null) return new Ledger();
        else return l;
    }

    /**
     * Metodo che mi salva il ledger nel file .json
     */
    @Override
    public void saveLedger() {
        storeManager.save(ledger, myFile);
    }

    /**
     * Metodo che preso un idTransaction mi cerca la ITransaction corrispondente a quell'id e la va a rimuovere
     * dal ledger
     *
     * @param idTransaction id Transazione da rimuovere
     */
    @Override
    public void removeTransaction(String idTransaction) {
        ITransaction transactionToRemove = null;
        for (ITransaction t : ledger.getTransactions()) {
            if (t.getId().equals(idTransaction)) {
                transactionToRemove = t;
            }
        }
        if (transactionToRemove != null) {
            ledger.removeTransaction(transactionToRemove);
        }
    }

    /**
     * Metodo che converte gli Account attraverso un apposito adapter in modo da poter mostrare le informazioni
     * come si vuole, nel modo piu opportuno possibile e rendendo il programma più estendibile
     *
     * @return lista degli Account adattati pronti per essere inseriti nella tabelView
     */
    public ArrayList<AdapterAccount> convertAccounts() {
        ArrayList<AdapterAccount> aaList = new ArrayList<>();
        for (IAccount a : ledger.getAccounts()) {
            aaList.add(new AdapterAccount(a.getName(), a.getDescription(), a.getOpeningBalance(), a.getBalance(), a.getType(), a.getMovements()));
        }
        return aaList;
    }

    /**
     * Metodo che converte le transazioni attraverso un apposito adapter in modo da poter mostrare le informazioni
     * come si vuole, nel modo piu opportuno possibile e rendendo il programma più estendibile
     *
     * @return lista di Transazioni adattate pronte per essere inseriti nella tabelView
     */
    public ArrayList<AdapterTransaction> convertTransactions() {
        ArrayList<AdapterTransaction> atList = new ArrayList<>();
        for (ITransaction t : ledger.getTransactions()) {
            atList.add(new AdapterTransaction(t.getDate(), t.getTotalAmount(), t.getTags(), t.getMovements()));
        }
        return atList;
    }

    /**
     * Metodo che converte i Tag attraverso un apposito adapter in modo da poter mostrare le informazioni
     * come si vuole, nel modo piu opportuno possibile e rendendo il programma più estendibile
     *
     * @return lista di tag adattati pronti per essere inseriti nella tableView
     */
    public HashSet<AdapterTags> convertTags() {
        HashSet<AdapterTags> atList = new HashSet<>();
        for (ITag t : ledger.getTags()) {
            atList.add(new AdapterTags(t.getName(), t.getId()));
        }
        return atList;
    }

    /**
     * Metodo che converte i movimenti attraverso un apposito adapter in modo da poter mostrare le informazioni
     * come si vuole, nel modo piu opportuno possibile e rendendo il programma più estendibile
     *
     * @param iMovements movimenti da convertire
     * @return lista di movimenti adattati pronti per essere inseriti nella tabelView
     */
    public ArrayList<AdapterMovement> convertMovements(List<IMovement> iMovements) {
        ArrayList<AdapterMovement> amList = new ArrayList<>();
        for (IMovement m : iMovements) {
            amList.add(new AdapterMovement(m.getDescription(), m.getDate(), m.getAmount(), m.getType(), m.getIdAccount(), ledger));
        }
        return amList;
    }

    /**
     * Metodo che fa la ricerca dei movimenti attraverso il tag selezionato
     *
     * @param tag tag da cercare
     * @return lista di movimenti corrispondenti al tag passato
     */
    public List<IMovement> movementsByTag(String tag) {
        List<IMovement> movementList = new ArrayList<>();
        for (ITransaction tran : ledger.getTransactions()) {
            for (IMovement m : tran.getMovements()) {
                for (ITag t : m.getTags()) {
                    if (!movementList.contains(m)) {
                        if (t.getName().toUpperCase().equals(tag.toUpperCase())) {
                            movementList.add(m);
                        }
                    }
                }
            }
        }
        return movementList;
    }

    /**
     * Metodo che controlla se il tag selezionato è già presente nel ledger, se non è presente nel ledger lo aggiunge
     * sia alla lista che alla lista che verrà poi inserita nel ledger, altrimenti solo alla lista passata
     *
     * @param tag  tag da controllare
     * @param tags lista dove aggiungere il tag
     */
    public void alreadyExistingTag(ITag tag, HashSet<ITag> tags, HashSet<ITag> tagsForLedger) {
        boolean flag = false;
        for (ITag t : ledger.getTags()) {
            if (tag.equals(t)) {
                tags.add(t);
                flag = true;
            }
        }
        if (!flag) {
            tagsForLedger.add(tag);
            tags.add(tag);
        }
    }

    /**
     * Metodo che aggiunge una lista di tag alla lista di tag del ledger
     */
    @Override
    public void addTagsToLedger(HashSet<ITag> tagsForLedger) {
        for (ITag iTag : tagsForLedger) {
            ledger.addTag(iTag);
        }
    }

    /**
     * Metodo che mi crea una transazione con tutti i parametri necessari e me la aggiunge al ledger
     */
    @Override
    public void createTransaction(HashSet<ITag> transactionTags, List<IMovement> movements, LocalDate localDate) {
        Date date = takeData(localDate);
        ledger.addTransaction(date, transactionTags, movements);
    }

    /**
     * Metodo che va a prendere il valore del DatePicker e la trasforma in oggetto Date
     *
     * @return valore Date corrispondente al DatePicker
     */
    public Date takeData(LocalDate localDate) {
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        return Date.from(instant);
    }

    /**
     * Metodo che prende la stringa con il nome dell'account selezionato e mi va a cercare l'account relativo a quel
     * nome Account e me lo ritorna
     *
     * @return IAccount relativo al nome account passato
     */
    public IAccount findAccount(String nameAccount) {
        for (IAccount a : ledger.getAccounts()) {
            if (a.getName().trim().toUpperCase().equals(nameAccount.trim().toUpperCase())) {
                return a;
            }
        }
        return null;
    }

    /**
     * Metodo che aggiunge ad ogni account il suo movimento effettuato corrispondente
     *
     * @param movements movimenti da associare ad ogni account di indice corrispondente
     * @param accounts  account a cui associare i movimenti
     */
    public void setMovementToAccount(List<IMovement> movements, List<IAccount> accounts) {
        for (int i = 0; i < movements.size(); i++) {
            accounts.get(i).addMovement(movements.get(i));
        }
    }

    /**
     * Metodo che mi va a creare un movimento con tutti i parametri necessari ottenuti dopo il controllo
     * e lo aggiunge alla lista di movimenti
     */
    @Override
    public IMovement createMovement(LocalDate localDate, HashSet<ITag> t, String nameAccount, MovementType type,
                               String description, String stringAmount, List<IMovement> movements, List<IAccount> accounts) {
        Date date = takeData(localDate);
        IAccount acc = findAccount(nameAccount);
        String idAccount = acc.getId();
        double amount = Double.parseDouble(stringAmount);
        IMovement m = null;
        if (type == MovementType.DEBITS) amount *= -1;
        if (acc.addRemoveMoney(type, amount)) {
            m = new Movement(idAccount, type, date, description, amount, t);
            movements.add(m);
            accounts.add(acc);
            return m;
        } else return m;
    }

    /**
     * Metodo che si occupa della creazione di un account e di aggiungere quest'ultimo al ledger
     *
     * @param name nome account
     * @param type tipo account
     * @param description descrizione account
     * @param stringOpening budget iniziale account
     */
    @Override
    public void createAccount(String name, AccountType type, String description, String stringOpening) {
        double openingBalance = Double.parseDouble(stringOpening);
        ledger.addAccount(type, name, description, openingBalance);
    }

    /**
     * Metodo che restituisce il this.ledger
     *
     * @return il ledger attuale
     */
    @Override
    public ILedger getLedger() {
        return this.ledger;
    }

}






