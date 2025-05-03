package core.basesyntax.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private static final String FRUIT = "banana";
    private Map<String, Integer> storage;
    private BalanceOperation balanceOperation;
    private FruitTransaction.Operation operation;

    @BeforeEach
    void setUp() {
        storage = new HashMap<>();
        balanceOperation = new BalanceOperation();
        operation = FruitTransaction.Operation.BALANCE;
    }

    private void applyTransaction(int quantity) {
        FruitTransaction transaction = new FruitTransaction(operation, FRUIT, quantity);
        balanceOperation.apply(storage, transaction);
    }

    @Test
    void balancePositive_OK() {
        int quantity = 100;
        applyTransaction(quantity);
        assertEquals(quantity, storage.get(FRUIT),
                "Failed in balancePositive_OK: "
                        + "Expected balance after positive transaction does not match.");
    }

    @Test
    void balanceZero_OK() {
        int quantity = 0;
        applyTransaction(quantity);
        assertEquals(quantity, storage.get(FRUIT),
                "Failed in balanceZero_OK: "
                        + "Expected balance should be zero when transaction quantity is zero.");
    }

    @Test
    void balanceNegative_NotOK() {
        int quantity = -100;
        assertThrows(RuntimeException.class, () -> applyTransaction(quantity),
                "Failed in balanceNegative_NotOK: "
                        + "Expected RuntimeException when transaction quantity is negative.");
    }
}
