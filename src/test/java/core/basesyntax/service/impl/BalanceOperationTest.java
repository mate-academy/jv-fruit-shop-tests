package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.FruitStorage;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
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
        transaction.setFruit("apple");
        transaction.setQuantity(50);
        balanceOperation.handle(transaction);
        assertEquals(50, storage.getFruitQuantity("apple"));
    }

    @Test
    public void handle_UpdateFruitQuantity_WhenFruitAlreadyExists() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("banana");
        transaction.setQuantity(100);
        storage.addFruit("banana", 20);
        balanceOperation.handle(transaction);
        assertEquals(100, storage.getFruitQuantity("banana"));
    }

    @Test
    public void handle_UpdateFruitQuantity_ToZeroQuantity() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("orange");
        transaction.setQuantity(0);
        storage.addFruit("orange", 30);
        balanceOperation.handle(transaction);
        assertEquals(0, storage.getFruitQuantity("orange"));
    }

    @Test
    public void handle_UpdateFruitQuantity_WithNegativeQuantity() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("grape");
        transaction.setQuantity(-15);
        balanceOperation.handle(transaction);
        assertEquals(-15, storage.getFruitQuantity("grape"));
    }

}
