package core.basesyntax.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationTest {
    private static final int INITIAL_QUANTITY = 100;
    private static final int PURCHASE_QUANTITY = 30;
    private static final String TEST_FRUIT = "banana";
    private static final FruitTransaction BIG_PURCHASE =
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 101);
    private static final FruitTransaction TRANSACTION_EXAMPLE =
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, TEST_FRUIT, 30);
    private OperationHandler purchaseOperation;

    @BeforeEach
    void setUp() {
        purchaseOperation = new PurchaseOperation();
        Storage.put(TEST_FRUIT, INITIAL_QUANTITY);
    }

    @Test
    void handle_validTransaction_setsPurchase() {
        purchaseOperation.handle(TRANSACTION_EXAMPLE);
        int expectedQuantity = INITIAL_QUANTITY - PURCHASE_QUANTITY;
        assertEquals(expectedQuantity, Storage.getQuantity(TEST_FRUIT),
                "The quantity of the fruit in storage doesn't match the expected value.");
    }

    @Test
    void handle_notEnoughFruit_throwsException() {
        assertThrows(IllegalStateException.class, () -> purchaseOperation.handle(BIG_PURCHASE),
                "Expected IllegalStateException when trying"
                        + "to purchase more fruit than available.");
    }
}
