package core.basesyntax.operationservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationImplTest {
    private PurchaseOperationImpl purchaseOperation;

    @BeforeEach
    void setUp() {
        purchaseOperation = new PurchaseOperationImpl();
        Storage.clearStorage();
    }

    @Test
    void apply_validTransaction_decreasesQuantity() {
        Storage.putFruit("apple", 30);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 10);
        purchaseOperation.apply(transaction);
        assertEquals(20, Storage.getQuantity("apple"));
    }

    @Test
    void apply_exactAmount_removesFruit() {
        Storage.putFruit("apple", 10);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 10);
        purchaseOperation.apply(transaction);
        assertEquals(0, Storage.getQuantity("apple"));
    }

    @Test
    void apply_insufficientStock_throwsException() {
        Storage.putFruit("apple", 5);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 10);
        Exception exception = assertThrows(RuntimeException.class, () ->
                purchaseOperation.apply(transaction));
        assertTrue(exception.getMessage().contains("Not enough apple in storage"));
    }

    @Test
    void apply_noStock_throwsException() {
        Storage.putFruit("apple", 0);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 1);
        Exception exception = assertThrows(RuntimeException.class, () ->
                purchaseOperation.apply(transaction));
        assertTrue(exception.getMessage().contains("Not enough apple in storage"));
    }
}
