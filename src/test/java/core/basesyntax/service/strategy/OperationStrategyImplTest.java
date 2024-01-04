package core.basesyntax.service.strategy;

import core.basesyntax.dao.FruitShopDao;
import core.basesyntax.dao.FruitShopDaoImpl;
import core.basesyntax.model.Operation;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperationHandlerImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private Map<Operation, OperationHandler> operationHandlerMap;
    private OperationStrategy operationStrategy;
    private OperationHandler operationHandler;
    private FruitShopDao fruitShopDao;

    @BeforeEach
    void setUp() {
        fruitShopDao = new FruitShopDaoImpl();
        operationHandler = new PurchaseOperationHandlerImpl(fruitShopDao);
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(Operation.PURCHASE, operationHandler);
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    void operationStrategyIsWork_ok() {
        Assertions.assertDoesNotThrow(() -> {
            operationStrategy.getOperationHandler(Operation.PURCHASE);
        });
    }
}
