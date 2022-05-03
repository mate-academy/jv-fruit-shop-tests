package service.impl;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import model.Operation;
import org.junit.BeforeClass;
import org.junit.Test;
import service.OperationService;
import strategy.BalanceOperationHandlerImpl;
import strategy.OperationHandler;
import strategy.PurchaseOperationHandlerImpl;
import strategy.ReturnOperationHandlerImpl;
import strategy.SupplyOperationHandlerImpl;

public class OperationServiceImplTest {
    private static OperationService operationStrategy;
    private static Map<Operation, OperationHandler> handlerMap;
    private static OperationHandler expected;
    private static OperationHandler actual;

    @BeforeClass
    public static void beforeClass() {
        handlerMap = new HashMap<>();
        handlerMap.put(Operation.BALANCE, new BalanceOperationHandlerImpl());
        handlerMap.put(Operation.SUPPLY, new SupplyOperationHandlerImpl());
        handlerMap.put(Operation.PURCHASE, new PurchaseOperationHandlerImpl());
        handlerMap.put(Operation.RETURN, new ReturnOperationHandlerImpl());
        operationStrategy = new OperationServiceImpl(handlerMap);
    }

    @Test
    public void getHandler_balanceHandler_isOk() {
        expected = handlerMap.get(Operation.BALANCE);
        actual = operationStrategy.getHandler(Operation.BALANCE);
        assertEquals(expected, actual);
    }

    @Test
    public void getHandler_supplyHandler_isOk() {
        expected = handlerMap.get(Operation.SUPPLY);
        actual = operationStrategy.getHandler(Operation.SUPPLY);
        assertEquals(expected, actual);
    }

    @Test
    public void getHandler_returnHandler_isOk() {
        expected = handlerMap.get(Operation.RETURN);
        actual = operationStrategy.getHandler(Operation.RETURN);
        assertEquals(expected, actual);
    }

    @Test
    public void getHandler_purchaseHandler_isOk() {
        expected = handlerMap.get(Operation.PURCHASE);
        actual = operationStrategy.getHandler(Operation.PURCHASE);
        assertEquals(expected, actual);
    }
}
