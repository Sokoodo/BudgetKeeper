package it.unicam.cs.pa.jbudget105371.utils;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Classe che implementa JsonSerializer e JsonDeserializer che mi va a risolvere il problema per cui non posso
 * implementare le interfacce, attraverso questa classe posso invece serializzare e deserializzare senza
 * riscontrare errori dovuti a ciò.
 */
final class InterfaceSerializer<T> implements JsonSerializer<T>, JsonDeserializer<T> {

    private final Class<T> implementationClass;

    private InterfaceSerializer(final Class<T> implementationClass) {
        this.implementationClass = implementationClass;
    }

    static <T> InterfaceSerializer<T> interfaceSerializer(final Class<T> implementationClass) {
        return new InterfaceSerializer<>(implementationClass);
    }

    @Override
    public JsonElement serialize(final T value, final Type type, final JsonSerializationContext context) {
        final Type targetType;
        if (value != null) {
            targetType = value.getClass();
        } else {
            targetType = type;
        }
        return context.serialize(value, targetType);
    }

    @Override
    public T deserialize(final JsonElement jsonElement, final Type typeOfT, final JsonDeserializationContext context) {
        return context.deserialize(jsonElement, implementationClass);
    }

}
