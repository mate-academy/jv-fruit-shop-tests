package core.basesyntax.service.transaction.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitShopOperation;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.transaction.TransactionHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyTransactionHandlerTest {
    private TransactionHandler supplyTransactionHandler;

    @BeforeEach
    void setUp() {
        supplyTransactionHandler = new SupplyTransactionHandler();
        Storage.clear();
    }

    @Test
    void executeTransaction_OK() {
        Storage.addPair("apple", 50);
        Storage.addPair("banana", 70);
        FruitTransaction supplyTransaction = new FruitTransaction(
                FruitShopOperation.SUPPLY, "apple", 50);
        supplyTransactionHandler.executeTransaction(supplyTransaction);
        Map<String, Integer> expected = new HashMap<>(Map.of("apple", 100,"banana", 70));
        Assertions.assertEquals(expected, Storage.getAll(),
                "Supply operation doesn't work correctly:\n");
        Storage.clear();
    }
}
