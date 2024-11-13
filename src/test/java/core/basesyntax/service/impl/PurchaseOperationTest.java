package core.basesyntax.service.impl;

import static core.basesyntax.db.Storage.storage;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.impl.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private FruitDao fruitDao = new FruitDaoImpl();
    private OperationStrategy purchaseOperation = new PurchaseOperation(fruitDao);

    @BeforeEach
    void setUp() {
        storage.clear();
    }

    @Test
    void purchase_operation_successful() {
        storage.put("banana", 40);
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 35);
        purchaseOperation.execute(fruitTransaction);
        int reminderQuantityActual = storage.get("banana");
        int expected = 5;
        Assertions.assertEquals(expected, reminderQuantityActual);
    }
}
