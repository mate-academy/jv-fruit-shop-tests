package core.basesyntax.strategy.activities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.db.StorageImpl;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseHandlerTest {
    private static final String APPLE_FRUIT = "apple";
    private static final int APPLE_NEGATIVE_AMOUNT = -80;
    private static final int APPLE_VALID_AMOUNT = 30;
    private Storage storage;

    @BeforeEach
    void setUp() {
        storage = new StorageImpl();
    }

    @Test
    public void executeTransaction_BalanceNegative_NotOk() {
        FruitTransaction transaction1 = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                APPLE_FRUIT,
                APPLE_NEGATIVE_AMOUNT
        );
        assertThrows(RuntimeException.class, () ->
                new PurchaseHandler().executeTransaction(transaction1));
    }

    @Test
    void executeTransaction_OperationTest_Ok() {
        FruitTransaction transaction2 = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                APPLE_FRUIT,
                APPLE_VALID_AMOUNT
        );
        storage.setValue("apple", 60);
        OperationHandler purchaseHandler = new PurchaseHandler();
        purchaseHandler.executeTransaction(transaction2);

        assertEquals(APPLE_VALID_AMOUNT, storage.getValue(APPLE_FRUIT));
    }

    @AfterEach
    void afterEach() {
        storage.clear();
    }
}
