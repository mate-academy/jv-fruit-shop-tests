package core.basesyntax.service.operationhandler;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceHandlerTest {
    private BalanceHandler balanceHandler;

    @BeforeEach
    public void setUp() {
        Storage.fruits.clear();
        balanceHandler = new BalanceHandler();
    }

    @Test
    public void handle_rightAction_Ok() {
        FruitTransaction item = new FruitTransaction(Operation.BALANCE,
                "banana", 15);
        balanceHandler.handleOperation(item);
        int quantity = Storage.fruits.get("banana");
        Assertions.assertEquals(quantity, 15);
    }
}
