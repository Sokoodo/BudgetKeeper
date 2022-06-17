package it.unicam.cs.pa.jbudget105371.utils;

import it.unicam.cs.pa.jbudget105371.model.*;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Classe usata per adattare gli oggetti Movement prendendo solo i parametri che mi servono per la TableView,
 * in caso di necessita anche andando a trasformare dati al suo interno (es. Date to String),
 * è anche un modo per rendere più estendibile il progetto.
 */
public class AdapterMovement {

    private final String description;
    private final String amount;
    private final MovementType type;
    private String accountName;
    private String strDate;

    public AdapterMovement(String description, Date date, Double amountDouble, MovementType type, String idAccount, ILedger ledger) {
        this.description = description;
        createSimpleData(date);
        this.amount = String.format("%.2f%s", amountDouble, " EUR");
        this.type = type;
        findAccount(ledger, idAccount);
    }

    /**
     * Metodo usato per cercare il nome corrispondente a un certo idAccount
     *
     * @param ledger    ledger dove cercare l'account
     * @param idAccount idAccount da cui rilevare il nomeAccount
     */
    private void findAccount(ILedger ledger, String idAccount) {
        for (IAccount a : ledger.getAccounts()) {
            if (a.getId().equals(idAccount)) {
                accountName = a.getName();
            }
        }
    }

    /**
     * Metodo che mi converte una data in stringa attraverso il SimpleDateFormat
     *
     * @param date data da convertire in stringa
     */
    private void createSimpleData(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        strDate = formatter.format(date);
    }

    public String getDescription() {
        return description;
    }

    public String getStrDate() {
        return strDate;
    }

    public String getAmount() {
        return amount;
    }

    public MovementType getType() {
        return type;
    }

    public String getAccountName() {
        return accountName;
    }
}
