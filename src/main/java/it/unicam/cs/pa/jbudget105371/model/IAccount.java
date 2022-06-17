package it.unicam.cs.pa.jbudget105371.model;

import java.util.List;

/**
 * Questa interfaccia è implementata dalle classi che hanno la responsabilità di gestire un conto.
 */
public interface IAccount {

    String getName();

    String getDescription();

    String getId();

    AccountType getType();

    double getBalance();

    double getOpeningBalance();

    List<IMovement> getMovements();

    boolean addRemoveMoney(MovementType typeM, double amount);

    boolean addCredit(double amount);

    boolean addDebit(double amount);

    void addMovement(IMovement m);

    void removeMovement(IMovement m);

}





