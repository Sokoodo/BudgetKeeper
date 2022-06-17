package it.unicam.cs.pa.jbudget105371.utils;

/**
 * Classe usata per adattare gli oggetti Tag prendendo solo i parametri che mi servono per la TableView,
 * in caso di necessita anche andando a trasformare dati al suo interno (es. Date to String),
 * è anche un modo per rendere più estendibile il progetto
 */
public class AdapterTags {

    private final String name;
    private final String id;

    public AdapterTags(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

}
