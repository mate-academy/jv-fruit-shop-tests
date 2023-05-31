package core.basesyntax.strategy.strategyimpl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.impl.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OperationStrategyImplTest {
    private OperationStrategy operationStrategy;
    private FruitDao fruitDao;

    @Before
    public void setUp() throws Exception {
        fruitDao = new FruitDaoImpl();
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler(fruitDao));
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler(fruitDao));
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler(fruitDao));
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler(fruitDao));
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    public void getBalanceOperation_Ok() {
        Class<BalanceOperationHandler> excepted = BalanceOperationHandler.class;
        Class<? extends OperationHandler> actual = operationStrategy
                .get(FruitTransaction.Operation.BALANCE).getClass();
        Assert.assertEquals(excepted,actual);
    }

    @Test
    public void getPurchaseOperation_Ok() {
        Class<PurchaseOperationHandler> expected = PurchaseOperationHandler.class;
        Class<? extends OperationHandler> actual = operationStrategy
                .get(FruitTransaction.Operation.PURCHASE).getClass();
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void getReturnOperation_Ok() {
        Class<ReturnOperationHandler> excepted = ReturnOperationHandler.class;
        Class<? extends OperationHandler> actual = operationStrategy
                .get(FruitTransaction.Operation.RETURN).getClass();
        Assert.assertEquals(excepted,actual);
    }

    @Test
    public void getSupplyOperation_Ok() {
        Class<SupplyOperationHandler> expected = SupplyOperationHandler.class;
        Class<? extends OperationHandler> actual = operationStrategy
                .get(FruitTransaction.Operation.SUPPLY).getClass();
        Assert.assertEquals(expected,actual);
    }

    @Test(expected = RuntimeException.class)
    public void getIncorrectOperation_NotOk() {
        operationStrategy.get(FruitTransaction.Operation.getByOperation("incorrect"));
    }
}
