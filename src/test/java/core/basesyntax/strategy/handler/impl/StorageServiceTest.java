package core.basesyntax.strategy.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class StorageServiceTest {
    @AfterEach
    public void afterEachTest() {
        Storage.FRUITS.clear();
    }

    @Test
    public void add_transaction_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                "apple",
                5);
        StorageService.add(transaction);
        Integer appleQuantity = Storage.FRUITS.get("apple");
        assertNotNull(appleQuantity);
        assertSame(5, appleQuantity);
    }

    @Test
    public void add_Transaction_notOk() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                "apple",
                -5);
        assertThrows(RuntimeException.class, () -> StorageService.add(transaction));
    }

    @Test
    public void add_multipleTransactions_ok() {
        FruitTransaction transaction1 = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                "apple",
                5);
        FruitTransaction transaction2 = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY,
                "banana",
                5);
        StorageService.add(transaction1);
        StorageService.add(transaction2);
        int storageSize = Storage.FRUITS.size();
        assertEquals(2, storageSize);
        assertSame(5, Storage.FRUITS.get("apple"));
        assertSame(5, Storage.FRUITS.get("banana"));
    }

    @Test
    public void add_multipleTransactions_notOk() {
        FruitTransaction transaction1 = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                "apple",
                -5);
        FruitTransaction transaction2 = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY,
                "banana",
                -5);
        assertThrows(RuntimeException.class, () -> StorageService.add(transaction1));
        assertThrows(RuntimeException.class, () -> StorageService.add(transaction2));
    }
}
