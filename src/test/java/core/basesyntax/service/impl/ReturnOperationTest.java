package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.FruitStorage;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final String ORANGE = "orange";
    private static final int INITIAL_BANANA_QUANTITY = 15;
    private static final int INITIAL_ORANGE_QUANTITY = 10;
    private static final int RETURN_QUANTITY_APPLE = 10;
    private static final int RETURN_QUANTITY_BANANA = 5;
    private static final int ZERO_QUANTITY = 0;
    private static final int EXPECTED_APPLE_QUANTITY = 10;
    private static final int EXPECTED_BANANA_QUANTITY = 20;
    private static final int EXPECTED_ORANGE_QUANTITY = 10;
    private FruitStorage storage;
    private ReturnOperation returnOperation;

    @BeforeEach
    public void setUp() {
        storage = new Storage();
        returnOperation = new ReturnOperation(storage);
    }

    @Test
    public void handle_AddFruitToEmptyStorage() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit(APPLE);
        transaction.setQuantity(RETURN_QUANTITY_APPLE);
        returnOperation.handle(transaction);
        assertEquals(EXPECTED_APPLE_QUANTITY, storage.getFruitQuantity(APPLE));
    }

    @Test
    public void handle_IncreaseFruitQuantity_WhenFruitAlreadyExists() {
        storage.updateFruitQuantity(BANANA, INITIAL_BANANA_QUANTITY);
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit(BANANA);
        transaction.setQuantity(RETURN_QUANTITY_BANANA);
        new ReturnOperation(storage).handle(transaction);
        assertEquals(EXPECTED_BANANA_QUANTITY, storage.getFruitQuantity("banana"));
    }

    @Test
    public void handle_DoNothing_WhenQuantityIsZero() {
        storage.updateFruitQuantity(ORANGE, INITIAL_ORANGE_QUANTITY);
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit(ORANGE);
        transaction.setQuantity(ZERO_QUANTITY);
        returnOperation.handle(transaction);

        assertEquals(EXPECTED_ORANGE_QUANTITY, storage.getFruitQuantity("orange"));
    }

}
