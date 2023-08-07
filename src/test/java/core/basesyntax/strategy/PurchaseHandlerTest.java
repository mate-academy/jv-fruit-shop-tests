package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandlerService;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseHandlerTest {
    private OperationHandlerService operationHandlerService;
    
    @BeforeEach
    void setUp() {
        operationHandlerService = new PurchaseHandler();
        Storage.fruitsStorage.clear();
    }

    @Test
    void purchaseOperationNotEnoughAmount_NotOk() {
        Storage.fruitsStorage.put("banana", 5);
        FruitTransaction invalidTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 6);
        assertThrows(RuntimeException.class,
                () -> operationHandlerService.handle(invalidTransaction));
    }

    @Test
    void purchaseOperationValidDate_Ok() {
        Map<String, Integer> expected = Map.of("banana", 10);
        Storage.fruitsStorage.put("banana", 20);
        operationHandlerService.handle(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 10));
        Map<String,Integer> actual = Storage.fruitsStorage;
        assertEquals(expected, actual);
    }
}
