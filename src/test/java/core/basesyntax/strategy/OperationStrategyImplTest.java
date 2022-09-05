package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.AddingOperationHandler;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static Map<FruitTransaction.Operation, OperationHandler> handlerMap;
    private OperationStratategy operationStratategy;

    @BeforeClass
    public static void beforeClass() {
        handlerMap = new HashMap<>();
        handlerMap.put(FruitTransaction.Operation.BALANCE,
                new AddingOperationHandler(new FruitDaoImpl()));
        handlerMap.put(FruitTransaction.Operation.RETURN,
                new AddingOperationHandler(new FruitDaoImpl()));
        handlerMap.put(FruitTransaction.Operation.SUPPLY,
                new AddingOperationHandler(new FruitDaoImpl()));
        handlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler(new FruitDaoImpl()));
    }

    @Before
    public void setUp() {
        operationStratategy = new OperationStrategyImpl(handlerMap);
    }

    @Test
    public void get_correctOperations_Ok() {
        AddingOperationHandler expectedAdding = new AddingOperationHandler(new FruitDaoImpl());
        OperationHandler returnHandler = operationStratategy
                .get(FruitTransaction.Operation.RETURN);
        assertEquals(expectedAdding.getClass(), returnHandler.getClass());

        OperationHandler supplyHandler = operationStratategy
                .get(FruitTransaction.Operation.SUPPLY);
        assertEquals(expectedAdding.getClass(), supplyHandler.getClass());

        OperationHandler balanceHandler = operationStratategy
                .get(FruitTransaction.Operation.BALANCE);
        assertEquals(expectedAdding.getClass(), balanceHandler.getClass());

        PurchaseOperationHandler expectedPurchase =
                new PurchaseOperationHandler(new FruitDaoImpl());
        OperationHandler purchaseHandler = operationStratategy
                .get(FruitTransaction.Operation.PURCHASE);
        assertEquals(expectedPurchase.getClass(), purchaseHandler.getClass());
    }

    @Test (expected = RuntimeException.class)
    public void get_nullOperation_NotOk() {
        OperationHandler nullHandler =
                operationStratategy.get(null);
    }
}
