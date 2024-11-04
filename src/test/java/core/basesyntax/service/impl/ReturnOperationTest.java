package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.FruitStorage;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
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
        transaction.setFruit("apple");
        transaction.setQuantity(10);
        returnOperation.handle(transaction);
        assertEquals(10, storage.getFruitQuantity("apple"));
    }

    @Test
    public void handle_IncreaseFruitQuantity_WhenFruitAlreadyExists() {
        storage.updateFruitQuantity("banana", 15);
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("banana");
        transaction.setQuantity(5);
        new ReturnOperation(storage).handle(transaction);
        assertEquals(20, storage.getFruitQuantity("banana"));
    }

    @Test
    public void handle_DoNothing_WhenQuantityIsZero() {
        storage.updateFruitQuantity("orange", 10);
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("orange");
        transaction.setQuantity(0);
        returnOperation.handle(transaction);

        assertEquals(10, storage.getFruitQuantity("orange"));
    }

}
