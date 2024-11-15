package core.basesyntax.service.impl;

import static core.basesyntax.db.Storage.storage;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.impl.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationStrategy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private static FruitDao fruitDao;
    private static OperationStrategy purchaseOperation;

    @BeforeAll
    static void beforeAll() {
        fruitDao = new FruitDaoImpl();
        purchaseOperation = new PurchaseOperation(fruitDao);
    }

    @AfterEach
    void tearDown() {
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
        assertEquals(expected, reminderQuantityActual);
    }
}
