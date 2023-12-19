package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.FruitTransaction;
import core.basesyntax.db.FruitDB;
import core.basesyntax.strategy.TransactionProcessor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceTransactionStrategyTest {

    private TransactionProcessor transactionProcessor;

    @BeforeEach
    void setUp() {
        transactionProcessor = new BalanceTransactionStrategy();
    }

    @AfterEach
    void tearDown() {
        FruitDB.FRUIT_DATA_BASE.clear();
    }

    @Test
    void process_validTransaction_ok() {
        FruitTransaction validTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 20);
        transactionProcessor.process(validTransaction);
        int actualQuantity = FruitDB.FRUIT_DATA_BASE
                .get(validTransaction.getFruit());
        assertEquals(validTransaction.getQuantity(), actualQuantity);
    }

    @Test
    void process_invalidTransaction_notOk() {
        FruitTransaction invalidTransaction = null;
        try {
            invalidTransaction = new FruitTransaction(
                    FruitTransaction.Operation.BALANCE, "banana", -10);
            transactionProcessor.process(invalidTransaction);
            Assertions.fail("Exception should have been thrown");
        } catch (IllegalArgumentException e) {
            assertNull(invalidTransaction);
            int actualQuantity = FruitDB.FRUIT_DATA_BASE
                    .getOrDefault("banana", 0);
            assertEquals(0, actualQuantity);
        }
    }

    @Test
    void process_newFruitTransaction_ok() {
        FruitTransaction newFruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "orange", 15);
        transactionProcessor.process(newFruitTransaction);
        int actualQuantity = FruitDB.FRUIT_DATA_BASE
                .getOrDefault(newFruitTransaction.getFruit(), 0);
        assertEquals(newFruitTransaction.getQuantity(), actualQuantity);
    }

    @Test
    void process_multipleTransactions_ok() {
        FruitTransaction transaction1 = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 50);
        FruitTransaction transaction2 = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 30);
        transactionProcessor.process(transaction1);
        transactionProcessor.process(transaction2);
        int quantityApple = FruitDB.FRUIT_DATA_BASE
                .getOrDefault("apple", 0);
        int quantityBanana = FruitDB.FRUIT_DATA_BASE
                .getOrDefault("banana", 0);
        assertEquals(50, quantityApple);
        assertEquals(30, quantityBanana);
    }

    @Test
    void process_largeAmountOfFruit_ok() {
        FruitTransaction largeTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", Integer.MAX_VALUE);
        transactionProcessor.process(largeTransaction);
        int actualQuantity = FruitDB.FRUIT_DATA_BASE.getOrDefault("banana", 0);
        int expectedQuantity = Integer.MAX_VALUE;
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    void process_negativeQuantity_ok() {
        final FruitTransaction[] invalidTransaction = {null};
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            invalidTransaction[0] = new FruitTransaction(
                    FruitTransaction.Operation.BALANCE, "banana", -10);
            transactionProcessor.process(invalidTransaction[0]);
        });
        assertNull(invalidTransaction[0]);
        assertEquals("Quantity can't be less then zero", exception.getMessage());
        int actualQuantity = FruitDB.FRUIT_DATA_BASE.getOrDefault("banana", 0);
        int expectedQuantity = 0;
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    void process_nullTransaction_notOk() {
        FruitTransaction nullTransaction = null;
        Assertions.assertThrows(NullPointerException.class, () ->
                transactionProcessor.process(nullTransaction));
        int expectedSize = 0;
        int actualSize = FruitDB.FRUIT_DATA_BASE.size();
        assertEquals(expectedSize, actualSize);
    }
}
