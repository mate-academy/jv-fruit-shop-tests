package strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.Map;
import model.FruitTransaction;
import org.junit.jupiter.api.Test;
import strategy.OperationHandler;

public class OperationStrategyImplTest {
    @Test
    void getHandler_validOperation_ok() {
        OperationHandler balanceHandler = new BalanceOperation(null);
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, balanceHandler);

        OperationStrategyImpl strategy = new OperationStrategyImpl(handlers);

        OperationHandler result = strategy.getHandler(FruitTransaction.Operation.BALANCE);

        assertNotNull(result);
        assertEquals(balanceHandler, result);
    }
}
