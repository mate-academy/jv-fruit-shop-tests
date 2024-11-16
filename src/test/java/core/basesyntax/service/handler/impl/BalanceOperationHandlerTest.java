package core.basesyntax.service.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.enums.Operation;
import java.util.Map;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest extends BaseOperationHandlerTest {
    @Test
    void handle_balanceFruitTransaction_Ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.BALANCE, BANANA, 10);
        handleTransaction(transaction);
        assertEquals(Map.of(BANANA, 10), db);
    }
}
