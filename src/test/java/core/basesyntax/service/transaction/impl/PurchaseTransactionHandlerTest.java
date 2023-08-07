package core.basesyntax.service.transaction.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.FruitShopOperation;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.transaction.TransactionHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseTransactionHandlerTest {
    private TransactionHandler purchaseTransactionHandler;

    @BeforeEach
    void setUp() {
        purchaseTransactionHandler = new PurchaseTransactionHandler();
        Storage.addPair("apple", 60);
        Storage.addPair("banana", 20);
    }

    @AfterEach
    void cleanUp() {
        Storage.clear();
    }

    @Test
    void executePurchaseTransaction_OK() {

        FruitTransaction transaction = new FruitTransaction(
                FruitShopOperation.PURCHASE, "apple", 60);
        Map<String, Integer> expected = new HashMap<>(
                Map.of("apple", 0,"banana", 20));
        purchaseTransactionHandler.executeTransaction(transaction);
        Assertions.assertEquals(expected, Storage.getAll(),
                "Purchase operation doesn't work correctly: ");
        Storage.clear();
    }

    @Test
    void executeReturnTransaction_NotOk() {
        FruitTransaction transaction = new FruitTransaction(
                FruitShopOperation.PURCHASE, "apple", 80);
        InvalidDataException actual = Assertions.assertThrows(InvalidDataException.class, () -> {
            purchaseTransactionHandler.executeTransaction(transaction);
        });
        String expectedMessage = "The amount of purchase is too big! "
                + "Purchase amount is 80, but shop has only 60 apple.";
        Assertions.assertEquals(expectedMessage, actual.getMessage());
        Storage.clear();
    }
}
