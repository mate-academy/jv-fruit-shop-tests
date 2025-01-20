package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.Operation;
import core.basesyntax.operationhandlers.BalanceOperationHandler;
import core.basesyntax.operationhandlers.OperationHandler;
import core.basesyntax.operationhandlers.PurchaseOperationHandler;
import core.basesyntax.operationhandlers.ReturnOperationHandler;
import core.basesyntax.operationhandlers.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private Map<Operation, OperationHandler> operationOperationHandlerMap = new HashMap<>();
    private OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        operationOperationHandlerMap.put(Operation.BALANCE, new BalanceOperationHandler());
        operationOperationHandlerMap.put(Operation.SUPPLY, new SupplyOperationHandler());
        operationOperationHandlerMap.put(Operation.PURCHASE, new PurchaseOperationHandler());
        operationOperationHandlerMap.put(Operation.RETURN, new ReturnOperationHandler());
        operationStrategy = new OperationStrategyImpl(operationOperationHandlerMap);
    }

    @Test
    void check_validOperationHandlers_ok() {
        assertEquals(new BalanceOperationHandler().getClass(),
                operationOperationHandlerMap.get(Operation.BALANCE).getClass());

        assertEquals(new PurchaseOperationHandler().getClass(),
                operationOperationHandlerMap.get(Operation.PURCHASE).getClass());

        assertEquals(new ReturnOperationHandler().getClass(),
                operationOperationHandlerMap.get(Operation.RETURN).getClass());

        assertEquals(new SupplyOperationHandler().getClass(),
                operationOperationHandlerMap.get(Operation.SUPPLY).getClass());

    }
}
