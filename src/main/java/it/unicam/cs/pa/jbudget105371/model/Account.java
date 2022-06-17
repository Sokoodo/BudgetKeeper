package it.unicam.cs.pa.jbudget105371.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Classe che implementa IAccount, un Account è un conto che può essere di tipo :
 * LIABILITIES (come la carta di credito o i prestiti) che rappresentano invece dei debiti da estinguere o
 * ASSETS (come la cassa o il conto corrente bancario) che rappresentano la nostra disponibilità di denaro;
 */
public class Account implements IAccount {

    private final String name;
    private final String description;
    private final double openingBalance;
    private final String id;
    private double balance;
    private final AccountType type;
    private final List<IMovement> movements;

    /**
     * Costruttore che crea un account e un ID unico
     *
     * @param name           nome account
     * @param type           tipo di account
     * @param description    descrizione
     * @param openingBalance budget iniziale
     */
    public Account(String name, AccountType type, String description, double openingBalance) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.openingBalance = openingBalance;
        this.balance = openingBalance;
        this.id = "AAC" + UUID.randomUUID().toString();
        this.movements = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public AccountType getType() {
        return type;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public double getOpeningBalance() {
        return openingBalance;
    }

    @Override
    public List<IMovement> getMovements() {
        return movements;
    }

    /**
     * Metodo che preso un tipo, se è tipo credit avvia addCredit se è debit avvia addDebit
     *
     * @param typeM  tipo del movimento
     * @param amount importo da gestire
     * @return true se transazione è effettuata correttamente, false altrimenti
     */
    @Override
    public boolean addRemoveMoney(MovementType typeM, double amount) {
        if (typeM == MovementType.CREDITS) {
            return addCredit(amount);
        } else {
            return addDebit(amount);
        }
    }

    /**
     * Se è un account di tipo assets aggiunge l'importo al balance altrimenti li rimuove
     * se è di tipo Liabilities (se il balance scende sotto lo 0 il movimento verrò annullato)
     *
     * @param amount importo da gestire
     * @return true se la transazione è stata effettuata, altrimenti false
     */
    @Override
    public boolean addCredit(double amount) {
        if (type == AccountType.ASSETS) {
            balance += amount;
            return true;
        } else if (type == AccountType.LIABILITIES) {
            if (balance - amount < 0) {
                return false;
            } else {
                balance -= amount;
                return true;
            }
        }
        return false;
    }

    /**
     * Se è un account di tipo Liabilities aggiunge importo al balance altrimenti li rimuove
     * se è di tipo Assets (se il balance scende sotto lo 0 il movimento verrò annullato)
     *
     * @param amount importo da gestire
     * @return true se la transazione è stata effettuata altrimenti false
     */
    @Override
    public boolean addDebit(double amount) {
        if (type == AccountType.ASSETS) {
            amount *= -1;
            if (balance - amount < 0) {
                return false;
            } else {
                balance -= amount;
                return true;
            }
        } else if (type == AccountType.LIABILITIES) {
            amount *= -1;
            balance += amount;
            return true;
        }
        return false;
    }

    /**
     * Metodo che aggiunge un movimento alla lista di Movimenti.
     *
     * @param m movimento da aggiungere alla lista nell'Account
     */
    @Override
    public void addMovement(IMovement m) {
        if (m != null) movements.add(m);
    }

    /**
     * Metodo che rimuove movimento dalla lista di Movimenti.
     *
     * @param m movimento da rimuovere
     */
    @Override
    public void removeMovement(IMovement m) {
        if (m != null) movements.remove(m);
    }

    /**
     * 2 Account sono uguali se hanno lo stesso id e nome
     *
     * @param o oggetto da confrontare
     * @return true se i due oggetti sono uguali, false altrimenti
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return name.equals(account.name) &&
                id.equals(account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }

    @Override
    public String toString() {
        return "Nome: " + name + ", Tipo: " + type + ", Descrizione: " + description + ", Id: " + id + ", Saldo: " + balance;
    }
}
