package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static final String NO_SUCH_FRUIT = "Your do not have such fruit";
    private static PurchaseOperationHandler purchaseOperationHandler;

    @BeforeAll
    static void beforeAll() {
        purchaseOperationHandler = new PurchaseOperationHandler();
    }

    @BeforeEach
    void setUp() {
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
    void handle_fruitNotInStorage_notOk() {
        FruitTransaction noSuchFruit = new FruitTransaction();
        noSuchFruit.setFruit(APPLE);
        noSuchFruit.setQuantity(5);
        assertThrows(RuntimeException.class,
                () -> purchaseOperationHandler.handle(noSuchFruit),
                NO_SUCH_FRUIT);
    }

    @Test
    void handle_purchaseZeroQuantity_ok() {
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
