package cc.ddrpa.dorian.infrastructure.utility.persistent;

public interface PersistableEnum<T> {
    T getPersistedValue();
}