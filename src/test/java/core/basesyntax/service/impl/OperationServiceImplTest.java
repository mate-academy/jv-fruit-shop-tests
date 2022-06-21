package core.basesyntax.service.impl;

import core.basesyntax.model.OperationWithFruit;
import core.basesyntax.service.OperationService;
import core.basesyntax.strategy.Operation;
import core.basesyntax.strategy.impl.BalanceOperationHandlerImpl;
import core.basesyntax.strategy.impl.PurchaseOperationHandlerImpl;
import core.basesyntax.strategy.impl.ReturnOperationHandlerImpl;
import core.basesyntax.strategy.impl.SupplyOperationHandlerImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationServiceImplTest {
    private static OperationService operationService;
    private static Map<OperationWithFruit, Operation> handlerMap;

    @BeforeClass
    public static void beforeClass() {
        handlerMap = new HashMap<>();
        handlerMap.put(OperationWithFruit.BALANCE, new BalanceOperationHandlerImpl());
        handlerMap.put(OperationWithFruit.PURCHASE, new PurchaseOperationHandlerImpl());
        handlerMap.put(OperationWithFruit.SUPPLY, new SupplyOperationHandlerImpl());
        handlerMap.put(OperationWithFruit.RETURN, new ReturnOperationHandlerImpl());
        operationService = new OperationServiceImpl(handlerMap);
    }

    @Test
    public void getHandler_balanceHandler_IsOk() {
        Operation expected = handlerMap.get(OperationWithFruit.BALANCE);
        Operation actual = operationService.getOperationHandler(OperationWithFruit.BALANCE);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getHandler_purchaseHandler_IsOk() {
        Operation expected = handlerMap.get(OperationWithFruit.PURCHASE);
        Operation actual = operationService.getOperationHandler(OperationWithFruit.PURCHASE);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getHandler_supplyHandler_IsOk() {
        Operation expected = handlerMap.get(OperationWithFruit.SUPPLY);
        Operation actual = operationService.getOperationHandler(OperationWithFruit.SUPPLY);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getHandler_returnHandler_IsOk() {
        Operation expected = handlerMap.get(OperationWithFruit.RETURN);
        Operation actual = operationService.getOperationHandler(OperationWithFruit.RETURN);
        Assert.assertEquals(expected, actual);
    }
}
