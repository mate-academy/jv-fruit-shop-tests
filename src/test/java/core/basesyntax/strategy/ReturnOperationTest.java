package core.basesyntax.strategy;

import static core.basesyntax.model.FruitTransaction.Operation.RETURN;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Storage;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private static Storage storage;
    private static OperationHandler operationHandler;

    @BeforeAll
    static void beforeAll() {
        storage = new Storage();
        operationHandler = new ReturnOperation(storage);
    }

    @AfterEach
    void tearDown() {
        storage.clearStorage();
    }

    @Test
    void handleTransaction_correctReturnOperation_ok() {
        storage.put("apple", 10);
        operationHandler.handleTransaction(new FruitTransaction(RETURN, "banana", 20));
        operationHandler.handleTransaction(new FruitTransaction(RETURN, "apple", 10));
        Map<String, Integer> actual = storage.getStorage();
        Map<String, Integer> expected = Map.of("apple", 20, "banana", 20);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void handleTransaction_negativeBalanceReturnOperation_notOk() {
        Assertions.assertThrows(RuntimeException.class, () -> operationHandler.handleTransaction(
                        new FruitTransaction(RETURN, "banana", -10)),
                "Expected RuntimeException was not thrown in " + ReturnOperation.class);
    }
}
