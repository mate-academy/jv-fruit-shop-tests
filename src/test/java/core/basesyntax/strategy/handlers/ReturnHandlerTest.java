package core.basesyntax.strategy.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnHandlerTest {
    private OperationHandler operationHandler = new ReturnHandler();

    @BeforeEach
    void setUp() {
        Storage.storage.clear();
        Storage.storage.put("banana", 20);
    }

    @Test
    void operate_validTransaction_ok() {
        operationHandler.operate(new FruitTransaction(FruitTransaction.OperationType.RETURN,
                "banana", 5));
        Map<String, Integer> expected = Map.of("banana", 25);
        Map<String, Integer> actual = Storage.getStorage();
        assertEquals(expected, actual);
    }

    @Test
    void operate_nullFruit_notOk() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> operationHandler
                .operate(new FruitTransaction(FruitTransaction.OperationType.BALANCE,
                        null, 60)));
        assertEquals("Fruit can't be null", exception.getMessage());
    }

    @Test
    void operate_negativeQuantity_notOk() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> operationHandler
                .operate(new FruitTransaction(FruitTransaction.OperationType.BALANCE,
                        "banana", -5)));
        assertEquals("Quantity can't be negative", exception.getMessage());
    }
}
