package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;

public interface TransactionParserService {
    //void saveToStorage(List<String> list);
    FruitTransaction saveToStorage(String line);
}
