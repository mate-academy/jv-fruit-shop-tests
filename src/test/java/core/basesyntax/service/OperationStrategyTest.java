package core.basesyntax.service;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.dao.FruitsDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.operation.BalanceOperation;
import core.basesyntax.strategy.operation.OperationHandler;
import core.basesyntax.strategy.operation.PurchaseOperation;
import core.basesyntax.strategy.operation.ReturnOperation;
import core.basesyntax.strategy.operation.SupplyOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationStrategyTest {
    private FruitsDao fruitsDao;
    private OperationStrategy operationStrategy;
    private Map<FruitTransaction.Operation, OperationHandler> operationHandlers;

    @BeforeEach
    public void setUp() throws Exception {
        fruitsDao = new FruitsDaoImpl();
        operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation(fruitsDao));
        operationHandlers.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperation(fruitsDao));
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation(fruitsDao));
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation(fruitsDao));
        operationStrategy = new OperationStrategyImpl(operationHandlers);
    }

    @Test
    public void operationStrategy_ok() {
        OperationHandler expected = new PurchaseOperation(fruitsDao);
        OperationHandler operationHandler
                = operationStrategy.get(FruitTransaction.Operation.PURCHASE);
        Assertions.assertEquals(expected.getClass(), operationHandler.getClass());
    }
  
}
