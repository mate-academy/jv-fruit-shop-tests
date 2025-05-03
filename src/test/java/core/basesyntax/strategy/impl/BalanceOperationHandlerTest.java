package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private static OperationHandler balanceHandler;

    @BeforeAll
    public static void setUpAll() {
        balanceHandler = new BalanceOperationHandler();
    }

    @Test
    public void performOperation_regularCase_ok() {
        Storage.fruits.put("banana", 20);
        balanceHandler.performOperation("apple", 10);
        balanceHandler.performOperation("banana", 40);
        assertEquals(40, Storage.fruits.get("banana"));
        assertEquals(10, Storage.fruits.get("apple"));
    }
}
