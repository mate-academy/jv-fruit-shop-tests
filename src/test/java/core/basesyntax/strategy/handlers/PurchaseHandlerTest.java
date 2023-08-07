package core.basesyntax.strategy.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseHandlerTest {
    private OperationHandler operationHandler = new PurchaseHandler();

    @BeforeEach
    void setUp() {
        Storage.storage.clear();
        Storage.storage.put("banana", 100);
    }

    @Test
    void operate_validTransaction_ok() {
        operationHandler.operate(new FruitTransaction(FruitTransaction.OperationType.PURCHASE,
                "banana", 50));
        Map<String, Integer> expected = Map.of("banana", 50);
        Map<String, Integer> actual = Storage.getStorage();
        assertEquals(expected, actual);
    }

    @Test
    void operate_notValidTransaction_notOk() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> operationHandler
                .operate(new FruitTransaction(FruitTransaction.OperationType.PURCHASE,
                "banana", 110)));
        assertEquals("Can't do purchase, because of shortage of banana. You can buy only 100",
                exception.getMessage());
    }

    @Test
    void operate_nullFruit_notOk() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> operationHandler
                .operate(new FruitTransaction(FruitTransaction.OperationType.BALANCE,
                        null, 20)));
        assertEquals("Fruit can't be null", exception.getMessage());
    }

    @Test
    void operate_negativeQuantity_notOk() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> operationHandler
                .operate(new FruitTransaction(FruitTransaction.OperationType.BALANCE,
                        "banana", -100)));
        assertEquals("Quantity can't be negative", exception.getMessage());
    }
}
