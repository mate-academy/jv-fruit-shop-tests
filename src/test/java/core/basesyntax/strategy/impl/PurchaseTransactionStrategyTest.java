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

    private static final int DEFAULT_VALUE = 0;
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
        String fruit = "banana";
        int quantity = 20;
        FruitTransaction validTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, fruit, quantity);
        transactionProcessor.process(validTransaction);
        int actualQuantity = FruitDB.FRUIT_DATA_BASE.get(validTransaction.getFruit());
        int expectedQuantity = 80;
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    void process_invalidTransaction_notOk() {
        String fruit = "banana";
        int quantity = 200;
        FruitTransaction invalidTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, fruit, quantity);
        assertThrows(RuntimeException.class,
                () -> transactionProcessor.process(invalidTransaction));
    }

    @Test
    void process_multipleTransactions_ok() {
        String fruitApple = "apple";
        int quantityApple = 50;
        FruitDB.FRUIT_DATA_BASE.put(fruitApple, quantityApple);
        String fruitOrange = "orange";
        int quantityOrange = 77;
        FruitDB.FRUIT_DATA_BASE.put(fruitOrange, quantityOrange);
        int purchaseQuantityApple = 30;
        FruitTransaction transaction1 = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, fruitApple, purchaseQuantityApple);
        int purchaseQuantityOrange = 25;
        FruitTransaction transaction2 = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, fruitOrange, purchaseQuantityOrange);
        transactionProcessor.process(transaction1);
        transactionProcessor.process(transaction2);
        int actualQuantityApple = FruitDB.FRUIT_DATA_BASE
                .getOrDefault(fruitApple, DEFAULT_VALUE);
        int actualQuantityOrange = FruitDB
                .FRUIT_DATA_BASE.getOrDefault(fruitOrange, DEFAULT_VALUE);
        int expectedApple = 20;
        int expectedOrange = 52;
        assertEquals(expectedApple, actualQuantityApple);
        assertEquals(expectedOrange, actualQuantityOrange);
    }

    @Test
    void process_nonExistingFruit_notAddedToDB() {
        String fruit = "apple";
        int initialQuantityApple = FruitDB.FRUIT_DATA_BASE.getOrDefault(fruit, DEFAULT_VALUE);
        int requiredQuantity = 10;
        assertThrows(RuntimeException.class, () -> {
            if (initialQuantityApple < requiredQuantity) {
                FruitTransaction purchaseApplesTransaction = new FruitTransaction(
                        FruitTransaction.Operation
                                .PURCHASE, fruit, requiredQuantity - initialQuantityApple);
                transactionProcessor.process(purchaseApplesTransaction);
            }
        });
        int finalQuantityApple = FruitDB.FRUIT_DATA_BASE.getOrDefault(fruit, DEFAULT_VALUE);
        assertEquals(initialQuantityApple, finalQuantityApple);
    }

    @Test
    void process_negativeQuantity_notProcessed() {
        String fruit = "banana";
        int wrongQuantity = -10;
        assertThrows(RuntimeException.class, () -> {
            FruitTransaction negativeQuantityTransaction = new FruitTransaction(
                    FruitTransaction.Operation.PURCHASE, fruit, wrongQuantity);
            transactionProcessor.process(negativeQuantityTransaction);
        });
        int actualQuantity = FruitDB.FRUIT_DATA_BASE
                .getOrDefault(fruit, DEFAULT_VALUE);
        int exceptedQuantity = 100;
        assertEquals(exceptedQuantity, actualQuantity);
    }
}
