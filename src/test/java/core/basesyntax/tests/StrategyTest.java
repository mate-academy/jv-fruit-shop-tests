package core.basesyntax.tests;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.dao.FruitsDao;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.BalanceHandler;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class StrategyTest {
    @Test
    public void operationHandler_CorrectData_Ok() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        FruitsDao fruitsDao = new FruitDaoImpl();
        OperationHandler operationHandler = new BalanceHandler(fruitsDao);
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, operationHandler);
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        operationStrategy.get(FruitTransaction.Operation.BALANCE);
        assertEquals(operationStrategy.get(FruitTransaction.Operation.BALANCE), operationHandler);
    }
}
