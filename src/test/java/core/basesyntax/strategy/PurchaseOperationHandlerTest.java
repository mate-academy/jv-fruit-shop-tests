package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.PurchaseBeyondStockException;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private OperationHandler purchaseOperationHandler;
    private String fruit;
    private int balance;

    @BeforeEach
    void setUp() {
        purchaseOperationHandler = new PurchaseOperationHandler();
        fruit = "apple";
        balance = 100;
        Storage.getFruits().put(fruit, balance);
    }

    @Test
    void applyHandler_ValidData_Ok() {
        int purchase = 75;
        purchaseOperationHandler.apply(fruit, purchase);
        assertEquals(25, Storage.getFruits().get(fruit));
    }

    @Test
    void applyHandler_PurchaseBiggerThanBalance_NotOk() {
        int purchase = 125;
        PurchaseBeyondStockException exception = assertThrows(PurchaseBeyondStockException.class,
                () -> purchaseOperationHandler.apply(fruit, purchase));
        assertEquals("Purchase quantity is bigger than stock. "
                + "Purchase q-ty: " + purchase
                + " Stock q-ty: " + Storage.getFruits().get(fruit), exception.getMessage());
    }

    @Test
    void applyHandler_PurchaseEqualsBalance_Ok() {
        purchaseOperationHandler.apply(fruit, balance);
        assertEquals(0, Storage.getFruits().get(fruit));
    }

    @Test
    void applyHandler_ZeroPurchase_Ok() {
        purchaseOperationHandler.apply(fruit, 0);
        assertEquals(balance, Storage.getFruits().get(fruit));
    }

    @AfterEach
    void clearStorage() {
        Storage.getFruits().clear();
    }
}
