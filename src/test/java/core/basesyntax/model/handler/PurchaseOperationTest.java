package core.basesyntax.model.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private static PurchaseOperation purchaseOperation;

    @BeforeAll
    static void beforeAll() {
        purchaseOperation = new PurchaseOperation();
    }

    @AfterEach
    void afterEach() {
        Storage.getStorage().clear();
    }

    @Test
    void purchaseMaxIntMoreThanHave_NotOk() {
        assertThrows(RuntimeException.class, () -> purchaseOperation.handle(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        "kiwi", Integer.MAX_VALUE)));
    }

    @Test
    void purchase_Ok() {
        Storage.getStorage().put("banana", 100);
        purchaseOperation.handle(new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "banana", 33));
        assertEquals(Integer.valueOf(67), Storage.getStorage().get("banana"));
    }

    @Test
    void purchase_negativeQuantity_NotOk() {
        Storage.getStorage().put("banana", 10);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", -7);
        assertThrows(IllegalArgumentException.class,
                () -> purchaseOperation.handle(fruitTransaction));
        assertEquals(Integer.valueOf(10), Storage.getStorage().get("banana"));
    }

    @Test
    public void handleFruitOperation_purchase_illegalQuantity() {
        Storage.getStorage().put("banana", 25);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 0);
        assertThrows(IllegalArgumentException.class,
                () -> purchaseOperation.handle(fruitTransaction));
        assertEquals(Integer.valueOf(25), Storage.getStorage().get("banana"));
    }
}
