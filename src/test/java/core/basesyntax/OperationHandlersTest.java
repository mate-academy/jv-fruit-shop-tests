package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntex.model.FruitTransaction;
import core.basesyntex.model.Operation;
import core.basesyntex.service.impl.BalanceOperationHandler;
import core.basesyntex.service.impl.PurchaseOperationHandler;
import core.basesyntex.service.impl.ReturnOperationHandler;
import core.basesyntex.service.impl.Storage;
import core.basesyntex.service.impl.SupplyOperationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationHandlersTest {
    private BalanceOperationHandler balanceHandler;
    private PurchaseOperationHandler purchaseHandler;
    private ReturnOperationHandler returnHandler;
    private SupplyOperationHandler supplyHandler;

    @BeforeEach
    void setUp() {
        balanceHandler = new BalanceOperationHandler();
        purchaseHandler = new PurchaseOperationHandler();
        returnHandler = new ReturnOperationHandler();
        supplyHandler = new SupplyOperationHandler();
        Storage.clearStorage();
    }

    @Test
    void balanceHandler_addToBalance_ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.BALANCE, "apple", 50);
        balanceHandler.handle(transaction);
        assertEquals(50, Storage.getQuantity("apple"));
    }

    @Test
    void purchaseHandler_reduceBalance_ok() {
        Storage.updateStorage("banana", 100);
        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE, "banana", -30);
        purchaseHandler.handle(transaction);
        assertEquals(70, Storage.getQuantity("banana"));
    }

    @Test
    void purchaseHandler_negativeBalance_notOk() {
        Storage.updateStorage("banana", 100);
        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE, "banana", -150);
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> purchaseHandler.handle(transaction));
        assertTrue(exception.getMessage().contains("Negative balance"));
    }

    @Test
    void returnHandler_returnFruit_ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.RETURN, "orange", 30);
        returnHandler.handle(transaction);
        assertEquals(30, Storage.getQuantity("orange"));
    }

    @Test
    void supplyHandler_supplyFruit_ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.SUPPLY, "grape", 100);
        supplyHandler.handle(transaction);
        assertEquals(100, Storage.getQuantity("grape"));
    }
}
