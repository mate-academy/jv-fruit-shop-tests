package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {

    private BalanceOperation balanceOperation;

    @BeforeEach
    void setUp() {
        balanceOperation = new BalanceOperation();
        Storage.clear();

    }

    @AfterEach
    void clearStorage() {
        Storage.clear();
    }

    @Test
    void apply_shouldSetCorrectlyQuantityToStorage() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 50);
        balanceOperation.apply(transaction);
        int actual = Storage.getQuantity("banana");
        assertEquals(50, actual);
    }

    @Test
    void apply_negativeQuantity_shouldThrownException() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", -10);
        assertThrows(RuntimeException.class, () -> balanceOperation.apply(transaction));
    }

}
