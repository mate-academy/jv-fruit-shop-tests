package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;

public interface TransactionParserService {
    FruitTransaction saveToStorage(String line);
}
