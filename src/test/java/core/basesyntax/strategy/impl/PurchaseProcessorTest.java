package core.basesyntax.strategy.impl;

import static core.basesyntax.model.Product.APPLE;
import static core.basesyntax.model.Product.BANANA;
import static core.basesyntax.strategy.FruitTransaction.Operation.PURCHASE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.ProductDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exeptions.InvalidTransaction;
import core.basesyntax.strategy.FruitTransaction;
import core.basesyntax.strategy.OperationProcessor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("PurchaseProcessor Test")
class PurchaseProcessorTest {
    private final OperationProcessor purchaseProcessor =
            new PurchaseProcessor(new ProductDaoImpl());

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }

    @DisplayName("Check purchase operation with valid value (Apple)")
    @Test
    void operate_checkPurchaseAppleEmptyStorage_ok() {
        Storage.storage.put(APPLE, 15);
        purchaseProcessor.operate(new FruitTransaction(PURCHASE, APPLE, 10));
        assertEquals(Storage.storage.get(APPLE), 5);
        purchaseProcessor.operate(new FruitTransaction(PURCHASE, APPLE, 5));
        assertEquals(Storage.storage.get(APPLE), 0);
    }

    @DisplayName("Check purchase operation with valid value (Apple) and empty Storage")
    @Test
    void operate_checkPurchaseApple_NotOk() {
        assertThrows(RuntimeException.class,
                () -> purchaseProcessor.operate(new FruitTransaction(PURCHASE, APPLE, 20)));
    }

    @DisplayName("Check purchase operation with valid value (Banana)")
    @Test
    void operate_checkPurchaseBananaEmptyStorage_ok() {
        Storage.storage.put(BANANA, 147);
        purchaseProcessor.operate(new FruitTransaction(PURCHASE, BANANA, 130));
        assertEquals(Storage.storage.get(BANANA), 17);
        purchaseProcessor.operate(new FruitTransaction(PURCHASE, BANANA, 17));
        assertEquals(Storage.storage.get(BANANA), 0);
    }

    @DisplayName("Check purchase operation with valid value (Banana) and empty Storage")
    @Test
    void operate_checkPurchaseBananaEmpty_notOk() {
        assertThrows(InvalidTransaction.class,
                () -> purchaseProcessor.operate(new FruitTransaction(PURCHASE, BANANA, 128)));
    }

    @DisplayName("Check purchase operation with invalid value (Apple) and non empty Storage")
    @Test
    void operate_checkPurchaseBananaIncorrect_notOk() {
        Storage.storage.put(BANANA, 147);
        assertThrows(InvalidTransaction.class,
                () -> purchaseProcessor.operate(new FruitTransaction(PURCHASE, BANANA, -15)));
    }

    @DisplayName("Check purchase operation with invalid value (Apple) and non empty Storage")
    @Test
    void operate_checkPurchaseApple_notOk() {
        Storage.storage.put(BANANA, 147);
        assertThrows(InvalidTransaction.class,
                () -> purchaseProcessor.operate(new FruitTransaction(PURCHASE, APPLE, -18)));
    }
}
