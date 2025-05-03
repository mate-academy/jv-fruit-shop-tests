package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.BalanceOperationHandler;
import core.basesyntax.service.operation.FruitOperationHandler;
import core.basesyntax.service.operation.PurchaseOperationHandler;
import core.basesyntax.service.operation.ReturnOperationHandler;
import core.basesyntax.service.operation.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class StrategyImplTest {
    private static FruitDao fruitDao;
    private static Strategy strategy;

    @BeforeClass
    public static void setUp() {
        fruitDao = new FruitDaoImpl();
        fruitDao.addFruit("banana",1000);
        Map<FruitTransaction.Operation, FruitOperationHandler> fruitOperationHandlerMap =
                new HashMap<>();
        FruitOperationHandler balanceOperationHandler = new BalanceOperationHandler(fruitDao);
        FruitOperationHandler returnOperationHandler = new ReturnOperationHandler(fruitDao);
        FruitOperationHandler supplyOperationHandler = new SupplyOperationHandler(fruitDao);
        FruitOperationHandler purchaseOperationHandler = new PurchaseOperationHandler(fruitDao);
        fruitOperationHandlerMap.put(FruitTransaction.Operation.BALANCE, balanceOperationHandler);
        fruitOperationHandlerMap.put(FruitTransaction.Operation.SUPPLY, supplyOperationHandler);
        fruitOperationHandlerMap.put(FruitTransaction.Operation.RETURN, returnOperationHandler);
        fruitOperationHandlerMap.put(FruitTransaction.Operation.PURCHASE, purchaseOperationHandler);
        strategy = new StrategyImpl(fruitOperationHandlerMap);
    }

    @Test
    public void getPurchaseOperation_Ok() {
        FruitOperationHandler actual = strategy.get(FruitTransaction.Operation.PURCHASE);
        FruitOperationHandler expected = new PurchaseOperationHandler(fruitDao);
        assertEquals(expected,actual);
    }

    @Test
    public void getReturnOperation_Ok() {
        FruitOperationHandler actual = strategy.get(FruitTransaction.Operation.RETURN);
        FruitOperationHandler expected = new ReturnOperationHandler(fruitDao);
        assertEquals(expected,actual);
    }

    @Test
    public void getBalanceOperation_Ok() {
        FruitOperationHandler actual = strategy.get(FruitTransaction.Operation.BALANCE);
        FruitOperationHandler expected = new BalanceOperationHandler(fruitDao);
        assertEquals(expected,actual);
    }

    @Test
    public void getSupplyOperation_Ok() {
        FruitOperationHandler actual = strategy.get(FruitTransaction.Operation.SUPPLY);
        FruitOperationHandler expected = new SupplyOperationHandler(fruitDao);
        assertEquals(expected,actual);
    }
}
