package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;

public interface FruitTransactionMapper {
    FruitTransaction[] buildFruitTransactions(String fileData);
}
