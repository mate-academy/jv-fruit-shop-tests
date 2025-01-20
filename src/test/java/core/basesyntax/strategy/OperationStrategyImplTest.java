package core.basesyntax.strategy;

import core.basesyntax.model.Operation;
import core.basesyntax.operationhandlers.BalanceOperationHandler;
import core.basesyntax.operationhandlers.OperationHandler;
import core.basesyntax.operationhandlers.PurchaseOperationHandler;
import core.basesyntax.operationhandlers.ReturnOperationHandler;
import core.basesyntax.operationhandlers.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
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
        Assertions.assertEquals(new BalanceOperationHandler().getClass(),
                operationOperationHandlerMap.get(Operation.BALANCE).getClass());

        Assertions.assertNotNull(operationOperationHandlerMap.get(Operation.BALANCE));

        Assertions.assertEquals(new PurchaseOperationHandler().getClass(),
                operationOperationHandlerMap.get(Operation.PURCHASE).getClass());

        Assertions.assertEquals(new ReturnOperationHandler().getClass(),
                operationOperationHandlerMap.get(Operation.RETURN).getClass());


        Assertions.assertEquals(new SupplyOperationHandler().getClass(),
                operationOperationHandlerMap.get(Operation.SUPPLY).getClass());

    }
}
