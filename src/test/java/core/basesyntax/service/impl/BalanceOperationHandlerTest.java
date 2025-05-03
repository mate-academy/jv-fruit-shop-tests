package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class BalanceOperationHandlerTest {

    @Test
    void operate_balanceOperation_ok() {
        FruitTransaction transactionBalance =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "pear", 15);
        Integer expected = transactionBalance.getQuantity();
        OperationHandler handler = new BalanceOperationHandler();
        handler.operate(transactionBalance);
        Integer actual = Storage.fruits.get("pear");
        assertEquals(expected, actual, "Test failed! You should returned next quantity "
                + expected + " but you returned "
                + actual);
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }
}
