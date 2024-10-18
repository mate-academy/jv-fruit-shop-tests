package core.basesyntax.strategy;

import static core.basesyntax.model.FruitTransaction.Operation.PURCHASE;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Storage;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private static Storage storage;
    private static OperationHandler operationHandler;

    @BeforeAll
    static void beforeAll() {
        storage = new Storage();
        operationHandler = new PurchaseOperation(storage);
    }

    @AfterEach
    void tearDown() {
        storage.clearStorage();
    }

    @Test
    void handleTransaction_correctOperation_ok() {
        storage.put("apple", 50);
        operationHandler.handleTransaction(new FruitTransaction(PURCHASE, "apple", 20));
        Map<String, Integer> actual = storage.getStorage();
        Map<String, Integer> expected = Map.of("apple", 30);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void handleTransaction_negativeBalancePurchaseOperation_notOk() {
        Assertions.assertThrows(RuntimeException.class, () -> operationHandler.handleTransaction(
                        new FruitTransaction(PURCHASE, "banana", -10)),
                "Expected RuntimeException was not thrown in " + PurchaseOperation.class);
    }

    @Test
    void handleTransaction_purchaseOperationGreaterThenBalance_notOk() {
        storage.put("banana", 20);
        Assertions.assertThrows(RuntimeException.class, () -> operationHandler.handleTransaction(
                new FruitTransaction(PURCHASE, "banana", 21)));
    }

    @Test
    void handleTransaction_fruitNotFoundInStorage_notOk() {
        Assertions.assertThrows(RuntimeException.class, () -> operationHandler.handleTransaction(
                new FruitTransaction(PURCHASE, "banana", 10)));
    }
}
