package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationTest {
    private static final String FRUIT_KEY = "banana";
    private static final String WRONG_KEY = "apple";

    private Storage storage;
    private OperationHandler purchaseOperation;

    @BeforeEach
    void setUp() {
        int startQuantity = 50;
        storage = new Storage();
        storage.set(FRUIT_KEY, startQuantity);
        purchaseOperation = new PurchaseOperation();
    }

    @Test
    void handle_purchaseOperation_validItemAndQuantity_Ok() {
        int quantityToPurchase = 30;
        int expectedQuantity = 20;
        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE,
                FRUIT_KEY, quantityToPurchase);
        purchaseOperation.handle(transaction, storage);
        Assertions.assertEquals(expectedQuantity, storage.getInventory().get(FRUIT_KEY));
    }

    @Test
    void handle_purchaseOperation_notExistingItem_NotOk() {
        int purchaseQuantity = 40;
        FruitTransaction wrongTransaction = new FruitTransaction(Operation.PURCHASE,
                WRONG_KEY, purchaseQuantity);
        Exception exception = Assertions.assertThrows(Exception.class,
                () -> purchaseOperation.handle(wrongTransaction, storage));
        String actualMessage = exception.getMessage();
        String expectedMessage = "Fruit " + WRONG_KEY
                + " not found in inventory";
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void handle_purchaseOperation_insufficientQuantity_NotOk() {
        int wrongPurchaseQuantity = 60;
        FruitTransaction wrongTransaction = new FruitTransaction(Operation.PURCHASE,
                FRUIT_KEY, wrongPurchaseQuantity);
        Exception exception = Assertions.assertThrows(Exception.class,
                () -> purchaseOperation.handle(wrongTransaction, storage));
        String actualMessage = exception.getMessage();
        String expectedMessage = "Insufficient quantity of " + FRUIT_KEY
                + " in inventory. " + "Current quantity: "
                + storage.getInventory().get(FRUIT_KEY);
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void handle_purchaseOperation_negativeQuantity_NotOk() {
        int negativeQuantity = -10;
        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE,
                FRUIT_KEY, negativeQuantity);
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> purchaseOperation.handle(transaction, storage));
        String expectedMessage = "Quantity to remove must be greater than 0";
        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }
}
