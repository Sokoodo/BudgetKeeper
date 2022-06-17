package it.unicam.cs.pa.jbudget105371.model;

import java.util.*;

/**
 * Classe che implementa IMovement, un Movimento rappresenta una uscita(DEBIT) o entrata(Credit)
 * da un determinato conto.
 * Esempi di movimento sono l’accredito dello stipendio, o il pagamento di un fattura per un’utenza.
 */
public class Movement implements IMovement {

    private final String id;
    private final String description;
    private final Date date;
    private final String idAccount;
    private final Double amount;
    private final HashSet<ITag> tags;
    private final MovementType type;

    /**
     * Costruttore che costruisce un oggetto Movement e gli assegna un id unico
     *
     * @param idAccount   id dell'account correlato
     * @param type        tipo movimento ( credit , debit )
     * @param date        data movimento
     * @param description descrizione movimento
     * @param amount      importo movimento
     * @param tags        i tags del movimento
     */
    public Movement(String idAccount, MovementType type, Date date, String description, double amount, HashSet<ITag> tags) {
        this.idAccount = idAccount;
        this.date = date;
        this.description = description;
        this.amount = amount;
        this.tags = tags;
        this.type = type;
        id = "MOV" + UUID.randomUUID().toString();
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public MovementType getType() {
        return type;
    }

    @Override
    public double getAmount() {
        return amount;
    }

    @Override
    public String getIdAccount() {
        return idAccount;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public HashSet<ITag> getTags() {
        return tags;
    }

    /**
     * Metodo che aggiunge un tag alla lista di tag nel movimento se non è null
     *
     * @param t tag da aggiungere
     */
    @Override
    public void addTag(ITag t) {
        if (t != null) tags.add(t);
    }

    /**
     * Due Movimenti sono uguali se hanno stesso id e data
     *
     * @param o oggetto da confrontare
     * @return true se gli oggetti sono uguali, false altrimenti
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movement movement = (Movement) o;
        return id.equals(movement.id) &&
                date.equals(movement.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (ITag t : this.tags) {
            sb.append(t.toString()).append(" ");
        }
        return "IdAccount: " + this.idAccount + ", Tipo: " + this.type + ", Descrizione: " + this.description + ", Importo: " + this.amount + ", Tags: " + sb;
    }
}
