package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.FruitTransaction;
import core.basesyntax.db.FruitDB;
import core.basesyntax.strategy.TransactionProcessor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnTransactionStrategyTest {

    private static final String FRUIT = "banana";
    private TransactionProcessor transactionProcessor;

    @BeforeEach
    void setUp() {
        transactionProcessor = new ReturnTransactionStrategy();
        FruitDB.FRUIT_DATA_BASE.put(FRUIT, 100);
    }

    @AfterEach
    void tearDown() {
        FruitDB.FRUIT_DATA_BASE.clear();
    }

    @Test
    void process_validTransaction_ok() {
        int quantity = 20;
        FruitTransaction validTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, FRUIT, quantity);
        transactionProcessor.process(validTransaction);
        int actualQuantity = FruitDB.FRUIT_DATA_BASE.get(validTransaction.getFruit());
        int exceptedQuantity = 120;
        assertEquals(exceptedQuantity, actualQuantity);
    }

    @Test
    void process_invalidTransaction_notOk() {
        int wrongQuantity = -10;
        assertThrows(RuntimeException.class, () -> {
            FruitTransaction invalidTransaction = new FruitTransaction(
                    FruitTransaction.Operation.RETURN, FRUIT, wrongQuantity);
            transactionProcessor.process(invalidTransaction);
        });
        int actualQuantity = FruitDB.FRUIT_DATA_BASE.getOrDefault(FRUIT, 0);
        int exceptedQuantity = 100;
        assertEquals(exceptedQuantity, actualQuantity);
    }

    @Test
    void process_returnZeroQuantity_nothingChangesInDB() {
        int quantity = 0;
        FruitTransaction zeroQuantityTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, FRUIT, quantity);
        transactionProcessor.process(zeroQuantityTransaction);
        int actualQuantity = FruitDB.FRUIT_DATA_BASE.get(zeroQuantityTransaction.getFruit());
        int exceptedQuantity = 100;
        assertEquals(exceptedQuantity, actualQuantity);
    }

    @Test
    void process_newFruitAddedToDB_ok() {
        FruitDB.FRUIT_DATA_BASE.clear();
        int quantity = 30;
        FruitTransaction newFruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, FRUIT, quantity);
        transactionProcessor.process(newFruitTransaction);
        int actualQuantity = FruitDB.FRUIT_DATA_BASE.get(newFruitTransaction.getFruit());
        int exceptedQuantity = 30;
        assertEquals(exceptedQuantity, actualQuantity);
    }
}
