package core.basesyntax.service.handles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PurchaseHandlerTest {
    private static OperationHandler purchaseHandler;
    private static FruitTransaction.Operation VALID_OPERATION = FruitTransaction.Operation.PURCHASE;
    private static int VALID_QUANTITY = 100;
    private static int MAX_PURCHASE_QUANTITY = 98;
    private static int PURCHASE_QUANTITY = 98;
    private static int UN_VALID_PURCHASE_QUANTITY = 190;
    private static String VALID_FRUIT = "banana";

    @BeforeAll
    static void beforeAll() {
        purchaseHandler = new PurchaseHandler();
    }

    @AfterEach
    void afterEach() {
        Storage.storage.clear();
    }

    @Test
    void purchaseHandler_validQuantity_ok() {
        Storage.storage.put(VALID_FRUIT, VALID_QUANTITY);
        FruitTransaction transaction = new FruitTransaction(VALID_OPERATION,
                VALID_FRUIT, PURCHASE_QUANTITY);
        purchaseHandler.handler(transaction);
        int expected = VALID_QUANTITY - PURCHASE_QUANTITY;
        assertEquals(expected, Storage.storage.get(transaction.getFruit()));
    }

    @Test
    void purchaseHandler_maxValidQuantity_ok() {
        Storage.storage.put(VALID_FRUIT, VALID_QUANTITY);
        FruitTransaction transaction = new FruitTransaction(VALID_OPERATION,
                VALID_FRUIT, MAX_PURCHASE_QUANTITY);
        purchaseHandler.handler(transaction);
        int expected = VALID_QUANTITY - MAX_PURCHASE_QUANTITY;
        assertEquals(expected,Storage.storage.get(transaction.getFruit()));
    }

    @Test
    void purchaseHandler_NotvalidQuantity_notOk() {
        Storage.storage.put(VALID_FRUIT, VALID_QUANTITY);
        FruitTransaction transaction = new FruitTransaction(VALID_OPERATION,
                VALID_FRUIT, UN_VALID_PURCHASE_QUANTITY);
        assertThrows(IllegalArgumentException.class, () -> purchaseHandler.handler(transaction));
    }
}
