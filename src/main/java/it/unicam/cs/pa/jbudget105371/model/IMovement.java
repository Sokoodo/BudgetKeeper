package it.unicam.cs.pa.jbudget105371.model;

import java.util.Date;
import java.util.HashSet;

/**
 * Questa interfaccia è implementata dalle classi che hanno la responsabilità di gestire un singolo movimento.
 */
public interface IMovement {

    String getDescription();

    MovementType getType();

    double getAmount();

    String getIdAccount();

    String getId();

    Date getDate();

    HashSet<ITag> getTags();

    void addTag(ITag t);

}





