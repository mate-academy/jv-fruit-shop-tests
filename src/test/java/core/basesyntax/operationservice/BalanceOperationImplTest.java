package core.basesyntax.operationservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationImplTest {
    private BalanceOperationImpl balanceOperation;

    @BeforeEach
    void setUp() {
        Storage.clearStorage();
        balanceOperation = new BalanceOperationImpl();
    }

    @Test
    void apply_existingFruit_updatesBalance() {
        Storage.putFruit("banana", 50);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 100);
        balanceOperation.apply(transaction);
        assertEquals(100, Storage.getQuantity("banana"));
    }

    @Test
    void apply_newFruit_addsToStorage() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 80);
        balanceOperation.apply(transaction);
        assertEquals(80, Storage.getQuantity("apple"));
    }

    @Test
    void apply_zeroQuantity_addsToStorage() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "kiwi", 0);
        balanceOperation.apply(transaction);
        assertEquals(0, Storage.getQuantity("kiwi"));
    }

    @Test
    void apply_negativeQuantity_throwsException() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", -50);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> balanceOperation.apply(transaction));
        assertTrue(exception.getMessage().contains("Error: negative amount"));
    }
}
