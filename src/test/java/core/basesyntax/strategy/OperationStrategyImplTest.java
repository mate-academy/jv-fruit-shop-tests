package core.basesyntax.strategy;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.AddingOperationHandler;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperationHandler;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

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
        OperationHandler returnHandler =
                operationStratategy.get(FruitTransaction.Operation.RETURN);
        OperationHandler supplyHandler =
                operationStratategy.get(FruitTransaction.Operation.SUPPLY);
        OperationHandler balanceHandler =
                operationStratategy.get(FruitTransaction.Operation.BALANCE);
        OperationHandler purchaseHandler =
                operationStratategy.get(FruitTransaction.Operation.PURCHASE);
        AddingOperationHandler expectedAdding = new AddingOperationHandler(new FruitDaoImpl());
        PurchaseOperationHandler expectedPurchase = new PurchaseOperationHandler(new FruitDaoImpl());
        assertEquals(expectedAdding.getClass(), returnHandler.getClass());
        assertEquals(expectedAdding.getClass(), supplyHandler.getClass());
        assertEquals(expectedAdding.getClass(), balanceHandler.getClass());
        assertEquals(expectedPurchase.getClass(), purchaseHandler.getClass());
    }

    @Test (expected = RuntimeException.class)
    public void get_nullOperation_NotOk() {
        OperationHandler nullHandler =
                operationStratategy.get(null);
    }


}