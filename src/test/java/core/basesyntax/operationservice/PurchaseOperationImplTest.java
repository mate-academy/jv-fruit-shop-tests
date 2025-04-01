package core.basesyntax.operationservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

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
    void apply_insufficientStock_throwsException() {
        Storage.putFruit("apple", 5);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 10);
        assertThrows(RuntimeException.class, () -> purchaseOperation.apply(transaction));
    }
}
