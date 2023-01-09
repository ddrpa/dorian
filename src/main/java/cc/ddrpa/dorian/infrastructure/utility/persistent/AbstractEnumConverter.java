package cc.ddrpa.dorian.infrastructure.utility.persistent;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;

@Converter
public abstract class AbstractEnumConverter<E extends Enum<E> & PersistableEnum<T>, T> implements AttributeConverter<E, T> {
    private final Class<E> clazz;

    public AbstractEnumConverter(Class<E> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T convertToDatabaseColumn(E attribute) {
        return attribute != null ? attribute.getPersistedValue() : null;
    }

    @Override
    public E convertToEntityAttribute(T dbData) {
        return Arrays.stream(clazz.getEnumConstants())
                .filter(i -> i.getPersistedValue().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}