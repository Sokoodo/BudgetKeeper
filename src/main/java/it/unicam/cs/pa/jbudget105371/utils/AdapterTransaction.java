package it.unicam.cs.pa.jbudget105371.utils;

import it.unicam.cs.pa.jbudget105371.model.IMovement;
import it.unicam.cs.pa.jbudget105371.model.ITag;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * Classe di appoggio usata per adattare gli oggetti Transaction prendendo solo i parametri che mi servono per la TableView,
 * in caso di necessita anche andando a trasformare dati al suo interno (es. Date to String),
 * è anche un modo per rendere più estendibile il progetto
 */
public class AdapterTransaction {

    private final String totalAmount;
    private final int movCount;
    private String tagNames;
    private String strDate;

    public AdapterTransaction(Date date, double totalAmountDouble, HashSet<ITag> tags, List<IMovement> movements) {
        createSimpleData(date);
        this.totalAmount = String.format("%.2f%s", totalAmountDouble, " EUR");
        this.movCount = movements.size();
        createTagNames(tags);
    }

    /**
     * Metodo che concatena i tag in stringa divisi attraverso "-"
     *
     * @param tags tags da convertire in stringa
     */
    private void createTagNames(HashSet<ITag> tags) {
        tagNames = "";
        boolean flag = true;
        for (ITag tag : tags) {
            if (flag) tagNames = tagNames.concat(tag.getName());
            else tagNames = tagNames.concat(" - " + tag.getName());
            flag = false;
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

    public String getStrDate() {
        return strDate;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public int getMovCount() {
        return movCount;
    }

    public String getTagNames() {
        return tagNames;
    }
}
