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
    private static final String APPLE = "apple";
    private static final int VALID_FRUIT_QUANTITY = 5;
    private static final String BANANA = "banana";
    private static final int VALID_STORAGE_SIZE = 2;
    private static final int INVALID_FRUIT_QUANTITY = -5;

    @AfterEach
    public void afterEachTest() {
        Storage.FRUITS.clear();
    }

    @Test
    public void test_add_transaction_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                APPLE,
                VALID_FRUIT_QUANTITY);
        StorageService.add(transaction);
        Integer appleQuantity = Storage.FRUITS.get(APPLE);
        assertNotNull(appleQuantity);
        assertSame(VALID_FRUIT_QUANTITY, appleQuantity);
    }

    @Test
    public void test_add_transaction_notOk() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                APPLE,
                INVALID_FRUIT_QUANTITY);
        assertThrows(RuntimeException.class, () -> StorageService.add(transaction));
    }

    @Test
    public void test_add_multiple_transactions_ok() {
        FruitTransaction transaction1 = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                APPLE,
                VALID_FRUIT_QUANTITY);
        FruitTransaction transaction2 = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY,
                BANANA,
                VALID_FRUIT_QUANTITY);
        StorageService.add(transaction1);
        StorageService.add(transaction2);
        int storageSize = Storage.FRUITS.size();
        assertEquals(VALID_STORAGE_SIZE, storageSize);
        assertSame(VALID_FRUIT_QUANTITY, Storage.FRUITS.get(APPLE));
        assertSame(VALID_FRUIT_QUANTITY, Storage.FRUITS.get(BANANA));
    }

    @Test
    public void test_add_multiple_transactions_notOk() {
        FruitTransaction transaction1 = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                APPLE,
                INVALID_FRUIT_QUANTITY);
        FruitTransaction transaction2 = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY,
                BANANA,
                INVALID_FRUIT_QUANTITY);
        assertThrows(RuntimeException.class, () -> StorageService.add(transaction1));
        assertThrows(RuntimeException.class, () -> StorageService.add(transaction2));
    }
}
