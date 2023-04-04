package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceOperationHandlerTest {
    private BalanceOperationHandler balanceOperationHandler;

    @BeforeEach
    public void setUp() {
        Storage.fruits.clear();
        balanceOperationHandler = new BalanceOperationHandler();
    }

    @Test
    public void handle_rightAction_Ok() {
        FruitTransaction item = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 15);
        balanceOperationHandler.handle(item);
        int quantity = Storage.fruits.get("banana");
        Assertions.assertEquals(quantity, 15);
    }
}
