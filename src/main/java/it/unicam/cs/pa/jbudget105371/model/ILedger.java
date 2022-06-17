package it.unicam.cs.pa.jbudget105371.model;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * Questa interfaccia è implementata dalle classi che hanno la responsabilità di gestire tutti i dati dell’applicazione.
 */
public interface ILedger {

    List<IAccount> getAccounts();

    void addTransaction(Date date, HashSet<ITag> categorie, List<IMovement> movementList);

    List<ITransaction> getTransactions();

    HashSet<ITag> getTags();

    IAccount addAccount(AccountType type, String name, String description, double opening);

    void addTag(ITag tag);

    void removeTransaction(ITransaction t);

    void removeTag(ITransaction t);

}



