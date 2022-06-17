package it.unicam.cs.pa.jbudget105371.model;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * Questa interfaccia è implementata dalle classi che hanno la responsabilità di gestire una transazione.
 */
public interface ITransaction {

    String getId();

    List<IMovement> getMovements();

    HashSet<ITag> getTags();

    void addTag(ITag t);

    void removeTag(ITag t);

    Date getDate();

    void addMovement(IMovement m);

    void removeMovement(IMovement m);

    double getTotalAmount();

}



