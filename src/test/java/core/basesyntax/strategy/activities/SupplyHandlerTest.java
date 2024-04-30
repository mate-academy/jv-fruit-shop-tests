package core.basesyntax.strategy.activities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.db.StorageImpl;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyHandlerTest {
    private static final String APPLE_FRUIT = "apple";
    private static final int APPLE_VALID_AMOUNT = 30;
    private OperationHandler supplyHandler;
    private Storage storage;

    @BeforeEach
    void setUp() {
        supplyHandler = new SupplyHandler();
        storage = new StorageImpl();
    }

    @Test
    void executeTransaction_OperationTest_Ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                APPLE_FRUIT,
                APPLE_VALID_AMOUNT
        );
        supplyHandler.executeTransaction(transaction);

        assertEquals(APPLE_VALID_AMOUNT, storage.getValue(APPLE_FRUIT));
    }

    @Test
    public void executeTransaction_withNullValue() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                APPLE_FRUIT,
                APPLE_VALID_AMOUNT
        );
        storage.setValue(APPLE_FRUIT, null);
        supplyHandler.executeTransaction(transaction);

        assertEquals(30, storage.getValue(APPLE_FRUIT));
    }

    @AfterEach
    void afterEach() {
        new StorageImpl().clear();
    }
}
