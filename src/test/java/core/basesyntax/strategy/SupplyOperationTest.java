package core.basesyntax.strategy;

import static core.basesyntax.model.FruitTransaction.Operation.SUPPLY;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Storage;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private static Storage storage;
    private static OperationHandler operationHandler;

    @BeforeAll
    static void beforeAll() {
        storage = new Storage();
        operationHandler = new SupplyOperation(storage);
    }

    @Test
    void handleTransaction_correctSupplyOperation_ok() {
        storage.put("banana", 10);
        operationHandler.handleTransaction(new FruitTransaction(SUPPLY, "apple", 10));
        operationHandler.handleTransaction(new FruitTransaction(SUPPLY, "banana", 10));
        Map<String, Integer> actual = storage.getStorage();
        Map<String, Integer> expected = Map.of("banana", 20, "apple", 10);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void handleTransaction_negativeBalanceSupplyOperation_notOk() {
        Assertions.assertThrows(RuntimeException.class, () -> operationHandler.handleTransaction(
                        new FruitTransaction(SUPPLY, "banana", -10)),
                "Expected RuntimeException was not thrown in " + SupplyOperation.class);
    }
}
