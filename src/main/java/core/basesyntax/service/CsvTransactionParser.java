package core.basesyntax.service;

public interface CsvTransactionParser<L,T> {
    L csvParse(T data);
}
