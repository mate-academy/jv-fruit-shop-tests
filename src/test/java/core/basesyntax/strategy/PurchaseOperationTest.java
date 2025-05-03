package core.basesyntax.strategy;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationTest {
    private OperationHandler purchaseOperation;

    @BeforeEach
    public void setUp() {
        purchaseOperation = new PurchaseOperation();
        Storage.setFruitQuantity("apple", 20);
    }

    @Test
    void test_apply_successfulPurchase_quantityDecreased() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 5);
        purchaseOperation.apply(fruitTransaction);

        assertEquals(15, Storage.getFruitQuantity("apple"));
    }

    @Test
    void test_apply_notEnoughFruitsInStorage_throwsException() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 25);

        assertThrows(RuntimeException.class, () -> purchaseOperation.apply(fruitTransaction));
    }
}
