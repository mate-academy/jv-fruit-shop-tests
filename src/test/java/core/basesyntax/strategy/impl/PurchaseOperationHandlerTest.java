package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private FruitTransaction fruitTransaction;
    private PurchaseOperationHandler purchaseOperationHandler = new PurchaseOperationHandler();

    @BeforeEach
    void setUp() {
//        fruitTransaction = new FruitTransaction();
//        fruitTransaction.setFruit(BANANA);
//        fruitTransaction.setQuantity(10);
//        Storage.SHOPSTORAGE.put(fruitTransaction.getFruit(), fruitTransaction.getQuantity());
        Storage.SHOPSTORAGE.clear();
    }

    @AfterEach
    void tearDown() {
        Storage.SHOPSTORAGE.clear();
    }

    @Test
    void handle_PurchaseBanana_ok() {
        Storage.SHOPSTORAGE.put(BANANA, 10);
        FruitTransaction fruitPurchase = new FruitTransaction();
        fruitPurchase.setFruit(BANANA);
        fruitPurchase.setQuantity(5);
        purchaseOperationHandler.handle(fruitPurchase);
        assertEquals(5, Storage.SHOPSTORAGE.get(BANANA));
    }

    @Test
    void handle_notFruitInStorage_notOk() {
        FruitTransaction noSuchFruit = new FruitTransaction();
        noSuchFruit.setFruit(APPLE);
        noSuchFruit.setQuantity(5);
        assertThrows(RuntimeException.class,
                () -> purchaseOperationHandler.handle(noSuchFruit),
                "Your do not have such fruit");
    }

    @Test
    void handle_purchaseFruitTransactionNull_notOk() {
        assertThrows(NullPointerException.class,
                () -> purchaseOperationHandler.handle(null),
                "Your FruitTransaction is Null");
    }

    @Test
    void handle_purchaseZeroQuantity_notOk() {
        Storage.SHOPSTORAGE.put(BANANA, 10);
        FruitTransaction zeroQuantityPurchase = new FruitTransaction();
        zeroQuantityPurchase.setFruit(BANANA);
        zeroQuantityPurchase.setQuantity(0);
        purchaseOperationHandler.handle(zeroQuantityPurchase);
        assertEquals(10, Storage.SHOPSTORAGE.get(BANANA));
    }

    @Test
    void handle_purchaseWithEdgeCase_ok() {
        Storage.SHOPSTORAGE.put(BANANA, 10);
        FruitTransaction purchaseFruit = new FruitTransaction();
        purchaseFruit.setFruit(BANANA);
        purchaseFruit.setQuantity(10);
        purchaseOperationHandler.handle(purchaseFruit);
        assertEquals(0, Storage.SHOPSTORAGE.get(BANANA));
    }
}
