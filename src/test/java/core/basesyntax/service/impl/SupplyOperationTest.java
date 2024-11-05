package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.FruitStorage;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private static final String FRUIT_APPLE = "apple";
    private static final String FRUIT_BANANA = "banana";
    private static final String FRUIT_ORANGE = "orange";

    private static final int QUANTITY_POSITIVE_10 = 10;
    private static final int QUANTITY_ZERO = 0;
    private static final int QUANTITY_NEGATIVE_5 = -5;
    private static final int QUANTITY_POSITIVE_5 = 5;
    private static final int QUANTITY_POSITIVE_3 = 3;
    private static final int EXPECTED_QUANTITY_10 = 10;
    private static final int EXPECTED_QUANTITY_8 = 8;
    private static final int EXPECTED_QUANTITY_5 = 5;
    private static final int EXPECTED_QUANTITY_10_ORANGE = 10;
    private FruitStorage storage;
    private SupplyOperation supplyOperation;

    @BeforeEach
    public void setUp() {
        storage = new Storage();
        supplyOperation = new SupplyOperation(storage);
    }

    @Test
    public void handle_AddFruitToStorage_WhenQuantityIsPositive() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit(FRUIT_APPLE);
        transaction.setQuantity(QUANTITY_POSITIVE_10);
        supplyOperation.handle(transaction);
        assertEquals(EXPECTED_QUANTITY_10, storage.getFruitQuantity(FRUIT_APPLE));
    }

    @Test
    public void handle_NotAddFruitToStorage_WhenQuantityIsZero() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit(FRUIT_BANANA);
        transaction.setQuantity(QUANTITY_ZERO);
        supplyOperation.handle(transaction);
        assertEquals(QUANTITY_ZERO, storage.getFruitQuantity(FRUIT_BANANA));
    }

    @Test
    public void handle_NotAddFruitToStorage_WhenQuantityIsNegative() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit(FRUIT_ORANGE);
        transaction.setQuantity(QUANTITY_NEGATIVE_5);
        supplyOperation.handle(transaction);
        assertEquals(QUANTITY_ZERO, storage.getFruitQuantity(FRUIT_ORANGE));
    }

    @Test
    public void handle_AddMultipleFruits_WhenQuantityIsPositive() {
        supplyOperation.handle(new FruitTransaction(FRUIT_APPLE, QUANTITY_POSITIVE_5));
        supplyOperation.handle(new FruitTransaction(FRUIT_APPLE, QUANTITY_POSITIVE_3));

        assertEquals(EXPECTED_QUANTITY_8, storage.getFruitQuantity(FRUIT_APPLE));
    }

    @Test
    public void handle_AddDifferentFruits() {
        supplyOperation.handle(new FruitTransaction(FRUIT_BANANA, EXPECTED_QUANTITY_5));
        supplyOperation.handle(new FruitTransaction(FRUIT_ORANGE, EXPECTED_QUANTITY_10_ORANGE));

        assertEquals(EXPECTED_QUANTITY_5, storage.getFruitQuantity(FRUIT_BANANA));
        assertEquals(EXPECTED_QUANTITY_10_ORANGE, storage.getFruitQuantity(FRUIT_ORANGE));
    }
}
