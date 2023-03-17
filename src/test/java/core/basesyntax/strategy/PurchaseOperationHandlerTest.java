package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationHandlerTest {
    private static final int INITIAL_QUANTITY1 = 20;
    private static final int INITIAL_QUANTITY2 = 10;
    private static final String FRUIT_NAME = "apple";
    private OperationHandler purchaseOperationHandler;

    @BeforeEach
    void setUp() {
        purchaseOperationHandler = new PurchaseOperationHandler();
        Storage.storage.clear();
    }

    @Test
    void handle_validTransaction_ok() {
        Storage.storage.put(FRUIT_NAME, INITIAL_QUANTITY1);
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        FRUIT_NAME,
                        INITIAL_QUANTITY2);
        purchaseOperationHandler.handle(fruitTransaction);
        int expectedQuantity = INITIAL_QUANTITY1 - INITIAL_QUANTITY2;
        assertEquals(expectedQuantity, Storage.storage.get(FRUIT_NAME));
    }

    @Test
    void handle_invalidTransaction_notOk() {
        FruitTransaction fruitTransactionWithNullFruit =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        null,
                        INITIAL_QUANTITY2);
        assertThrows(RuntimeException.class, () ->
                purchaseOperationHandler.handle(fruitTransactionWithNullFruit));
        FruitTransaction fruitTransactionWithNullOperation =
                new FruitTransaction(null, FRUIT_NAME, INITIAL_QUANTITY2);
        assertThrows(RuntimeException.class, () ->
                purchaseOperationHandler.handle(fruitTransactionWithNullOperation));
    }

    @Test
    void handle_notEnoughQuantity_notOk() {
        Storage.storage.put(FRUIT_NAME, INITIAL_QUANTITY2);
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        FRUIT_NAME,
                        INITIAL_QUANTITY1);
        assertThrows(RuntimeException.class, () ->
                purchaseOperationHandler.handle(fruitTransaction));
    }
}
