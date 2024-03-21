package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.Operation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private final OperationHandler purchase = new PurchaseOperationHandler();
    private final FruitTransaction testTransaction = new FruitTransaction(
            Operation.PURCHASE, "banana", 100);
    private final FruitTransaction wrongTransaction = new FruitTransaction(
            Operation.PURCHASE, "banana", 183);

    @BeforeAll
    static void initAll() {
        Storage.dataBase.put("banana", 100);
    }

    @Test
    void purchaseOperationHandler_WrongValue_ThrowsException() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> purchase.apply(wrongTransaction));
        assertEquals("Wrong transaction value: 183 is bigger than balance: 100",
                exception.getMessage());
    }

    @Test
    void purchaseOperationHandler_OK_True() {
        purchase.apply(testTransaction);
        assertTrue(Storage.dataBase.containsKey("banana"));
        assertTrue(Storage.dataBase.containsValue(0));
    }

    @Test
    void purchaseOperationHandler_IsApplicableOK_True() {
        assertTrue(purchase.isAplicable(testTransaction));
    }
}
