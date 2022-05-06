package core.basesyntax.strategy;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.operation.Operation;
import core.basesyntax.service.activity.BalanceHandler;
import core.basesyntax.service.activity.OperationHandler;
import core.basesyntax.service.activity.SupplyHandler;
import org.junit.Assert;
import org.junit.Test;

public class OperationStrategyImplTest {
    @Test
    public void getOperation_Ok() {
        MapStrategy mapStrategy = new MapStrategy();
        FruitDao fruitDao = new FruitDaoImpl();
        OperationStrategy operationStrategy =
                new OperationStrategyImpl(mapStrategy.mapStrategy(fruitDao));
        OperationHandler operationHandler = operationStrategy.get(Operation.SUPPLY);
        OperationHandler supplyHandler = new SupplyHandler(fruitDao);
        Assert.assertEquals(supplyHandler.getClass(), operationHandler.getClass());
        operationHandler = operationStrategy.get(Operation.BALANCE);
        OperationHandler balanceHandler = new BalanceHandler(fruitDao);
        Assert.assertEquals(balanceHandler.getClass(), operationHandler.getClass());
    }
}
