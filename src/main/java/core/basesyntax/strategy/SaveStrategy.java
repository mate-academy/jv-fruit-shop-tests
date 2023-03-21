package core.basesyntax.strategy;

public interface SaveStrategy<T> {
    String NEGATIVE_STORAGE = "Negative fruit balance cannot exist.";
    String NEGATIVE_TRANSACTION = "Negative transaction values are not supported.";
    String WRONG_OPERATION = "Wrong operation type %s passed to %s.";
    void save(T value);
}
