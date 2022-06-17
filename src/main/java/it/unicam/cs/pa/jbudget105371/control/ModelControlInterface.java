package it.unicam.cs.pa.jbudget105371.control;

import it.unicam.cs.pa.jbudget105371.model.*;

import java.io.File;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

/**
 * Questa interfaccia è implementata dalle classi che hanno la responsabilità di fare da controller per il
 * blocco model.
 */
public interface ModelControlInterface {

    ILedger findLedger(File f);

    void saveLedger();

    void createTransaction(HashSet<ITag> transactionTags, List<IMovement> movements, LocalDate localDate);

    IMovement createMovement(LocalDate localDate, HashSet<ITag> t, String nameAccount, MovementType type,
                             String description, String stringAmount, List<IMovement> movements, List<IAccount> accounts);

    void createAccount(String name, AccountType type, String description, String stringOpening);

    ILedger getLedger();

    void addTagsToLedger(HashSet<ITag> tagsForLedger);

    void removeTransaction(String idTransaction);

}
