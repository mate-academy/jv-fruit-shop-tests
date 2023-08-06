package core.basesyntax.strategy.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseHandlerTest {
    private OperationHandler operationHandler;

    @BeforeEach
    void setUp() {
        operationHandler = new PurchaseHandler();
        Storage.storage.clear();
        Storage.storage.put("banana", 100);
    }

    @Test
    void testOperatePurchase_ok() {
        operationHandler.operate(new FruitTransaction(FruitTransaction.OperationType.PURCHASE,
                "banana", 50));
        Map<String, Integer> expected = Map.of("banana", 50);
        Map<String, Integer> actual = Storage.getStorage();
        assertEquals(expected, actual);
    }

    @Test
    void testOperatePurchase_throwsException() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> operationHandler
                .operate(new FruitTransaction(FruitTransaction.OperationType.PURCHASE,
                "banana", 110)));
        assertEquals("Can't do purchase, because of shortage of banana. You can buy only 100",
                exception.getMessage());
    }
}
