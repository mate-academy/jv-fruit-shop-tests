package core.basesyntax.service.transaction.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.transaction.TransactionHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseTransactionHandlerTest {
    private TransactionHandler purchaseTransactionHandler;

    @BeforeEach
    void setUp() {
        purchaseTransactionHandler = new PurchaseTransactionHandler();
        Storage.addPair(Fruit.PINEAPPLE, 60);
        Storage.addPair(Fruit.STRAWBERRY, 20);
    }

    @Test
    void executePurchaseTransaction_OK() {
        FruitTransaction transaction = new FruitTransaction(
                Operation.PURCHASE, Fruit.PINEAPPLE, 60);
        Map<Fruit, Integer> expected = new HashMap<>(
                Map.of(Fruit.PINEAPPLE, 0,Fruit.STRAWBERRY, 20));
        purchaseTransactionHandler.executeTransaction(transaction);
        Assertions.assertEquals(expected, Storage.getAll(),
                "Purchase operation doesn't work correctly: ");
        Storage.clear();
    }

    @Test
    void executeReturnTransaction_NotOk() {
        FruitTransaction transaction = new FruitTransaction(
                Operation.PURCHASE, Fruit.PINEAPPLE, 80);
        InvalidDataException actual = Assertions.assertThrows(InvalidDataException.class, () -> {
            purchaseTransactionHandler.executeTransaction(transaction);
        });
        String expectedMessage = "The amount of purchase is too big! "
                + "Purchase amount is 80, but shop has only 60 pineapple";
        Assertions.assertEquals(expectedMessage, actual.getMessage());
        Storage.clear();
    }
}
