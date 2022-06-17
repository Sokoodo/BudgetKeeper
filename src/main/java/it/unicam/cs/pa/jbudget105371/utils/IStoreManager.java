package it.unicam.cs.pa.jbudget105371.utils;

import it.unicam.cs.pa.jbudget105371.model.ILedger;
import it.unicam.cs.pa.jbudget105371.model.Ledger;

import java.io.File;

/**
 * Questa interfaccia è implementata dalle classi che hanno la responsabilità di salvare e caricare dati dal file.
 */
public interface IStoreManager {

    default void save(ILedger ledger, String file) {
        save(ledger, new File(file));
    }

    default Ledger load(String file) {
        return load(new File(file));
    }

    void save(ILedger ledger, File file);

    Ledger load(File file);

}
