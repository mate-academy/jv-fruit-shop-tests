package core.basesyntax.strategy.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.Map;
import org.junit.jupiter.api.Test;

class BalanceHandlerTest {
    private OperationHandler operationHandler = new BalanceHandler();

    @Test
    void operate_validTransaction_ok() {
        operationHandler.operate(new FruitTransaction(FruitTransaction.OperationType.BALANCE,
                "banana", 50));
        Map<String, Integer> expected = Map.of("banana", 50);
        Map<String, Integer> actual = Storage.getStorage();
        assertEquals(expected, actual);
    }

    @Test
    void operate_nullProduct_notOk() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> operationHandler
                .operate(new FruitTransaction(FruitTransaction.OperationType.BALANCE,
                null, 50)));
        assertEquals("Fruit can't be null", exception.getMessage());
    }

    @Test
    void operate_negativeQuantity_notOk() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> operationHandler
                .operate(new FruitTransaction(FruitTransaction.OperationType.BALANCE,
                        "banana", -10)));
        assertEquals("Quantity can't be negative", exception.getMessage());
    }
}
