package core.basesyntax.strategy.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceHandlerTest {
    private BalanceHandler balanceHandler;

    @BeforeEach
    void setUp() {
        balanceHandler = new BalanceHandler();
    }

    @Test
    public void handle_inputValidTransaction_ok() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana",
                10);
        balanceHandler.handle(transaction);
        int actualQuantity = Storage.get(transaction.getFruit());
        int expectedQuantity = 10;
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    public void handle_inputZeroQuantity_ok() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana",
                0);
        balanceHandler.handle(transaction);
        int actualQuantity = Storage.get(transaction.getFruit());
        int expectedQuantity = 0;
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    public void handle_negativeInputValue_notOk() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana",
                -10);
        assertThrows(RuntimeException.class,
                () -> balanceHandler.handle(transaction));
    }

    @AfterEach
    public void afterEach() {
        Storage.fruitStorage.clear();
    }
}
