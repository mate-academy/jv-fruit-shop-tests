package core.basesyntax.strategy.impl;

import static core.basesyntax.util.FruitTestConstants.APPLE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.impl.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private PurchaseOperationHandler operationHandler;

    @BeforeEach
    void setUp() {
        operationHandler = new PurchaseOperationHandler(new FruitDaoImpl());
        Storage.fruitStorage.clear();
    }

    @Test
    void applyOperation_ValidTransaction_Ok() {
        Storage.fruitStorage.put(APPLE, 10);
        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE, APPLE, 4);
        operationHandler.applyOperation(transaction);
        Map<String, Integer> expected = Map.of(APPLE, 6);
        Map<String, Integer> actual = Storage.fruitStorage;
        assertEquals(expected, actual, "Purchase strategy is not working correctly!");
    }

    @Test
    void applyOperation_InsufficientQuantity_NotOk() {
        Storage.fruitStorage.put(APPLE, 2);
        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE, APPLE, 4);
        assertThrows(RuntimeException.class, () -> operationHandler.applyOperation(transaction),
                "Expected RuntimeException for insufficient quantity");
    }
}
