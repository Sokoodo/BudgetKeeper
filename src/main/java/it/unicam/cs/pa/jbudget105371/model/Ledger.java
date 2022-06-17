package it.unicam.cs.pa.jbudget105371.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * Classe che implementa ILedger che ha la responsabilità di gestire tutti i dati fondamentali dell'applicazione,
 * contiene le operazioni principali del programma quali: aggiunta e rimozione
 * Transazioni e Tag, aggiunta Account e tutti i loro getter
 */
public class Ledger implements ILedger {

    private final ArrayList<IAccount> accounts;
    private final List<ITransaction> transactions;
    private final HashSet<ITag> tagList;

    /**
     * Costruttore che inizializza Liste e HashSet
     */
    public Ledger() {
        this.accounts = new ArrayList<>();
        this.transactions = new ArrayList<>();
        this.tagList = new HashSet<>();
    }

    @Override
    public List<IAccount> getAccounts() {
        return accounts;
    }

    /**
     * Metodo che agiunge una transazione alla lista di transazioni del ledger e calcola e imposta il totalAmount della
     * transazione stessa
     *
     * @param date         data transazione
     * @param tags         Tags transazione
     * @param movementList movimenti transazione
     */
    @Override
    public void addTransaction(Date date, HashSet<ITag> tags, List<IMovement> movementList) {
        if (!movementList.isEmpty()) {
            Transaction tran = new Transaction(date, tags, movementList);
            double c = 0;
            for (IMovement m : movementList) {
                c += m.getAmount();
            }
            tran.setTotalAmount(c);
            transactions.add(tran);
        } else {
            System.err.println("Transazione non eseguita, movimenti non effettuabili! ");
        }
    }

    @Override
    public List<ITransaction> getTransactions() {
        return transactions;
    }

    @Override
    public HashSet<ITag> getTags() {
        return tagList;
    }

    /**
     * Metodo che Crea un account e lo aggiunge alla lista di account nel ledger
     *
     * @param type        tipo account
     * @param name        nome account
     * @param description descrizione account
     * @param opening     budget di apertura
     * @return Account creato
     */
    @Override
    public IAccount addAccount(AccountType type, String name, String description, double opening) {
        Account acc = new Account(name, type, description, opening);
        accounts.add(acc);
        return acc;
    }

    /**
     * Metodo che aggiunge un tag alla lista di tag nel ledger
     *
     * @param tag tag da aggiungere
     */
    @Override
    public void addTag(ITag tag) {
        tagList.add(tag);
    }

    /**
     * Metodo che rimuove una transazione dalla lista transazioni, per farlo tolgo anche i movimenti della
     * transazione dagli account corrispondenti e i suoi tag corrispondenti
     *
     * @param t transazione da rimuovere
     */
    @Override
    public void removeTransaction(ITransaction t) {
        for (IMovement m : t.getMovements()) {
            for (IAccount acc : accounts) {
                if (m.getIdAccount().equals(acc.getId())) {
                    acc.removeMovement(m);
                }
            }
        }
        removeTag(t);
        transactions.remove(t);
    }

    /**
     * Metodo che rimuove tags (della transazione che passo) dalla lista tag del ledger ma solo se non sono usati
     * in altre transazioni altrimenti non vengono tolte
     *
     * @param t transazione in questione
     */
    @Override
    public void removeTag(ITransaction t) {
        boolean contains;
        for (IMovement m : t.getMovements()) {
            for (ITag tag : m.getTags()) {
                contains = false;
                for (ITransaction tra : transactions) {
                    if (!tra.equals(t)) {
                        for (IMovement move : tra.getMovements()) {
                            if (move.getTags().contains(tag)) contains = true;
                        }
                    }
                }
                if (!contains) tagList.remove(tag);
            }
        }
    }

}
