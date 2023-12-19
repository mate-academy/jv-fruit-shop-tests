package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.FruitTransaction;
import core.basesyntax.db.FruitDB;
import core.basesyntax.strategy.TransactionProcessor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseTransactionStrategyTest {

    private TransactionProcessor transactionProcessor;

    @BeforeEach
    void setUp() {
        transactionProcessor = new PurchaseTransactionStrategy();
        FruitDB.FRUIT_DATA_BASE.put("banana", 100);
    }

    @AfterEach
    void tearDown() {
        FruitDB.FRUIT_DATA_BASE.clear();
    }

    @Test
    void process_validTransaction_ok() {
        FruitTransaction validTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 20);
        transactionProcessor.process(validTransaction);
        int actualQuantity = FruitDB.FRUIT_DATA_BASE.get(validTransaction.getFruit());
        assertEquals(80, actualQuantity);
    }

    @Test
    void process_invalidTransaction_notOk() {
        FruitTransaction invalidTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 200);
        assertThrows(RuntimeException.class,
                () -> transactionProcessor.process(invalidTransaction));
    }

    @Test
    void process_multipleTransactions_ok() {
        FruitDB.FRUIT_DATA_BASE.put("apple", 50);
        FruitDB.FRUIT_DATA_BASE.put("orange", 77);
        FruitTransaction transaction1 = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 30);
        FruitTransaction transaction2 = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "orange", 25);
        transactionProcessor.process(transaction1);
        transactionProcessor.process(transaction2);
        int defaultValue = 0;
        int quantityApple = FruitDB.FRUIT_DATA_BASE
                .getOrDefault("apple", defaultValue);
        int quantityOrange = FruitDB
                .FRUIT_DATA_BASE.getOrDefault("orange", defaultValue);
        int expectedApple = 20;
        int expectedOrange = 52;
        assertEquals(expectedApple, quantityApple);
        assertEquals(expectedOrange, quantityOrange);
    }

    @Test
    void process_nonExistingFruit_notAddedToDB() {
        int initialQuantityApple = FruitDB.FRUIT_DATA_BASE.getOrDefault("apple", 0);
        int requiredQuantity = 10;
        assertThrows(RuntimeException.class, () -> {
            if (initialQuantityApple < requiredQuantity) {
                FruitTransaction purchaseApplesTransaction = new FruitTransaction(
                        FruitTransaction.Operation
                                .PURCHASE, "apple", requiredQuantity - initialQuantityApple);
                transactionProcessor.process(purchaseApplesTransaction);
            }
        });
        int finalQuantityApple = FruitDB.FRUIT_DATA_BASE.getOrDefault("apple", 0);
        assertEquals(initialQuantityApple, finalQuantityApple);
    }

    @Test
    void process_negativeQuantity_notProcessed() {
        assertThrows(RuntimeException.class, () -> {
            FruitTransaction negativeQuantityTransaction = new FruitTransaction(
                    FruitTransaction.Operation.PURCHASE, "banana", -10);
            transactionProcessor.process(negativeQuantityTransaction);
        });
        int actualQuantity = FruitDB.FRUIT_DATA_BASE
                .getOrDefault("banana", 0);
        int exceptedQuantity = 100;
        assertEquals(exceptedQuantity, actualQuantity);
    }
}
