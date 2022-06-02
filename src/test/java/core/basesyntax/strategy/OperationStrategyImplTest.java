package core.basesyntax.strategy;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitService;
import core.basesyntax.service.impl.FruitServiceImpl;
import core.basesyntax.strategy.handler.BalanceHandler;
import core.basesyntax.strategy.handler.OperationHandler;
import core.basesyntax.strategy.handler.PurchaseHandler;
import core.basesyntax.strategy.handler.ReturnHandler;
import core.basesyntax.strategy.handler.SupplyHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static FruitService fruitService;
    private static OperationStrategy operationStrategy;

    @BeforeClass
    public static void setUp() {
        FruitDao fruitDao = new FruitDaoImpl();
        fruitService = new FruitServiceImpl(fruitDao);
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        operationHandlerMap
                .put(FruitTransaction.Operation.BALANCE, new BalanceHandler(fruitService));
        operationHandlerMap
                .put(FruitTransaction.Operation.SUPPLY, new SupplyHandler(fruitService));
        operationHandlerMap
                .put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler(fruitService));
        operationHandlerMap
                .put(FruitTransaction.Operation.RETURN, new ReturnHandler(fruitService));
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void getHandlerBalance_Ok() {
        OperationHandler actualBalance =
                operationStrategy.getHandler(FruitTransaction.Operation.BALANCE);
        BalanceHandler expectedBalance = new BalanceHandler(fruitService);
        Assert.assertEquals(expectedBalance.getClass(), actualBalance.getClass());
    }

    @Test
    public void getHandlerSupply_Ok() {
        OperationHandler actualSupply =
                operationStrategy.getHandler(FruitTransaction.Operation.SUPPLY);
        SupplyHandler expectedSupply = new SupplyHandler(fruitService);
        Assert.assertEquals(expectedSupply.getClass(),actualSupply.getClass());
    }

    @Test
    public void getHandlerPurchase_Ok() {
        OperationHandler actualPurchase =
                operationStrategy.getHandler(FruitTransaction.Operation.PURCHASE);
        PurchaseHandler expectedPurchase = new PurchaseHandler(fruitService);
        Assert.assertEquals(expectedPurchase.getClass(),actualPurchase.getClass());
    }

    @Test
    public void getHandlerReturn_Ok() {
        OperationHandler actualReturn =
                operationStrategy.getHandler(FruitTransaction.Operation.RETURN);
        ReturnHandler expectedReturn = new ReturnHandler(fruitService);
        Assert.assertEquals(expectedReturn.getClass(),actualReturn.getClass());
    }
}
