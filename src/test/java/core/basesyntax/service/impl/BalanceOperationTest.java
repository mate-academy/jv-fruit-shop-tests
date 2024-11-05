package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.FruitStorage;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final String ORANGE = "orange";
    private static final String GRAPE = "grape";
    private static final int APPLE_QUANTITY = 50;
    private static final int BANANA_QUANTITY = 100;
    private static final int INITIAL_BANANA_QUANTITY = 20;
    private static final int ORANGE_INITIAL_QUANTITY = 30;
    private static final int ZERO_QUANTITY = 0;
    private static final int NEGATIVE_GRAPE_QUANTITY = -15;
    private FruitStorage storage;
    private BalanceOperation balanceOperation;

    @BeforeEach
    public void setUp() {
        storage = new Storage();
        balanceOperation = new BalanceOperation(storage);
    }

    @Test
    public void handle_UpdateFruitQuantity_WhenFruitDoesNotExist() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit(APPLE);
        transaction.setQuantity(APPLE_QUANTITY);
        balanceOperation.handle(transaction);
        assertEquals(APPLE_QUANTITY, storage.getFruitQuantity(APPLE));
    }

    @Test
    public void handle_UpdateFruitQuantity_WhenFruitAlreadyExists() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit(BANANA);
        transaction.setQuantity(BANANA_QUANTITY);
        storage.addFruit(BANANA, INITIAL_BANANA_QUANTITY);
        balanceOperation.handle(transaction);
        assertEquals(BANANA_QUANTITY, storage.getFruitQuantity(BANANA));
    }

    @Test
    public void handle_UpdateFruitQuantity_ToZeroQuantity() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit(ORANGE);
        transaction.setQuantity(ZERO_QUANTITY);
        storage.addFruit(ORANGE, ORANGE_INITIAL_QUANTITY);
        balanceOperation.handle(transaction);
        assertEquals(ZERO_QUANTITY, storage.getFruitQuantity(ORANGE));
    }

    @Test
    public void handle_UpdateFruitQuantity_WithNegativeQuantity() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit(GRAPE);
        transaction.setQuantity(NEGATIVE_GRAPE_QUANTITY);
        assertThrows(RuntimeException.class, () -> balanceOperation.handle(transaction),
                "Error: Handling transaction with a "
                        + "negative quantity should throw RuntimeException.");
    }

}
