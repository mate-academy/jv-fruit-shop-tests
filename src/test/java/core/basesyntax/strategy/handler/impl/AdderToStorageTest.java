package core.basesyntax.strategy.handler.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.Before;
import org.junit.Test;

public class AdderToStorageTest {
    private static final String APPLE = "apple";
    private static final int VALID_FRUIT_QUANTITY = 5;
    private static final String BANANA = "banana";
    private static final int VALID_STORAGE_SIZE = 2;
    private static final int INVALID_FRUIT_QUANTITY = -5;

    @Before
    public void setUp() {
        Storage.storage.clear();
    }

    @Test
    public void test_add_transaction_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                APPLE,
                VALID_FRUIT_QUANTITY);
        AdderToStorage.add(transaction);
        Integer appleQuantity = Storage.storage.get(APPLE);
        assertNotNull(appleQuantity);
        assertSame(VALID_FRUIT_QUANTITY, appleQuantity);
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
        AdderToStorage.add(transaction1);
        AdderToStorage.add(transaction2);
        int storageSize = Storage.storage.size();
        assertEquals(VALID_STORAGE_SIZE, storageSize);
        assertSame(VALID_FRUIT_QUANTITY, Storage.storage.get(APPLE));
        assertSame(VALID_FRUIT_QUANTITY, Storage.storage.get(BANANA));
    }

    @Test(expected = RuntimeException.class)
    public void test_add_negative_quantity_not_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY,
                BANANA,
                INVALID_FRUIT_QUANTITY);
        AdderToStorage.add(transaction);
    }
}