package mate.fruitshop.strategy;

import static mate.fruitshop.TransactionHandlerTest.DEFAULT_FRUIT;
import static mate.fruitshop.TransactionHandlerTest.DEFAULT_QUANTITY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import mate.fruitshop.model.FruitTransaction;
import mate.fruitshop.service.strategy.FruitPurchaseHandler;
import mate.fruitshop.service.strategy.FruitTransactionHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class PurchaseTransactionHandlerTest {
    private static FruitTransactionHandler purchaseHandler;
    private static FruitTransaction purchaseTransaction;

    @BeforeAll
    static void beforeAll() {
        purchaseHandler = new FruitPurchaseHandler();
        purchaseTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, DEFAULT_FRUIT, DEFAULT_QUANTITY);
    }

    @Test
    void conductTransaction_purchaseTransaction_ok() {
        assertEquals(0, purchaseHandler.conductTransaction(purchaseTransaction, DEFAULT_QUANTITY));
    }

    @Test
    void conductTransaction_purchaseTransactionNotEnoughFruits_notOk() {
        assertThrows(IllegalArgumentException.class, () ->
                purchaseHandler.conductTransaction(purchaseTransaction, DEFAULT_QUANTITY - 1));
    }
}
