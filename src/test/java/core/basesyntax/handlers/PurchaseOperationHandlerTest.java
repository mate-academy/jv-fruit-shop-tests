package core.basesyntax.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.storage.Storage;
import core.basesyntax.transaction.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private static final String APPLE = "apple";
    private static final int INITIAL_FRUIT_QUANTITY = 100;
    private static final int PURCHASE_QUANTITY = 50;
    private static final String BANANA = "banana";
    private static final int INVALID_FRUIT_QUANTITY = -10;
    private static FruitTransaction validFruitTransaction;
    private static FruitTransaction invalidFruitTransaction;
    private static PurchaseOperationHandler purchaseOperationHandler;

    @BeforeAll
    static void beforeAll() {
        purchaseOperationHandler = new PurchaseOperationHandler();

        validFruitTransaction = new FruitTransaction();
        validFruitTransaction.setFruit(APPLE);
        validFruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        validFruitTransaction.setQuantity(PURCHASE_QUANTITY);

        invalidFruitTransaction = new FruitTransaction();
        invalidFruitTransaction.setFruit(BANANA);
        invalidFruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        invalidFruitTransaction.setQuantity(INVALID_FRUIT_QUANTITY);

        Storage.getFruitBalance().put(APPLE, INITIAL_FRUIT_QUANTITY);
        Storage.getFruitBalance().put(BANANA, INITIAL_FRUIT_QUANTITY);
    }

    @AfterEach
    void afterEach() {
        Storage.getFruitBalance().clear();
    }

    @Test
    void handle_Valid_Transaction_Ok() {
        Storage.getFruitBalance().put(APPLE, INITIAL_FRUIT_QUANTITY);
        purchaseOperationHandler.handleOperation(validFruitTransaction);
        assertEquals(INITIAL_FRUIT_QUANTITY - PURCHASE_QUANTITY,
                Storage.getFruitBalance().get(APPLE));
    }

    @Test
    void handle_Invalid_Transaction_NotOk() {
        assertThrows(RuntimeException.class,
                () -> purchaseOperationHandler.handleOperation(invalidFruitTransaction));
    }
}
