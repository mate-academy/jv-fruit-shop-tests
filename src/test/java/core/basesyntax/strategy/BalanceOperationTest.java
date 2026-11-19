package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private static OperationHandler balanceOperation;

    @BeforeAll
    static void beforeAll() {
        balanceOperation = new BalanceOperation();
    }

    @AfterEach
    void tearDown() {
        Storage.clear();
    }

    @Test
    void apply_validTransaction_Ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 20
        );
        balanceOperation.apply(transaction);
        assertEquals(20, Storage.get("banana"));
    }

    @Test
    void apply_nullTransaction_notOk() {
        assertThrows(Exception.class, () -> balanceOperation.apply(null));
    }

    @Test
    void apply_negativeQuantity_notOk() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", -5
        );
        assertThrows(IllegalArgumentException.class, () -> balanceOperation.apply(transaction));
    }
}
