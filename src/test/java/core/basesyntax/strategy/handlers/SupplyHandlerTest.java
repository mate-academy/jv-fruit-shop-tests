package core.basesyntax.strategy.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyHandlerTest {
    private OperationHandler operationHandler = new SupplyHandler();

    @BeforeEach
    void setUp() {
        Storage.storage.clear();
        Storage.storage.put("banana", 50);
    }

    @Test
    void operate_validTransaction_ok() {
        operationHandler.operate(new FruitTransaction(FruitTransaction.OperationType.SUPPLY,
                "banana", 20));
        Map<String, Integer> expected = Map.of("banana", 70);
        Map<String, Integer> actual = Storage.getStorage();
        assertEquals(expected, actual);
    }

    @Test
    void operate_nulFruit_notOk() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> operationHandler
                .operate(new FruitTransaction(FruitTransaction.OperationType.BALANCE,
                        null, 5)));
        assertEquals("Fruit can't be null", exception.getMessage());
    }

    @Test
    void operate_negativeQuantity_notOk() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> operationHandler
                .operate(new FruitTransaction(FruitTransaction.OperationType.BALANCE,
                        "banana", -6)));
        assertEquals("Quantity can't be negative", exception.getMessage());
    }
}
