package it.unicam.cs.pa.jbudget105371.model;

import java.util.Objects;
import java.util.UUID;

/**
 * Categorie di transazioni e movimenti, ha la responsabilità di definire una categoria di spesa/guadagno,
 * 2 tag sono uguali se hanno lo stesso nome, ogni tag possiede un id identificativo
 */
public class Tag implements ITag {

    private final String name;
    private final String id;

    /**
     * Costruttore che costruisce un Tag e crea un id unico
     *
     * @param name nome tag
     */
    public Tag(String name) {
        this.name = name;
        id = "TAG" + UUID.randomUUID().toString();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getId() {
        return id;
    }

    /**
     * Due tag sono uguali se hanno lo stesso nome
     *
     * @param o oggetto da confrontare
     * @return true se i due oggetti sono uguali, false altrimenti
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return name.trim().toUpperCase().equals(tag.name.trim().toUpperCase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "(ID: " + this.id + ", Nome: " + this.name + ")";
    }
}
