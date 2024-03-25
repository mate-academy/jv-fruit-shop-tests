package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.Operation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private final OperationHandler purchase = new PurchaseOperationHandler();
    private final FruitTransaction testTransaction = new FruitTransaction(
            Operation.PURCHASE, "banana", 100);
    private final FruitTransaction wrongTransaction = new FruitTransaction(
            Operation.PURCHASE, "banana", 183);

    @BeforeEach
    void initialize() {
        Storage.dataBase.put("banana", 100);
    }

    @Test
    void purchaseOperationHandler_WrongValue_ThrowsException() {
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                () -> purchase.apply(wrongTransaction));
        Assertions.assertEquals("Wrong transaction value: 183 is bigger than balance: 100",
                exception.getMessage());
    }

    @Test
    void purchaseOperationHandler_OK_True() {
        purchase.apply(testTransaction);
        Assertions.assertTrue(Storage.dataBase.containsKey("banana"));
        Assertions.assertTrue(Storage.dataBase.containsValue(0));
    }

    @Test
    void purchaseOperationHandler_IsApplicableOK_True() {
        Assertions.assertTrue(purchase.isAplicable(testTransaction));
    }
}
