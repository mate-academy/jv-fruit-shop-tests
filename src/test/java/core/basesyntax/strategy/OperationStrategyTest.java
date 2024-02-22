package core.basesyntax.strategy;

import core.basesyntax.model.Operation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OperationStrategyTest {
    private OperationStrategy operationStrategy;
    private final Map<Operation, OperationHandler> operationHandlerMap = new HashMap<>();

    @Test
    void get_mapNullOperationNull_notOk() {
        operationStrategy = new OperationStrategyImpl(null);
        Assertions.assertThrows(RuntimeException.class,() -> operationStrategy.get(null));
    }

    @Test
    void get_mapEmptyOperationNull_notOk() {
        operationStrategy = new OperationStrategyImpl(new HashMap<>());
        Assertions.assertThrows(RuntimeException.class,() -> operationStrategy.get(null));
    }

    @Test
    void get_mapFilled_Ok() {
        initMapOperationHandler();
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        for (Map.Entry<Operation, OperationHandler> expected : operationHandlerMap.entrySet()) {
            Assertions.assertInstanceOf(expected.getValue().getClass(),
                    operationStrategy.get(expected.getKey()));
        }
    }

    @Test
    void get_operationOutOfMap_Ok() {
        operationHandlerMap.put(Operation.BALANCE, new BalanceHandlerImpl());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        OperationHandler actual = operationStrategy.get(Operation.RETURN);
        Assertions.assertNull(actual);
    }

    @AfterEach
    void tearDown() {
        operationHandlerMap.clear();
    }

    private void initMapOperationHandler() {
        operationHandlerMap.put(Operation.BALANCE, new BalanceHandlerImpl());
        operationHandlerMap.put(Operation.SUPPLY, new SupplyHandlerImpl());
        operationHandlerMap.put(Operation.PURCHASE, new PurchaseHandlerImpl());
        operationHandlerMap.put(Operation.RETURN, new ReturnHandlerImpl());
    }
}
