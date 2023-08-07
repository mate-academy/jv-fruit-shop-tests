package core.basesyntax.strategy.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnHandlerTest {
    private OperationHandler operationHandler;

    @BeforeEach
    void setUp() {
        operationHandler = new ReturnHandler();
        Storage.storage.put("banana", 20);
    }

    @Test
    void operateReturn_validTransaction_ok() {
        operationHandler.operate(new FruitTransaction(FruitTransaction.OperationType.RETURN,
                "banana", 5));
        Map<String, Integer> expected = Map.of("banana", 25);
        Map<String, Integer> actual = Storage.getStorage();
        assertEquals(expected, actual);
    }

    @Test
    void operateReturn_nullFruit_notOk() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> operationHandler
                .operate(new FruitTransaction(FruitTransaction.OperationType.BALANCE,
                        null, 60)));
        assertEquals("Fruit can't be null", exception.getMessage());
    }

    @Test
    void operateReturn_negativeQuantity_notOk() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> operationHandler
                .operate(new FruitTransaction(FruitTransaction.OperationType.BALANCE,
                        "banana", -5)));
        assertEquals("Quantity can't be negative", exception.getMessage());
    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }
}
