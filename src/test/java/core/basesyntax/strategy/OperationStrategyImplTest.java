package core.basesyntax.strategy;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.BalanceOperationHandler;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.PurchaseOperationHandler;
import core.basesyntax.operation.ReturnOperationHandler;
import core.basesyntax.operation.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static FruitDao fruitDao;
    private static OperationStrategy strategy;

    @BeforeClass
    public static void setUp() {
        fruitDao = new FruitDaoImpl();
        fruitDao.addFruit("melon", 70);
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        OperationHandler balanceOperationHandler = new BalanceOperationHandler(fruitDao);
        OperationHandler purchaseOperationHandler = new PurchaseOperationHandler(fruitDao);
        OperationHandler returnOperationHandler = new ReturnOperationHandler(fruitDao);
        OperationHandler supplyOperationHandler = new SupplyOperationHandler(fruitDao);
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, balanceOperationHandler);
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE, purchaseOperationHandler);
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, returnOperationHandler);
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, supplyOperationHandler);
        strategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    public void get_getBalanceOperation_Ok() {
        OperationHandler actual = strategy.get(FruitTransaction.Operation.BALANCE);
        OperationHandler expected = new BalanceOperationHandler(fruitDao);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void get_PurchaseOperation_Ok() {
        OperationHandler actual = strategy.get(FruitTransaction.Operation.PURCHASE);
        OperationHandler expected = new PurchaseOperationHandler(fruitDao);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void get_ReturnOperation_Ok() {
        OperationHandler actual = strategy.get(FruitTransaction.Operation.RETURN);
        OperationHandler expected = new ReturnOperationHandler(fruitDao);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void get_SupplyOperation_Ok() {
        OperationHandler actual = strategy.get(FruitTransaction.Operation.SUPPLY);
        OperationHandler expected = new SupplyOperationHandler(fruitDao);
        Assert.assertEquals(actual, expected);
    }
}
