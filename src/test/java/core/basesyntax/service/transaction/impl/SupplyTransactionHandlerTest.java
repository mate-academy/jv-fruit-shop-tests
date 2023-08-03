package core.basesyntax.service.transaction.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
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
        Storage.addPair(Fruit.APPLE, 50);
        Storage.addPair(Fruit.BANANA, 70);
    }

    @Test
    void executeTransaction_OK() {
        FruitTransaction supplyTransaction = new FruitTransaction(
                Operation.SUPPLY, Fruit.APPLE, 50);
        supplyTransactionHandler.executeTransaction(supplyTransaction);
        Map<Fruit, Integer> expected = new HashMap<>(Map.of(Fruit.APPLE, 100,Fruit.BANANA, 70));
        Assertions.assertEquals(expected, Storage.getAll(),
                "Supply operation doesn't work correctly:\n");
        Storage.clear();
    }
}
