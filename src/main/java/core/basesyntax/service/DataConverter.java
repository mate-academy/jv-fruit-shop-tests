package core.basesyntax.service;

public interface DataConverter<T, R> {
    R convertCsvToFruitTransaction(T inputReport);
}
