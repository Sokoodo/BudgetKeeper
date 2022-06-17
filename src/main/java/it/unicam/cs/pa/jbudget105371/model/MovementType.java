package it.unicam.cs.pa.jbudget105371.model;

/**
 * Tipologia di movimento (DEBITS, CREDITS). La tipologia di movimento determina l’effetto di un movimento su un conto.
 * Infatti, il saldo d'un conto di tipo ASSET crescerà con movimenti di tipo CREDITS e diminuirà con movimenti di tipo DEBITS.
 * Viceversa, il saldo d’un conto di tipo LIABILITIES aumenterà con movimenti di tipo DEBITS e diminuirà con movimenti di tipo CREDITS.
 */
public enum MovementType {

    DEBITS,
    CREDITS

}
