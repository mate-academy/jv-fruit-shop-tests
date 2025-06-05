package core.basesyntax.operation.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private BalanceOperation operation;

    @BeforeEach
    void setUp() {
        operation = new BalanceOperation();
    }

    @Test
    void handle_Balance_Ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 10);
        operation.handle(transaction);
        assertEquals(10, FruitStorage.getStorage().get("apple"));
    }

    @AfterAll
    static void cleanMeth() {
        FruitStorage.getStorage().clear();
    }
}
