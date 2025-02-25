package handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.database.Storage;
import core.basesyntax.handler.OperationHandler;
import core.basesyntax.handler.PurchaseHandler;
import core.basesyntax.transactor.FruitTransaction;
import core.basesyntax.transactor.Operation;
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
    void operatePurchase_ok() {
        operationHandler.operate(new FruitTransaction(Operation.PURCHASE, "banana", 50));
        Map<String, Integer> expected = Map.of("banana", 50);
        Map<String, Integer> actual = Storage.storage;
        assertEquals(expected, actual);
    }
}
