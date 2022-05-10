package core.basesyntax.strategy;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.operation.Operation;
import core.basesyntax.service.activity.BalanceHandler;
import core.basesyntax.service.activity.OperationHandler;
import core.basesyntax.service.activity.PurchaseHandler;
import core.basesyntax.service.activity.ReturnHandler;
import core.basesyntax.service.activity.SupplyHandler;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static MapStrategy mapStrategy;
    private static FruitDao fruitDao;

    @BeforeClass
    public static void beforeClass() throws Exception {
        mapStrategy = new MapStrategy();
        fruitDao = new FruitDaoImpl();
    }

    @Test
    public void getOperation_Balance_Ok() {
        OperationStrategy operationStrategy =
                new OperationStrategyImpl(mapStrategy.mapStrategy(fruitDao));
        OperationHandler operationHandler = operationStrategy.get(Operation.BALANCE);
        OperationHandler balanceHandler = new BalanceHandler(fruitDao);
        Assert.assertEquals("Test failed! Expected operation: " + balanceHandler,
                balanceHandler.getClass(), operationHandler.getClass());

    }

    @Test
    public void getOperation_Supply_Ok() {
        OperationStrategy operationStrategy =
                new OperationStrategyImpl(mapStrategy.mapStrategy(fruitDao));
        OperationHandler operationHandler = operationStrategy.get(Operation.SUPPLY);
        OperationHandler supplyHandler = new SupplyHandler(fruitDao);
        Assert.assertEquals("Test failed! Expected operation: " + supplyHandler,
                supplyHandler.getClass(), operationHandler.getClass());
    }

    @Test
    public void getOperation_Purchase_Ok() {
        OperationStrategy operationStrategy =
                new OperationStrategyImpl(mapStrategy.mapStrategy(fruitDao));
        OperationHandler operationHandler = operationStrategy.get(Operation.PURCHASE);
        OperationHandler purchaseHandler = new PurchaseHandler(fruitDao);
        Assert.assertEquals("Test failed! Expected operation: " + purchaseHandler,
                purchaseHandler.getClass(), operationHandler.getClass());
    }

    @Test
    public void getOperation_Return_Ok() {
        OperationStrategy operationStrategy =
                new OperationStrategyImpl(mapStrategy.mapStrategy(fruitDao));
        OperationHandler operationHandler = operationStrategy.get(Operation.RETURN);
        OperationHandler returnHandler = new ReturnHandler(fruitDao);
        Assert.assertEquals("Test failed! Expected operation: " + returnHandler,
                returnHandler.getClass(), operationHandler.getClass());
    }

    @Test(expected = NullPointerException.class)
    public void getOperation_NotOk() {
        OperationStrategyImpl operationStrategy =
                new OperationStrategyImpl(mapStrategy.mapStrategy(fruitDao));
        operationStrategy.get(null).getClass();
    }
}
