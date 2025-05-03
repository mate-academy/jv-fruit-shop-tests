package core.basesyntax.service.operationhandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.ShopDataBase;
import core.basesyntax.service.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private OperationHandler purchaseHandler;

    @BeforeEach
    void setUp() {
        purchaseHandler = new PurchaseOperation();
    }

    @Test
    void testPurchaseReducesQuantity_Ok() {
        ShopDataBase.shopData.put("banana", 100);
        FruitTransaction transaction = new FruitTransaction("p", "banana", 30);
        purchaseHandler.process(transaction);
        assertEquals(70, ShopDataBase.shopData.get("banana"));
    }

    @Test
    void testPurchaseFailsWhenNotEnoughStock_NotOk() {
        ShopDataBase.shopData.put("apple", 10);
        FruitTransaction transaction = new FruitTransaction("p", "apple", 20);
        assertThrows(RuntimeException.class, () ->
                purchaseHandler.process(transaction));
    }

    @Test
    void testPurchaseNonExistentFruit_NotOk() {
        FruitTransaction transaction = new FruitTransaction("p", "peach", 5);
        assertThrows(IllegalArgumentException.class, () ->
                purchaseHandler.process(transaction));
    }

    @AfterEach
    void tearDown() {
        ShopDataBase.shopData.clear();
    }

}
