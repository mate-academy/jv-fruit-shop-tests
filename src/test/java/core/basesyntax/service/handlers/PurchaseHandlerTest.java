package core.basesyntax.service.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PurchaseHandlerTest {
    private static final FruitTransaction.Operation VALID_OPERATION
            = FruitTransaction.Operation.PURCHASE;
    private static final String VALID_FRUIT = "banana";
    private static final int VALID_QUANTITY = 100;
    private static final int PURCHASE_QUANTITY = 98;
    private static final int MAX_PURCHASE_QUANTITY = 98;
    private static final int UN_VALID_PURCHASE_QUANTITY = 190;
    private static OperationHandler purchaseHandler;

    @BeforeAll
    static void beforeAll() {
        purchaseHandler = new PurchaseHandler();
    }

    @AfterEach
    void afterEach() {
        Storage.storage.clear();
    }

    @Test
    void handle_ValidQuantity_ok() {
        Storage.storage.put(VALID_FRUIT, VALID_QUANTITY);
        FruitTransaction transaction = new FruitTransaction(VALID_OPERATION,
                VALID_FRUIT, PURCHASE_QUANTITY);
        purchaseHandler.handle(transaction);
        int expected = VALID_QUANTITY - PURCHASE_QUANTITY;
        assertEquals(expected, Storage.storage.get(transaction.getFruit()));
    }

    @Test
    void handle_MaxValidQuantity_ok() {
        Storage.storage.put(VALID_FRUIT, VALID_QUANTITY);
        FruitTransaction transaction = new FruitTransaction(VALID_OPERATION,
                VALID_FRUIT, MAX_PURCHASE_QUANTITY);
        purchaseHandler.handle(transaction);
        int expected = VALID_QUANTITY - MAX_PURCHASE_QUANTITY;
        assertEquals(expected, Storage.storage.get(transaction.getFruit()));
    }

    @Test
    void handle_NotValidPurchase_notOk() {
        Storage.storage.put(VALID_FRUIT, VALID_QUANTITY);
        FruitTransaction transaction = new FruitTransaction(VALID_OPERATION,
                VALID_FRUIT, UN_VALID_PURCHASE_QUANTITY);
        assertThrows(IllegalArgumentException.class, ()
                -> purchaseHandler.handle(transaction));
    }
}
