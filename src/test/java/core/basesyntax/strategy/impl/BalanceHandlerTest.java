package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceHandlerTest {
    private static OperationHandler balanceHandler;

    @BeforeAll
    static void beforeAll() {
        balanceHandler = new BalanceHandler();
    }

    @BeforeEach
    void setUp() {
        Storage.storage.clear();
    }

    @Test
    void executeOperationBalanceHandlerValidData_Ok() {
        FruitTransaction transactionBalance =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "apple", 20);
        balanceHandler.executeOperation(transactionBalance);
        int expectedQuantity = 20;
        int actualQuantity = Storage.storage.get(transactionBalance.getFruit());
        assertEquals(expectedQuantity,actualQuantity);
    }

    @Test
    void executeOperationBalanceHandlerInValidData_NotOk() {
        FruitTransaction transactionBalance =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "apple", - 20);
        assertThrows(RuntimeException.class, () ->
                balanceHandler.executeOperation(transactionBalance));
    }

    @AfterAll
    static void afterAll() {
        Storage.storage.clear();
    }
}
