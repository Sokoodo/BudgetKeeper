package it.unicam.cs.pa.jbudget105371.utils;

import it.unicam.cs.pa.jbudget105371.model.AccountType;
import it.unicam.cs.pa.jbudget105371.model.IMovement;

import java.util.List;

/**
 * Classe usata per adattare gli oggetti Account prendendo solo i parametri che mi servono per la TableView,
 * in caso di necessita anche andando a trasformare dati al suo interno (es. Date to String),
 * è anche un modo per rendere più estendibile il progetto
 */
public class AdapterAccount {

    private final String name;
    private final String description;
    private final String openingBalance;
    private final String currentBalance;
    private final AccountType type;
    private final int movCount;

    public AdapterAccount(String name, String description, Double openingBalance, Double currentBalanceDouble, AccountType type, List<IMovement> movements) {
        this.name = name;
        this.description = description;
        this.openingBalance = String.format("%.2f%s", openingBalance, " EUR");
        this.currentBalance = String.format("%.2f%s", currentBalanceDouble, " EUR");
        this.type = type;
        this.movCount = movements.size();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getOpeningBalance() {
        return openingBalance;
    }

    public String getCurrentBalance() {
        return currentBalance;
    }

    public AccountType getType() {
        return type;
    }

    public int getMovCount() {
        return movCount;
    }


}
