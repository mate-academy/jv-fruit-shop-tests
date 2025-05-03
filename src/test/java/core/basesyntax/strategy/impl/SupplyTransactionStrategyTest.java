package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.FruitTransaction;
import core.basesyntax.db.FruitDB;
import core.basesyntax.strategy.TransactionProcessor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyTransactionStrategyTest {

    private static final int DEFAULT_VALUE = 0;
    private TransactionProcessor transactionProcessor;

    @BeforeEach
    void setUp() {
        transactionProcessor = new SupplyTransactionStrategy();
        FruitDB.FRUIT_DATA_BASE.put("banana", 100);
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
                FruitTransaction.Operation.RETURN, fruit, quantity);
        transactionProcessor.process(validTransaction);
        int actualQuantity = FruitDB.FRUIT_DATA_BASE
                .get(validTransaction.getFruit());
        int exceptedQuantity = 120;
        assertEquals(exceptedQuantity, actualQuantity);
    }

    @Test
    void process_invalidTransaction_notOk() {
        String fruit = "orange";
        int quantity = -10;
        String failMessage = "Expected an IllegalArgumentException to be thrown";
        FruitTransaction invalidTransaction = null;
        try {
            invalidTransaction = new FruitTransaction(
                    FruitTransaction.Operation.RETURN, fruit, quantity);
            Assertions.fail(failMessage);
        } catch (IllegalArgumentException e) {
            Assertions.assertNotNull(e);
            String expectedMessage = "Quantity can't be less then zero";
            assertEquals(expectedMessage, e.getMessage());
            Assertions.assertNull(invalidTransaction);
        }
    }

    @Test
    void process_unknownFruit_Ok() {
        String fruit = "apple";
        int quantity = 15;
        FruitTransaction unknownFruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, fruit, quantity);
        transactionProcessor.process(unknownFruitTransaction);
        int actualQuantity = FruitDB.FRUIT_DATA_BASE.getOrDefault(
                unknownFruitTransaction.getFruit(), DEFAULT_VALUE);
        int expectedQuantity = 15;
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    void process_zeroQuantity_notOk() {
        String fruit = "banana";
        int quantity = 0;
        FruitTransaction zeroQuantityTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, fruit, quantity);
        transactionProcessor.process(zeroQuantityTransaction);
        int actualQuantity = FruitDB.FRUIT_DATA_BASE.getOrDefault(
                zeroQuantityTransaction.getFruit(), DEFAULT_VALUE);
        int exceptedQuantity = 100;
        assertEquals(exceptedQuantity, actualQuantity);
    }

    @Test
    void process_multipleTransactions_ok() {
        String fruitApple = "apple";
        int quantityApple = 15;
        FruitTransaction transaction1 = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, fruitApple, quantityApple);
        String fruitOrange = "orange";
        int quantityOrange = 25;
        FruitTransaction transaction2 = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, fruitOrange, quantityOrange);
        transactionProcessor.process(transaction1);
        transactionProcessor.process(transaction2);
        int actualQuantityApple = FruitDB.FRUIT_DATA_BASE.getOrDefault(
                transaction1.getFruit(), DEFAULT_VALUE);
        int actualQuantityOrange = FruitDB.FRUIT_DATA_BASE.getOrDefault(
                transaction2.getFruit(), DEFAULT_VALUE);
        int exceptedQuantityOne = 15;
        int exceptedQuantityTwo = 25;
        assertEquals(exceptedQuantityOne, actualQuantityApple);
        assertEquals(exceptedQuantityTwo, actualQuantityOrange);
    }
}
