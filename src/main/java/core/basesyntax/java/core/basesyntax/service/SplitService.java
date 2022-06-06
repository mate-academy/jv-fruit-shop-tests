package core.basesyntax.java.core.basesyntax.service;

import core.basesyntax.java.core.basesyntax.model.FruitTransaction;

public interface SplitService {
    FruitTransaction getTransactionFromRow(String line);
}
