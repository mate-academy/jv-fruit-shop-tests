package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.BalanceOperation;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.strategy.OperationStrategy;
import core.basesyntax.service.strategy.OperationStrategyImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class OperationStrategyTest {

    @Test
    void getHandler_validOperation_ok() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        OperationHandler balanceHandler = new BalanceOperation();
        handlers.put(FruitTransaction.Operation.BALANCE, balanceHandler);
        OperationStrategy strategy = new OperationStrategyImpl(handlers);
        OperationHandler actual = strategy.getHandler(FruitTransaction.Operation.BALANCE);
        assertEquals(balanceHandler, actual);
    }

    @Test
    void gethandler_unregisteredOperatio_notOk() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        OperationStrategy strategy = new OperationStrategyImpl(handlers);
        assertThrows(RuntimeException.class,
                () -> strategy.getHandler(FruitTransaction.Operation.PURCHASE));
    }
}
