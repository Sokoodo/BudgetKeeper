package it.unicam.cs.pa.jbudget105371.model;

import java.util.*;

/**
 * Classe che implementa ITransaction, una Transazione rappresenta un insieme di movimenti e tag.
 * L’uso della transazione è necessario perché alcune spese possono coinvolgere più movimenti.
 */
public class Transaction implements ITransaction {

    private String id;
    private final Date date;
    private final HashSet<ITag> tags;
    private final List<IMovement> movements;
    private double totalAmount;

    /**
     * Costruttore che costruisce una transazione e crea un Id unico, totalAmount è l'importo totale della transazione
     *
     * @param date      data transazione
     * @param tags      tags transazione
     * @param movements movimenti della transazione
     */
    public Transaction(Date date, HashSet<ITag> tags, List<IMovement> movements) {
        this.date = date;
        this.tags = tags;
        this.movements = movements;
        this.id = "TRN" + UUID.randomUUID().toString();
        this.totalAmount = 0;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public List<IMovement> getMovements() {
        return movements;
    }

    @Override
    public HashSet<ITag> getTags() {
        return tags;
    }

    /**
     * Metodo che aggiunge un tag alla lista di tag della transazione
     *
     * @param t tag da aggiungere
     */
    @Override
    public void addTag(ITag t) {
        if (t != null) tags.add(t);
    }

    /**
     * Metodo che rimuove tag dalla lista di tag della transazione
     *
     * @param t tag da rimuovere
     */
    @Override
    public void removeTag(ITag t) {
        if (t != null) tags.remove(t);
    }

    @Override
    public Date getDate() {
        return date;
    }

    /**
     * Metodo che aggiunge un movimento alla lista di movimenti nella transazione
     *
     * @param m movimento da aggiungere
     */
    @Override
    public void addMovement(IMovement m) {
        if (m != null) movements.add(m);
    }

    /**
     * Metodo che rimuove un movimento dalla lista di movimenti nella transazione
     *
     * @param m movimento da rimuovere
     */
    @Override
    public void removeMovement(IMovement m) {
        if (m != null) movements.remove(m);
    }

    @Override
    public double getTotalAmount() {
        return totalAmount;
    }

    /**
     * Metodo che imposta l'importo totale della transazione
     *
     * @param totalAmount importo totale
     */
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * Due Transazioni sono uguali se hanno stesso id e data
     *
     * @param o oggetto da confrontare
     * @return true se sono uguali, false altrimenti
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return id.equals(that.id) &&
                date.equals(that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (ITag t : this.tags) {
            s.append(t.toString()).append(" ");
        }
        return "ID: " + this.id + ", Data: " + this.date + ", Importo Totale: " + totalAmount + ", Tags: " + s;
    }
}
