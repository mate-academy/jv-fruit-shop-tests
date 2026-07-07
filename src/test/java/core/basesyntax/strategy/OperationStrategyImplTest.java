package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class OperationStrategyImplTest {
    @Test
    public void get_validOperation_ok() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        OperationHandler balanceHandler = new BalanceOperation();
        handlers.put(FruitTransaction.Operation.BALANCE, balanceHandler);

        OperationStrategy strategy = new OperationStrategyImpl(handlers);
        assertEquals(balanceHandler, strategy.get(FruitTransaction.Operation.BALANCE));
    }
}



