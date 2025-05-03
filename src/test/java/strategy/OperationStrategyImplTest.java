package strategy;

import static org.junit.Assert.assertEquals;

import dao.FruitDaoImpl;
import dao.FruitsDao;
import java.util.HashMap;
import java.util.Map;
import model.FruitTransaction;
import org.junit.BeforeClass;
import org.junit.Test;
import strategy.handler.BalanceOperationHandler;
import strategy.handler.OperationHandler;
import strategy.handler.PurchaseOperationHandler;
import strategy.handler.ReturnOperationHandler;
import strategy.handler.SupplyOperationHandler;

public class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;

    @BeforeClass
    public static void beforeClass() {
        FruitsDao fruitsDao = new FruitDaoImpl();
        Map<FruitTransaction.Operation, OperationHandler> handlerMap = new HashMap<>();
        handlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler(fruitsDao));
        handlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler(fruitsDao));
        handlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler(fruitsDao));
        handlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler(fruitsDao));
        operationStrategy = new OperationStrategyImpl(handlerMap);
    }

    @Test
    public void get_balance_Ok() {
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.BALANCE);
        assertEquals(actual.getClass(), BalanceOperationHandler.class);
    }

    @Test
    public void get_supply_Ok() {
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.SUPPLY);
        assertEquals(actual.getClass(), SupplyOperationHandler.class);
    }

    @Test
    public void get_purchase_Ok() {
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.PURCHASE);
        assertEquals(actual.getClass(), PurchaseOperationHandler.class);
    }

    @Test
    public void get_return_Ok() {
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.RETURN);
        assertEquals(actual.getClass(), ReturnOperationHandler.class);
    }
}
