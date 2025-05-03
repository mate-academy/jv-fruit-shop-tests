package core.basesyntax.strategy;

import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Storage;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private static Storage storage;
    private static OperationHandler operationHandler;

    @BeforeAll
    static void beforeAll() {
        storage = new Storage();
        operationHandler = new BalanceOperation(storage);
    }

    @AfterEach
    void tearDown() {
        storage.clearStorage();
    }

    @Test
    void handleTransaction_correctBalanceOperation_ok() {
        operationHandler.handleTransaction(new FruitTransaction(BALANCE, "banana", 20));
        operationHandler.handleTransaction(new FruitTransaction(BALANCE, "apple", 50));
        Map<String, Integer> actual = storage.getStorage();
        Map<String, Integer> expected = Map.of("banana", 20, "apple", 50);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void handleTransaction_negativeBalance_notOk() {
        Assertions.assertThrows(RuntimeException.class, () -> operationHandler.handleTransaction(
                        new FruitTransaction(BALANCE, "banana", -10)),
                "Expected RuntimeException was not thrown in " + BalanceOperation.class);
    }
}
