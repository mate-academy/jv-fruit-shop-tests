package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.db.StorageImpl;
import core.basesyntax.exception.InvalidValueExeption;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationProcessor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private static Storage storageImpl;
    private static OperationProcessor balanceOperation;

    @BeforeAll
    static void setUp() {
        storageImpl = new StorageImpl();
        balanceOperation = new BalanceOperation(storageImpl);
    }

    @Test
    void process_positiveQuantity_ok() {
        Storage.storage.put("apple", 100);
        FruitTransaction fruitTransaction = new FruitTransaction(FruitTransaction.Operation
                .BALANCE, "apple", 50);
        balanceOperation.process(fruitTransaction);
        int actual = Storage.storage.get("apple");
        assertEquals(150, actual);
    }

    @Test
    void process_negativeQuantity_throwsInvalidValueException() {
        Storage.storage.put("banana", 100);
        FruitTransaction fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", -150);
        assertThrows(InvalidValueExeption.class, () -> balanceOperation.process(fruitTransaction));
    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }
}
