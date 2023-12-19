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
        FruitTransaction validTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "banana", 20);
        transactionProcessor.process(validTransaction);
        int actualQuantity = FruitDB.FRUIT_DATA_BASE
                .get(validTransaction.getFruit());
        int exceptedQuantity = 120;
        assertEquals(exceptedQuantity, actualQuantity);
    }

    @Test
    void process_invalidTransaction_notOk() {
        FruitTransaction invalidTransaction = null;
        try {
            invalidTransaction = new FruitTransaction(
                    FruitTransaction.Operation.RETURN, "banana", -10);
            Assertions.fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            Assertions.assertNotNull(e);
            assertEquals("Quantity can't be less then zero", e.getMessage());
            Assertions.assertNull(invalidTransaction);
        }
    }

    @Test
    void process_unknownFruit_Ok() {
        FruitTransaction unknownFruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "orange", 15);
        transactionProcessor.process(unknownFruitTransaction);
        int actualQuantity = FruitDB.FRUIT_DATA_BASE.getOrDefault(
                unknownFruitTransaction.getFruit(), 0);
        int expectedQuantity = 15;
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    void process_zeroQuantity_notOk() {
        FruitTransaction zeroQuantityTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 0);
        transactionProcessor.process(zeroQuantityTransaction);
        int actualQuantity = FruitDB.FRUIT_DATA_BASE.getOrDefault(
                zeroQuantityTransaction.getFruit(), 0);
        int exceptedQuantity = 100;
        assertEquals(exceptedQuantity, actualQuantity);
    }

    @Test
    void process_multipleTransactions_ok() {
        FruitTransaction transaction1 = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "apple", 15);
        FruitTransaction transaction2 = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "orange", 25);
        transactionProcessor.process(transaction1);
        transactionProcessor.process(transaction2);
        int actualQuantityApple = FruitDB.FRUIT_DATA_BASE.getOrDefault(
                transaction1.getFruit(), 0);
        int actualQuantityOrange = FruitDB.FRUIT_DATA_BASE.getOrDefault(
                transaction2.getFruit(), 0);
        int exceptedQuantityOne = 15;
        int exceptedQuantityTwo = 25;
        assertEquals(exceptedQuantityOne, actualQuantityApple);
        assertEquals(exceptedQuantityTwo, actualQuantityOrange);
    }
}
