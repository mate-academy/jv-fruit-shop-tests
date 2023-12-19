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

    private TransactionProcessor transactionProcessor;

    @BeforeEach
    void setUp() {
        transactionProcessor = new ReturnTransactionStrategy();
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
        int actualQuantity = FruitDB.FRUIT_DATA_BASE.get(validTransaction.getFruit());
        assertEquals(120, actualQuantity);
    }

    @Test
    void process_invalidTransaction_notOk() {
        assertThrows(RuntimeException.class, () -> {
            FruitTransaction invalidTransaction = new FruitTransaction(
                    FruitTransaction.Operation.RETURN, "banana", -10);
            transactionProcessor.process(invalidTransaction);
        });
        int actualQuantity = FruitDB.FRUIT_DATA_BASE.getOrDefault("banana", 0);
        int exceptedQuantity = 100;
        assertEquals(exceptedQuantity, actualQuantity);
    }

    @Test
    void process_returnZeroQuantity_nothingChangesInDB() {
        FruitTransaction zeroQuantityTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "banana", 0);
        transactionProcessor.process(zeroQuantityTransaction);
        int actualQuantity = FruitDB.FRUIT_DATA_BASE.get(zeroQuantityTransaction.getFruit());
        int exceptedQuantity = 100;
        assertEquals(exceptedQuantity, actualQuantity);
    }

    @Test
    void process_newFruitAddedToDB_ok() {
        FruitTransaction newFruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "orange", 30);
        transactionProcessor.process(newFruitTransaction);
        int actualQuantity = FruitDB.FRUIT_DATA_BASE.get(newFruitTransaction.getFruit());
        int exceptedQuantity = 30;
        assertEquals(exceptedQuantity, actualQuantity);
    }
}
