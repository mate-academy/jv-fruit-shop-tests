package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.FruitStorage;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {

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
        transaction.setFruit("apple");
        transaction.setQuantity(10);
        supplyOperation.handle(transaction);
        assertEquals(10, storage.getFruitQuantity("apple"));
    }

    @Test
    public void handle_NotAddFruitToStorage_WhenQuantityIsZero() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("banana");
        transaction.setQuantity(0);
        supplyOperation.handle(transaction);
        assertEquals(0, storage.getFruitQuantity("banana"));
    }

    @Test
    public void handle_NotAddFruitToStorage_WhenQuantityIsNegative() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("orange");
        transaction.setQuantity(-5);
        supplyOperation.handle(transaction);
        assertEquals(0, storage.getFruitQuantity("orange"));
    }

    @Test
    public void handle_AddMultipleFruits_WhenQuantityIsPositive() {
        supplyOperation.handle(new FruitTransaction("apple", 5));
        supplyOperation.handle(new FruitTransaction("apple", 3));

        assertEquals(8, storage.getFruitQuantity("apple"));
    }

    @Test
    public void handle_AddDifferentFruits() {
        supplyOperation.handle(new FruitTransaction("banana", 5));
        supplyOperation.handle(new FruitTransaction("orange", 10));

        assertEquals(5, storage.getFruitQuantity("banana"));
        assertEquals(10, storage.getFruitQuantity("orange"));
    }
}
