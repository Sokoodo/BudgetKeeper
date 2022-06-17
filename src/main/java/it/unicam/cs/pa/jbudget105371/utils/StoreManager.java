package it.unicam.cs.pa.jbudget105371.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import it.unicam.cs.pa.jbudget105371.model.*;

import java.io.*;

/**
 * Classe che implementa IStoreManager e si occupa del salvataggio del ledger nel file e del caricamento
 * del ledger dal file
 */
public class StoreManager implements IStoreManager {

    private final Gson gson;

    /**
     * Costruttore che inizializza gson attraverso l'interfaceSerializer che permette di annullare gli errori dovuti
     * all'utilizzo di interfacce, usa il prettyPrinting così da avere una visualizzazione più comprensibile
     * nel File .json
     */
    public StoreManager() {
        gson = new GsonBuilder()
                .registerTypeAdapter(ITransaction.class, InterfaceSerializer.interfaceSerializer(Transaction.class))
                .registerTypeAdapter(IAccount.class, InterfaceSerializer.interfaceSerializer(Account.class))
                .registerTypeAdapter(ITag.class, InterfaceSerializer.interfaceSerializer(Tag.class))
                .registerTypeAdapter(IMovement.class, InterfaceSerializer.interfaceSerializer(Movement.class))
                .setPrettyPrinting()
                .create();
    }

    /**
     * Si occupa del salvataggio del ledger in un File json utilizzando un FileWriter
     *
     * @param ledger il ledger da salvare
     * @param file   il File in cui salvarlo
     */
    @Override
    public void save(ILedger ledger, File file) {
        Writer writer;
        try {
            writer = new FileWriter(file);
            String sLedger = gson.toJson(ledger);
            writer.write(sLedger);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.err.println("Errore salvataggio nel file");
        }
    }

    /**
     * Si occupa del caricamento del ledger da un precedente salvataggio da uno specifico File
     *
     * @param file file da cui prelevare un precedente ledger
     * @return Ledger se il caricamento va a buon fine altrimenti null se il file non esiste o se è impossibile
     * leggere il ledger per problemi di JsonParse
     */
    @Override
    public Ledger load(File file) {
        BufferedReader bufferedReader;
        Ledger ledger;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            ledger = gson.fromJson(bufferedReader, Ledger.class);
            return ledger;
        } catch (FileNotFoundException | JsonParseException e) {
            return null;
        }
    }
}





