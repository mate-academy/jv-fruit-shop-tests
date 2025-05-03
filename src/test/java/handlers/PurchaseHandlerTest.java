package handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.database.Storage;
import core.basesyntax.handler.OperationHandler;
import core.basesyntax.handler.PurchaseHandler;
import core.basesyntax.transactor.FruitTransaction;
import core.basesyntax.transactor.Operation;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseHandlerTest {
    private OperationHandler operationHandler;

    @BeforeEach
    void setUp() {
        operationHandler = new PurchaseHandler();
        Storage.storage.put("banana", 100);
    }

    @AfterEach
    void clear() {
        Storage.storage.clear();
    }

    @Test
    void operatePurchase_validData_ok() {
        operationHandler.operate(new FruitTransaction(Operation.PURCHASE, "banana", 50));
        Map<String, Integer> expected = Map.of("banana", 50);
        Map<String, Integer> actual = Storage.storage;
        assertEquals(expected, actual);
    }

    @Test
    void operatePurchase_notEnoughBananas_throwsException() {
        int actualBananas = Storage.storage.get("banana");
        int expectedBananas = 100;
        int purchaseBanana = new FruitTransaction(Operation.PURCHASE, "banana", 150).getQuantity();
        RuntimeException runtimeException = assertThrows(RuntimeException.class, ()
                        -> operationHandler.operate(new FruitTransaction(
                        Operation.PURCHASE, "banana", 150)));

        assertEquals(String.format(
                "Can't do purchase, because amount = %d < purchase = %d",
                expectedBananas, purchaseBanana), runtimeException.getMessage());

        assertEquals(expectedBananas, actualBananas);
    }
}
