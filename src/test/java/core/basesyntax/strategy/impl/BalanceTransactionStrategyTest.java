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

    private static final int DEFAULT_VALUE = 0;
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
        String fruit = "banana";
        int quantity = 20;
        FruitTransaction validTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, fruit, quantity);
        transactionProcessor.process(validTransaction);
        int actualQuantity = FruitDB.FRUIT_DATA_BASE
                .get(validTransaction.getFruit());
        assertEquals(validTransaction.getQuantity(), actualQuantity);
    }

    @Test
    void process_invalidTransaction_notOk() {
        String fruit = "banana";
        int wrongQuantity = -10;
        String failMessage = "Exception should have been thrown";
        FruitTransaction invalidTransaction = null;
        try {
            invalidTransaction = new FruitTransaction(
                    FruitTransaction.Operation.BALANCE, fruit, wrongQuantity);
            transactionProcessor.process(invalidTransaction);
            Assertions.fail(failMessage);
        } catch (IllegalArgumentException e) {
            assertNull(invalidTransaction);
            int actualQuantity = FruitDB.FRUIT_DATA_BASE
                    .getOrDefault(fruit, DEFAULT_VALUE);
            int expectedQuantity = 0;
            assertEquals(expectedQuantity, actualQuantity);
        }
    }

    @Test
    void process_newFruitTransaction_ok() {
        String fruit = "orange";
        int quantity = 15;
        FruitTransaction newFruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, fruit, quantity);
        transactionProcessor.process(newFruitTransaction);
        int actualQuantity = FruitDB.FRUIT_DATA_BASE
                .getOrDefault(newFruitTransaction.getFruit(), DEFAULT_VALUE);
        assertEquals(newFruitTransaction.getQuantity(), actualQuantity);
    }

    @Test
    void process_multipleTransactions_ok() {
        String fruitApple = "apple";
        int quantityApple = 50;
        FruitTransaction transaction1 = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, fruitApple, quantityApple);
        String fruitBanana = "banana";
        int quantityBanana = 30;
        FruitTransaction transaction2 = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, fruitBanana, quantityBanana);
        transactionProcessor.process(transaction1);
        transactionProcessor.process(transaction2);
        int actualQuantityApple = FruitDB.FRUIT_DATA_BASE
                .getOrDefault(fruitApple, DEFAULT_VALUE);
        int actualQuantityBanana = FruitDB.FRUIT_DATA_BASE
                .getOrDefault(fruitBanana, DEFAULT_VALUE);
        int expectedQuantityApple = 50;
        int expectedQuantityBanana = 30;
        assertEquals(expectedQuantityApple, actualQuantityApple);
        assertEquals(expectedQuantityBanana, actualQuantityBanana);
    }

    @Test
    void process_largeAmountOfFruit_ok() {
        String fruit = "banana";
        FruitTransaction largeTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, fruit, Integer.MAX_VALUE);
        transactionProcessor.process(largeTransaction);
        int actualQuantity = FruitDB.FRUIT_DATA_BASE.getOrDefault(fruit, DEFAULT_VALUE);
        int expectedQuantity = Integer.MAX_VALUE;
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    void process_negativeQuantity_ok() {
        String fruit = "banana";
        int quantity = -10;
        final FruitTransaction[] invalidTransaction = {null};
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            invalidTransaction[0] = new FruitTransaction(
                    FruitTransaction.Operation.BALANCE, fruit, quantity);
            transactionProcessor.process(invalidTransaction[0]);
        });
        assertNull(invalidTransaction[0]);
        String expectedMessage = "Quantity can't be less then zero";
        assertEquals(expectedMessage, exception.getMessage());
        int actualQuantity = FruitDB.FRUIT_DATA_BASE.getOrDefault(fruit, DEFAULT_VALUE);
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
