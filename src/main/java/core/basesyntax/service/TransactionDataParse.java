package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;

public interface TransactionDataParse {
    /**
     * Parse data from CSV-file.
     *
     * @param line String with line from  CSV- file.
     */
    FruitTransaction parseTransaction(String line);
}
